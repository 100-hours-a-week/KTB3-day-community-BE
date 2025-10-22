package com.demo.community.users.domain.repository;

import com.demo.community.users.domain.enitty.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findFirstByEmail(String email);
    Optional<Users> findFirstByNickname(String nickname);
}
