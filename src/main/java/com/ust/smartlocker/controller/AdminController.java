package com.ust.smartlocker.controller;



import com.ust.smartlocker.model.AccessRequest;
import com.ust.smartlocker.model.AccessLog;
import com.ust.smartlocker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private LockerService lockerService;

    // List all pending access requests.
    @GetMapping("/requests")
    public List<AccessRequest> getPendingRequests() {
        return lockerService.getPendingRequests();
    }

   //for debugging the authentication
        @GetMapping("/api/me")
        public Map<String, String> currentUser(Authentication authentication) {
            Map<String, String> result = new HashMap<>();
            result.put("username", authentication.getName());
            return result;
        }


    // Approve an access request.
    @PostMapping("/requests/{requestId}/approve")
    public Map<String, Object> approveRequest(@PathVariable Long requestId, Authentication authentication) {
        String adminUsername = authentication.getName();
        lockerService.approveRequest(requestId, adminUsername);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Access request approved.");
        return response;
    }

    // Deny an access request.
    @PostMapping("/requests/{requestId}/deny")
    public Map<String, Object> denyRequest(@PathVariable Long requestId, Authentication authentication) {
        String adminUsername = authentication.getName();
        lockerService.denyRequest(requestId, adminUsername);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Access request denied.");
        return response;
    }

    // Retrieve all locker access logs.
    @GetMapping("/logs")
    public List<AccessLog> getAccessLogs() {
        return lockerService.getAllAccessLogs();
    }
}
