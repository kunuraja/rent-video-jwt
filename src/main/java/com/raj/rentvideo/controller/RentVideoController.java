package com.raj.rentvideo.controller;

import com.raj.rentvideo.dto.VideoEntityDto;
import com.raj.rentvideo.exception.InvalidRequestException;
import com.raj.rentvideo.exception.NoDataFoundException;
import com.raj.rentvideo.exchange.RetrieveVideosResponse;
import com.raj.rentvideo.exchange.VideoResponse;
import com.raj.rentvideo.service.RentVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class RentVideoController {

    @Autowired
    RentVideoService rentVideoService;

    @PostMapping("/videos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createVideo(@RequestBody VideoEntityDto videoEntityDto) {
        try {
            if (null != videoEntityDto) {
                VideoResponse videoResponse = rentVideoService.createVideo(videoEntityDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(videoResponse);
            }else throw new InvalidRequestException("Invalid Request");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }

    @DeleteMapping("/videos/{videoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> removeVideo(@PathVariable Long videoId) {
        try {
            rentVideoService.removeVideoService(videoId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        } catch (NoDataFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid video Id");
    }

    @PutMapping("/videos/{videoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateVideo(@PathVariable Long videoId, @RequestBody VideoEntityDto videoEntityDto) {
        VideoResponse videoResponse = null;
        try {
            videoResponse = rentVideoService.updateVideoService(videoId, videoEntityDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(videoResponse);
        } catch (NoDataFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid video Id");
    }

    @GetMapping("/videos")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> retrieveVideos() {
        RetrieveVideosResponse retrieveVideosResponse = null;
        try {
            retrieveVideosResponse = rentVideoService.retrieveVideosService();
            return ResponseEntity.status(HttpStatus.OK).body(retrieveVideosResponse);
        } catch (NoDataFoundException e) {
            e.getStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No videos found");

    }

}
