package com.demo.community.likes.controller;

import com.demo.community.common.dto.ApiResponse;
import com.demo.community.likes.dto.LikesPostsResponseDTO;
import com.demo.community.likes.service.LikesPostsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequestMapping("/likes/posts")
@RequiredArgsConstructor
public class LikesPostsController {

    private final LikesPostsService likesPostsService;

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<LikesPostsResponseDTO.LikesPostsResultResponse>> createLike(
            @PathVariable("postId") Long postId,
            HttpServletRequest req
    ){
        HttpSession session = req.getSession(false);
        if (session == null) {return ResponseEntity.status(401).body(new ApiResponse<>("sessoin expired", null));}
        Long userId = (Long) session.getAttribute("USER_ID");

        LikesPostsResponseDTO.LikesPostsResultResponse result = likesPostsService.likeCreate(postId, userId);

        return ResponseEntity.ok(new ApiResponse<>("like added", result));
    }
}
