package com.demo.community.users.service;

import com.demo.community.users.domain.enitty.Users;
import com.demo.community.users.domain.repository.UserRepository;
import com.demo.community.users.dto.UsersRequestDTO;
import com.demo.community.users.dto.UsersResponseDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsersService {

//    private static String sha256(String input) {
//        try{
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public Long creatUser(UsersRequestDTO.UserCreateRequest req){

        Optional<Users> checkEmail = userRepository.findFirstByEmail(req.getEmail());
        Optional<Users> checkName = userRepository.findFirstByNickname(req.getNickname());

        // 이것도 상태코드 + 오류문 반환하는 예외처리로 바꾸기.
        if(checkEmail.isPresent()) {
            throw new EntityExistsException("same email user already exists.");
        }
        if (checkName.isPresent()) {
            throw new EntityExistsException("same nickname user already exits");
        }

        String encrypted = passwordEncoder.encode(req.getPassword());

        Users user = Users.builder()
                .email(req.getEmail())
                .password(encrypted)
                .nickname(req.getNickname())
                .profileImage(req.getProfileImage())
                .build();

        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public Boolean checkEmail (UsersRequestDTO.EmailCheckRequest req){
        Optional<Users> checkEmail = userRepository.findFirstByEmail(req.getEmail());

        if(checkEmail.isPresent()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    @Transactional
    public Boolean checkNickname (UsersRequestDTO.NicknameCheckRequest req){
        Optional<Users> checkNickname = userRepository.findFirstByNickname(req.getNickname());

        if(checkNickname.isPresent()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    // 하드코딩으로 localhost 주소 반환하는 상태.
    @Transactional
    public UsersResponseDTO.UserImageResponse getProfileImageUrl (MultipartFile file) throws IOException {
        String originName = file.getOriginalFilename();

        Path savepath = Paths.get("./uploads").toAbsolutePath().normalize().resolve(originName);
        file.transferTo(savepath.toFile());

        String finalUrl = "http://localhost:8080/uploads/" + originName;

        return UsersResponseDTO.UserImageResponse.builder().url(finalUrl).build();
    }

    @Transactional
    public UsersResponseDTO.UserInfoResponse getUser (Long userId, Long curUser){
        // 인가
        if (!Objects.equals(userId, curUser)) {
            throw new EntityNotFoundException("forbidden user (not a writer)");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        return UsersResponseDTO.UserInfoResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .userImage(user.getProfileImage())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getUpdatedAt()).build();
    }

    @Transactional
    public void modifyPassword (
            UsersRequestDTO.PasswordUpdateRequest request,
            Long userId,
            Long curUser
    ){
        // 인가
        if (!Objects.equals(userId, curUser)) {
            throw new EntityNotFoundException("forbidden user");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(request.getCurPassword(), user.getPassword())){
            throw new AccessDeniedException("current password not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    @Transactional
    public UsersResponseDTO.UserInfoResponse updateProfile (
        UsersRequestDTO.UserUpdateRequest request,
        Long userId,
        Long curUser
    ){
        if (!Objects.equals(userId, curUser)) {
            throw new EntityNotFoundException("forbidden user (not a writer)");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        user.updateUser(request.getNickname(), request.getProfileImage());

        userRepository.flush();

        return UsersResponseDTO.UserInfoResponse.builder()
                .userId(user.getId())
                .userImage(user.getProfileImage())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getUpdatedAt()).build();
    }

}
