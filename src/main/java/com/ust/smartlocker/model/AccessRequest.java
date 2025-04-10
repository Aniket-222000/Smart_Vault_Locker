package com.ust.smartlocker.model;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AccessRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The customer requesting access
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The locker the customer wants to access
    @ManyToOne
    @JoinColumn(name = "locker_id", nullable = false)
    private Locker locker;

    // Request status: OTP_PENDING, PENDING_ADMIN, APPROVED, or DENIED
    @Column(nullable = false)
    private String status;

    // Generated OTP code (for simulation)
    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(name = "otp_verified_time")
    private LocalDateTime otpVerifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public LocalDateTime getOtpVerifiedTime() {
        return otpVerifiedTime;
    }

    public void setOtpVerifiedTime(LocalDateTime otpVerifiedTime) {
        this.otpVerifiedTime = otpVerifiedTime;
    }

    public LocalDateTime getDecisionTime() {
        return decisionTime;
    }

    public void setDecisionTime(LocalDateTime decisionTime) {
        this.decisionTime = decisionTime;
    }

    public String getDecisionBy() {
        return decisionBy;
    }

    public void setDecisionBy(String decisionBy) {
        this.decisionBy = decisionBy;
    }

    @Column(name = "decision_time")
    private LocalDateTime decisionTime;

    @Column(name = "decision_by")
    private String decisionBy; // Admin username who processed the request
}
