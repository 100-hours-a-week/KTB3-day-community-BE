package com.demo.community.posts.domain.repository;

import com.demo.community.posts.domain.entity.Posts;
import com.demo.community.posts.domain.entity.PostsImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsImageRepository extends JpaRepository<PostsImages, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from PostsImages pi where pi.posts.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}
