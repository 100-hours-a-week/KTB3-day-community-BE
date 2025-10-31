package com.demo.community.auth.service;

import com.demo.community.auth.dto.AuthResponseDTO;
import com.demo.community.users.domain.enitty.Users;
import com.demo.community.users.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponseDTO.LoginResponse verifyUser(String email, String password){
        Optional<Users> user = userRepository.findFirstByEmail(email);
        if(user.isEmpty()){
            // exception 으로 바로 오류코드와 함께 응답 반환하게 변경
            return null;
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())){
            // exception 으로 바로 오류코드와 함께 응답 반환하게 변경
            return null;
        }

        return AuthResponseDTO.LoginResponse.builder()
                .userId(user.get().getId())
                .email(user.get().getEmail())
                .nickname(user.get().getNickname())
                .profileImage(user.get().getProfileImage()).build();
    }

}
