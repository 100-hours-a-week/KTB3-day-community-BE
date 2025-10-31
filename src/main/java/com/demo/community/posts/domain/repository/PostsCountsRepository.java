package com.demo.community.posts.domain.repository;

import com.demo.community.posts.domain.entity.PostsCounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsCountsRepository extends JpaRepository<PostsCounts, Long> {
    void deleteById(@Param("postId") Long Id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update PostsCounts p set p.likeCounts = p.likeCounts + 1 where p.posts.id = :postId")
    void incrementLikeCount(@Param("postId") Long postId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update PostsCounts p set p.likeCounts = p.likeCounts - 1 where p.posts.id = :postId")
    void decrementLikeCount(@Param("postId") Long postId);

    @Query("select pc.likeCounts from PostsCounts pc where pc.posts.id = :postId")
    int getLikeCount(@Param("postId") Long postId);
}
