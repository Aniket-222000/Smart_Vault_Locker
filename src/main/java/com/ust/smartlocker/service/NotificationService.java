package com.ust.smartlocker.service;



import com.ust.smartlocker.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    // Simulate sending OTP notifications via email/SMS.
    public void sendOtpNotification(User user, String otpCode) {
        logger.info("Sending OTP to {}: Your OTP code is {}", user.getEmail(), otpCode);
    }

    // Simulate sending notification regarding access approval/denial.
    public void sendAccessDecisionNotification(User user, boolean approved) {
        String decision = approved ? "approved" : "denied";
        logger.info("Notification: Locker access request for user {} has been {}", user.getUsername(), decision);
    }
}
