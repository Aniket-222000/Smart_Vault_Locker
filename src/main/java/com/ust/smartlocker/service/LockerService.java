package com.ust.smartlocker.service;



import com.ust.smartlocker.model.*;
import com.ust.smartlocker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LockerService {

    @Autowired
    private AccessRequestRepository accessRequestRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private BiometricAuthService biometricAuthService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private NotificationService notificationService;

    // Create a new access request after verifying the biometric data.
    public AccessRequest createAccessRequest(User user, String lockerNumber, String fingerprintInput) {
        // Look up the locker by its number.
        Locker locker = lockerRepository.findByLockerNumber(lockerNumber)
                .orElseThrow(() -> new RuntimeException("Locker not found"));
        // Ensure the locker belongs to the customer.
        if (!locker.getOwner().getUsername().equals(user.getUsername())) {
            throw new RuntimeException("Locker does not belong to the user");
        }

        // Verify biometric credentials.
        boolean biometricVerified = biometricAuthService.verifyFingerprint(user, fingerprintInput);
        if (!biometricVerified) {
            throw new RuntimeException("Biometric verification failed");
        }

        // Generate an OTP.
        String otpCode = otpService.generateOtp();

        // Create a new AccessRequest with status "OTP_PENDING".

//        AccessRequest accessRequest = AccessRequest.builder()
//                .user(user)
//                .locker(locker)
//                .status("OTP_PENDING")
//                .otpCode(otpCode)
//                .requestTime(LocalDateTime.now())
//                .build();

        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setUser(user);
        accessRequest.setLocker(locker);
        accessRequest.setStatus("OTP_PENDING");
        accessRequest.setOtpCode(otpCode);
        accessRequest.setRequestTime(LocalDateTime.now());

        accessRequestRepository.save(accessRequest);

        // Send out the OTP notification.
        notificationService.sendOtpNotification(user, otpCode);

        return accessRequest;
    }

    // Verify the OTP provided by the customer.
    public AccessRequest verifyOtp(Long requestId, String providedOtp, User currentUser) {
        AccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Access request not found"));

        // Check that the current user is the owner of the request.
        if (!request.getUser().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("Unauthorized access request");
        }

        if (!otpService.verifyOtp(request.getOtpCode(), providedOtp)) {
            throw new RuntimeException("OTP verification failed");
        }

        // Mark the OTP as verified and update status.
        request.setOtpVerifiedTime(LocalDateTime.now());
        request.setStatus("PENDING_ADMIN");
        accessRequestRepository.save(request);
        return request;
    }

    // Admin: approve the access request.
    public void approveRequest(Long requestId, String adminUsername) {
        AccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Access request not found"));

        if (!"PENDING_ADMIN".equals(request.getStatus())) {
            throw new RuntimeException("Access request is not pending admin approval");
        }

        request.setStatus("APPROVED");
        request.setDecisionTime(LocalDateTime.now());
        request.setDecisionBy(adminUsername);
        accessRequestRepository.save(request);

        // Create an access log entry.
//        AccessLog log = AccessLog.builder()
//                .user(request.getUser())
//                .locker(request.getLocker())
//                .accessTime(LocalDateTime.now())
//                .outcome("GRANTED")
//                .build();
//        accessLogRepository.save(log);

        AccessLog log = new AccessLog();
        log.setUser(request.getUser());
        log.setLocker(request.getLocker());
        log.setAccessTime(LocalDateTime.now());
        log.setOutcome("GRANTED");
        accessLogRepository.save(log);

        // Notify the customer of approval.
        notificationService.sendAccessDecisionNotification(request.getUser(), true);
    }

    // Admin: deny the access request.
    public void denyRequest(Long requestId, String adminUsername) {
        AccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Access request not found"));

        if (!"PENDING_ADMIN".equals(request.getStatus())) {
            throw new RuntimeException("Access request is not pending admin approval");
        }

        request.setStatus("DENIED");
        request.setDecisionTime(LocalDateTime.now());
        request.setDecisionBy(adminUsername);
        accessRequestRepository.save(request);

        // Create an access log entry for the denial.
//        AccessLog log = AccessLog.builder()
//                .user(request.getUser())
//                .locker(request.getLocker())
//                .accessTime(LocalDateTime.now())
//                .outcome("DENIED")
//                .build();
//        accessLogRepository.save(log);

        AccessLog log = new AccessLog();
        log.setUser(request.getUser());
        log.setLocker(request.getLocker());
        log.setAccessTime(LocalDateTime.now());
        log.setOutcome("DENIED");
        accessLogRepository.save(log);

        // Notify the customer of the denial.
        notificationService.sendAccessDecisionNotification(request.getUser(), false);
    }

    // Retrieve all access requests pending admin approval.
    public List<AccessRequest> getPendingRequests() {
        return accessRequestRepository.findByStatus("PENDING_ADMIN");
    }

    // Retrieve all access logs.
    public List<AccessLog> getAllAccessLogs() {
        return accessLogRepository.findAll();
    }
}
