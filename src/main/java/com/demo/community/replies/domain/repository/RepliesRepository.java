package com.demo.community.replies.domain.repository;

import com.demo.community.replies.domain.entity.Replies;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepliesRepository extends JpaRepository<Replies, Long> {
    @Query("""
        select r
        from Replies r
        join fetch r.users u
        where r.posts.id = :postId
          and (:cursorId is null or r.id < :cursorId)
        order by r.id desc
    """)
    List<Replies> findSliceByPostId(@Param("postId") Long postId,
                                    @Param("cursorId") Long cursorId,
                                    Pageable pageable);

    @Modifying
    @Query("update Replies r set r.users = null where r.users.id = :userId")
    int nullifyUserReferences(@Param("userId") Long userId);
}
