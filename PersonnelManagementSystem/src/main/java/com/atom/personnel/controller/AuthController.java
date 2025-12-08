package com.atom.personnel.controller;

import com.atom.personnel.dto.request.LoginRequest;
import com.atom.personnel.dto.request.RegisterRequest;
import com.atom.personnel.dto.response.ApiResponse;
import com.atom.personnel.entity.User;
import com.atom.personnel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin("*")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody RegisterRequest registerRequest) {
        // 1. check if user-Agent is valid
        Optional<String> userAgentOptional = Optional.ofNullable(registerRequest.getUserAgent());
        userAgentOptional.ifPresentOrElse(System.out::println,()->{
            System.out.println("User Agent is empty!");
        });

        // 2. reshape & save user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(registerRequest.getPassword());
        System.out.println("Phone: " + registerRequest.getPhone());
        System.out.println("Password: " + registerRequest.getPassword());
        userService.registerUser(user);

        // 3. Respond to frontend
        return ApiResponse.success(user.getUsername());
    }

    @PostMapping(value = "/login")
    @CrossOrigin("*")
    public ResponseEntity<ApiResponse<Long>> userLogin(@RequestBody LoginRequest loginRequest) {
        // 1. Check if User-Agent is valid
        Optional<String> userAgentOptional = Optional.ofNullable(loginRequest.getUserAgent());
        userAgentOptional.ifPresentOrElse(System.out::println,() -> {
            System.out.println("User-Agent is Empty!");
        });

        // 2. Reshape & Save User
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        user.setAutoLogin(loginRequest.getAutoLogin());
        user.setLastLoginTime(LocalDateTime.now());
        //user.setIpAddress(loginRequest.getIpAddress());
        //user.setProvince(loginRequest.getProvince());
        user.setHasLogined(1);
        Long userId = userService.loginUser(user);

        // 3. Respond to frontend
        ResponseCookie cookie = ResponseCookie.from("username", URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();
        return ApiResponse.success(userId);
    }

    @GetMapping("/logout/{id}")
    public ResponseEntity<?> logout(@PathVariable Integer id) {
        userService.logout(id);
        return ResponseEntity.ok().build();
    }

}
