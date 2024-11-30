package com.raj.rentvideo.repository;

import com.raj.rentvideo.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoEntityRepository extends JpaRepository<VideoEntity, Long> {

    @Query("select rv.videoId from User u join u.rentalVideos rv where u.userId = :userId")
    List<Long> findAllVideosByUserId(@Param("userId") Long userId);
}
