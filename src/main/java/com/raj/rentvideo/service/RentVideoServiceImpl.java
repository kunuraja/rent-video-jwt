package com.raj.rentvideo.service;

import com.raj.rentvideo.dto.VideoEntityDto;
import com.raj.rentvideo.entity.VideoEntity;
import com.raj.rentvideo.enums.AvailableEnum;
import com.raj.rentvideo.exception.NoDataFoundException;
import com.raj.rentvideo.exchange.RetrieveVideosResponse;
import com.raj.rentvideo.exchange.VideoResponse;
import com.raj.rentvideo.repository.VideoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentVideoServiceImpl implements RentVideoService {

    @Autowired
    VideoEntityRepository videoEntityRepository;


    @Override
    public VideoResponse createVideo(VideoEntityDto videoEntityDto) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setDirector(videoEntityDto.getDirector());
        videoEntity.setGenre(videoEntityDto.getGenre());
        videoEntity.setTitle(videoEntityDto.getTitle());
        System.out.println(videoEntityDto.getAvailable());
        if (null == videoEntityDto.getAvailable())
            videoEntity.setAvailable(AvailableEnum.AVAILABLE);
        else
            videoEntity.setAvailable(videoEntityDto.getAvailable());
        VideoEntity savedVideo = videoEntityRepository.save(videoEntity);
        return new VideoResponse(savedVideo.getVideoId());
    }

    @Override
    public void removeVideoService(Long videoId) throws NoDataFoundException {
        if (null != videoId) {
            Optional<VideoEntity> retrievedVideoOptional = videoEntityRepository.findById(videoId);
            retrievedVideoOptional.ifPresent(videoEntity -> videoEntityRepository.delete(videoEntity));
        }else throw new NoDataFoundException("Invalid video Id");
    }

    @Override
    public VideoResponse updateVideoService(Long videoId, VideoEntityDto videoEntityDto) throws NoDataFoundException {
        VideoResponse videoResponse = new VideoResponse();
        if (null != videoId) {
            Optional<VideoEntity> retrievedVideoOptional = videoEntityRepository.findById(videoId);
            if (retrievedVideoOptional.isPresent()) {
                VideoEntity videoEntityToUpdate = retrievedVideoOptional.get();
                videoEntityToUpdate.setVideoId(videoId);
                videoEntityToUpdate.setTitle(videoEntityDto.getTitle());
                videoEntityToUpdate.setDirector(videoEntityDto.getDirector());
                videoEntityToUpdate.setGenre(videoEntityDto.getGenre());
                videoEntityToUpdate.setAvailable(videoEntityDto.getAvailable());
                videoEntityRepository.save(videoEntityToUpdate);
                videoResponse.setVideoId(videoId);
                return videoResponse;
            }else throw new NoDataFoundException("Invalid video Id");
        }
        videoResponse.setVideoId(-1L);
        return videoResponse;
    }

    @Override
    public RetrieveVideosResponse retrieveVideosService() throws NoDataFoundException {

        RetrieveVideosResponse retrieveVideosResponse = new RetrieveVideosResponse();
        List<VideoEntity> videoEntityList = videoEntityRepository.findAll();
        if (!videoEntityList.isEmpty()) {
            videoEntityList.stream().forEach(videoEntity -> {
                        VideoEntityDto videoEntityDto = new VideoEntityDto();
                        videoEntityDto.setTitle(videoEntity.getTitle());
                        videoEntityDto.setDirector(videoEntity.getDirector());
                        videoEntityDto.setGenre(videoEntity.getGenre());
                        videoEntityDto.setAvailable(videoEntity.getAvailable());
                        retrieveVideosResponse.getVideoEntityDtoList().add(videoEntityDto);


                    }
            );
        }else throw new NoDataFoundException("No videos found");

        System.out.println(retrieveVideosResponse.getVideoEntityDtoList());
        return retrieveVideosResponse;
    }
}
