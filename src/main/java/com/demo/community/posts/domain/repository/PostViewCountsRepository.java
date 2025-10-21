package com.demo.community.posts.domain.repository;

import com.demo.community.posts.domain.entity.PostViewCounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostViewCountsRepository extends JpaRepository<PostViewCounts, Long> {
    void deleteById(@Param("postId") Long Id);
}
