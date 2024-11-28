package com.raj.rentvideo.repository;

import com.raj.rentvideo.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoEntityRepository extends JpaRepository<VideoEntity, Long> {
}
