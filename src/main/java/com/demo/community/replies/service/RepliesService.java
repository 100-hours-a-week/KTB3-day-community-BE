package com.demo.community.replies.service;

import com.demo.community.replies.domain.entity.Replies;
import com.demo.community.replies.domain.repository.RepliesRepository;
import com.demo.community.replies.dto.RepliesResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RepliesService {

    private final RepliesRepository repliesRepository;

    @Transactional(readOnly = true)
    public RepliesResponseDTO.ReplyListSliceResponse getReplyList(Long postId, Long lastSeenId, int size){

        List<Replies> replies = repliesRepository.findSliceByPostId(postId, lastSeenId, PageRequest.of(0, size + 1));

        List<RepliesResponseDTO.ReplyDetailResponse> result = replies.stream().map(
                reply -> RepliesResponseDTO.ReplyDetailResponse.builder()
                        .id(reply.getId())
                        .userId(reply.getUsers().getId())
                        .content(reply.getContent())
                        .createdAt(reply.getCreatedAt())
                        .nickname(reply.getUsers().getNickname())
                        .profileImg(reply.getUsers().getProfileImage())
                        .build()).toList();

        boolean hasNext = replies.size() > size;
        if (hasNext){
            replies = replies.subList(0, size);
        }

        Long nextCursor = replies.isEmpty() ? null : replies.getLast().getId();

        return new RepliesResponseDTO.ReplyListSliceResponse(result, hasNext, nextCursor);
    }

}
