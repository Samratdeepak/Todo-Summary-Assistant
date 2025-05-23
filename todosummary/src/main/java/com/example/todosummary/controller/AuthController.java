package com.example.todosummary.controller;

import com.example.todosummary.model.User;
import com.example.todosummary.repository.UserRepository;
import com.example.todosummary.service.MailService;
import com.example.todosummary.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.security.SecureRandom;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserRepository userRepo;
    @Autowired private MailService mailService;
    @Autowired private PasswordEncoder encoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;

    private String generateOtp() {
        SecureRandom rand = new SecureRandom();
        int otp = 100000 + rand.nextInt(900000);
        return String.valueOf(otp);
    }

    // Step 1: Send OTP for signup
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        if (userRepo.findByEmail(email).isPresent())
            return ResponseEntity.badRequest().body(Map.of("message", "Email already exists"));
        String otp = generateOtp();
        User user = new User();
        user.setEmail(email);
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        user.setEnabled(false);
        userRepo.save(user);
        mailService.sendOtp(email, otp);
        return ResponseEntity.ok(Map.of("message", "OTP sent to email"));
    }

    // Step 2: Validate OTP
    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String otp = req.get("otp");
        Optional<User> oUser = userRepo.findByEmail(email);
        if (oUser.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        User user = oUser.get();
        if (!otp.equals(user.getOtp()) || user.getOtpExpiry().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired OTP"));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message", "OTP validated"));
    }

    // Step 3: Register (username + password)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String username = req.get("username");
        String password = req.get("password");
        if (userRepo.findByUsername(username).isPresent())
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
        Optional<User> oUser = userRepo.findByEmail(email);
        if (oUser.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        User user = oUser.get();
        if (user.getEnabled()) return ResponseEntity.badRequest().body(Map.of("message", "Already registered"));
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEnabled(true);
        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message", "Registration successful, please login"));
    }

    // Login (by username)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.get("username"), req.get("password"))
            );
            String token = jwtUtil.generateToken(req.get("username"));
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

    // Forgot password - send OTP
    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        Optional<User> oUser = userRepo.findByEmail(email);
        if (oUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));

        String otp = generateOtp();
        User user = oUser.get();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        userRepo.save(user);
        mailService.sendOtp(email, otp);
        return ResponseEntity.ok(Map.of("message", "OTP sent to email"));
    }

    // Forgot password - validate OTP
    @PostMapping("/forgot/validate-otp")
    public ResponseEntity<?> validateForgotOtp(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String otp = req.get("otp");
        Optional<User> oUser = userRepo.findByEmail(email);
        if (oUser.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        User user = oUser.get();
        if (!otp.equals(user.getOtp()) || user.getOtpExpiry().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired OTP"));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message", "OTP validated"));
    }

    // Forgot password - set new password
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String newPassword = req.get("password");
        Optional<User> oUser = userRepo.findByEmail(email);
        if (oUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        User user = oUser.get();
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }
}