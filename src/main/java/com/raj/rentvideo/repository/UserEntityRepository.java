package com.raj.rentvideo.repository;

import com.raj.rentvideo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}