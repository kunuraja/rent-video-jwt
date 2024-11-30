package com.raj.rentvideo.service;

import com.raj.rentvideo.dto.VideoEntityDto;
import com.raj.rentvideo.exception.InvalidRequestException;
import com.raj.rentvideo.exception.NoDataFoundException;
import com.raj.rentvideo.exception.VideoRentalThresholdException;
import com.raj.rentvideo.exception.VideoUnavailableException;
import com.raj.rentvideo.exchange.RentVideoResponse;
import com.raj.rentvideo.exchange.RetrieveVideosResponse;
import com.raj.rentvideo.exchange.VideoResponse;

public interface RentVideoService {
    VideoResponse createVideo(VideoEntityDto videoEntityDto);
    void removeVideoService(Long videoId) throws NoDataFoundException;
    VideoResponse updateVideoService(Long videoId, VideoEntityDto videoEntityDto) throws NoDataFoundException;

    RetrieveVideosResponse retrieveVideosService() throws NoDataFoundException;

    RentVideoResponse rentVideoService(Long userId, Long videoId) throws VideoUnavailableException, NoDataFoundException, VideoRentalThresholdException;

    RentVideoResponse returnVideoService(Long userId, Long videoId) throws InvalidRequestException, NoDataFoundException;
}



