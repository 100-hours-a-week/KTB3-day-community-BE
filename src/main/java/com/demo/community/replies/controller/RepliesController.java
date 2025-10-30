package com.demo.community.replies.controller;

import com.demo.community.common.dto.ApiResponse;
import com.demo.community.replies.dto.RepliesResponseDTO;
import com.demo.community.replies.service.RepliesService;
import com.demo.community.users.dto.UsersResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class RepliesController {

    private final RepliesService repliesService;

    public ResponseEntity<ApiResponse<RepliesResponseDTO.ReplyListSliceResponse>> getRepliesList(
            @PathVariable("postId") Long postId,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long lastSeenId
    ){
        RepliesResponseDTO.ReplyListSliceResponse result = repliesService.getReplyList(postId, lastSeenId, size);

        return ResponseEntity.ok(new ApiResponse<>("reply list successfully loaded", result));
    }
}
