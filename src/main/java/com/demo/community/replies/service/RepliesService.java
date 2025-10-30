package com.demo.community.replies.service;

import com.demo.community.posts.domain.entity.Posts;
import com.demo.community.posts.domain.repository.PostRepository;
import com.demo.community.replies.domain.entity.Replies;
import com.demo.community.replies.domain.repository.RepliesRepository;
import com.demo.community.replies.dto.RepliesRequestDTO;
import com.demo.community.replies.dto.RepliesResponseDTO;
import com.demo.community.users.domain.enitty.Users;
import com.demo.community.users.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RepliesService {

    private final RepliesRepository repliesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

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

    @Transactional
    public RepliesResponseDTO.ReplyDetailResponse createReply(Long userId, RepliesRequestDTO.ReplyCreateRequest request){

        Optional<Users> user = userRepository.findById(userId);
        if(user.isEmpty()){throw new EntityNotFoundException("user not found");}

        Optional<Posts> post = postRepository.findById(request.getPostId());
        if(post.isEmpty()){throw new EntityNotFoundException("post not found");}

        Replies reply = Replies.builder()
                .posts(post.get())
                .content(request.getContent())
                .users(user.get()).build();

        repliesRepository.save(reply);
        repliesRepository.flush();

        return RepliesResponseDTO.ReplyDetailResponse.builder()
                .id(reply.getId())
                .userId(reply.getUsers().getId())
                .createdAt(reply.getCreatedAt())
                .nickname(reply.getUsers().getNickname())
                .profileImg(reply.getUsers().getProfileImage())
                .content(reply.getContent()).build();
    }

}
