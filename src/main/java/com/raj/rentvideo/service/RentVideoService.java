package com.raj.rentvideo.service;

import com.raj.rentvideo.dto.VideoEntityDto;
import com.raj.rentvideo.exception.NoDataFoundException;
import com.raj.rentvideo.exchange.RetrieveVideosResponse;
import com.raj.rentvideo.exchange.VideoResponse;

public interface RentVideoService {
    VideoResponse createVideo(VideoEntityDto videoEntityDto);
    void removeVideoService(Long videoId) throws NoDataFoundException;
    VideoResponse updateVideoService(Long videoId, VideoEntityDto videoEntityDto) throws NoDataFoundException;

    RetrieveVideosResponse retrieveVideosService() throws NoDataFoundException;
}

