package com.ust.smartlocker.service;



import com.ust.smartlocker.model.User;
import org.springframework.stereotype.Service;

@Service
public class BiometricAuthService {

    // Simulate biometric verification by comparing the provided fingerprint with the stored hash.
    public boolean verifyFingerprint(User user, String providedFingerprint) {
        if (user.getFingerprintHash() == null) {
            return false;
        }
        // In production, implement a proper biometric matching algorithm.
        return user.getFingerprintHash().equals(providedFingerprint);
    }
}
