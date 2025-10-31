package com.demo.community.likes.domain.repository;

import com.demo.community.likes.domain.entity.LikesPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesPostsRepository extends JpaRepository<LikesPosts, Long> {
    void deleteByUsersIdAndPostsId(Long userId, Long postId);
    void deleteByUsersId(Long userId);
    boolean existsByUsersIdAndPostsId(Long userId, Long postId);
}
