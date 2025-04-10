package com.ust.smartlocker.controller;



import com.ust.smartlocker.model.AccessRequest;
import com.ust.smartlocker.model.User;
import com.ust.smartlocker.repository.UserRepository;
import com.ust.smartlocker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/locker")
public class LockerController {

    @Autowired
    private LockerService lockerService;

    @Autowired
    private UserRepository userRepository;

    // DTO for initiating an access request.
    public static class AccessRequestDto {
        public String lockerNumber;
        public String fingerprintInput;
    }

    // DTO for OTP verification.
    public static class OtpVerificationDto {
        public String otp;
    }

    @PostMapping("/request")
    public Map<String, Object> createAccessRequest(@RequestBody AccessRequestDto requestDto, Authentication authentication) {
        User currentUser = getUserFromAuth(authentication);
        AccessRequest request = lockerService.createAccessRequest(currentUser, requestDto.lockerNumber, requestDto.fingerprintInput);
        Map<String, Object> response = new HashMap<>();
        response.put("requestId", request.getId());
        response.put("message", "OTP sent to your registered contact.");
        return response;
    }

    @PostMapping("/request/{requestId}/verify")
    public Map<String, Object> verifyOtp(@PathVariable Long requestId, @RequestBody OtpVerificationDto otpDto, Authentication authentication) {
        User currentUser = getUserFromAuth(authentication);
        AccessRequest request = lockerService.verifyOtp(requestId, otpDto.otp, currentUser);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "OTP verified. Awaiting admin approval.");
        response.put("status", request.getStatus());
        return response;
    }

    // Helper method to load the full User entity using the username from authentication.
    private User getUserFromAuth(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
