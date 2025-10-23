package com.demo.community.auth.controller;

import com.demo.community.auth.dto.AuthRequestDTO;
import com.demo.community.auth.dto.AuthResponseDTO;
import com.demo.community.auth.service.AuthService;
import com.demo.community.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> login(
            @RequestBody @Valid AuthRequestDTO.LoginRequest req,
            HttpServletRequest request
            ){
        // 아이디, 비밀번호 검증 로직
        AuthResponseDTO.LoginResponse user = authService.verifyUser(req.getEmail(), req.getPassword());
        if (user == null){return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("login failed", null));}

        HttpSession session = request.getSession(true);
        session.setAttribute("USER_ID", user.getUserId());

        return ResponseEntity.ok(new ApiResponse<>("login success", user));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> logout(
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok(new ApiResponse<>("logout success", null));
    }
}
