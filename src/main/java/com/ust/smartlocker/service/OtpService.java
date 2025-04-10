package com.ust.smartlocker.service;



import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OtpService {

    // Generate a random 6-digit OTP.
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Verify the provided OTP against the stored OTP.
    public boolean verifyOtp(String storedOtp, String providedOtp) {
        if (storedOtp == null) return false;
        return storedOtp.equals(providedOtp);
    }
}
