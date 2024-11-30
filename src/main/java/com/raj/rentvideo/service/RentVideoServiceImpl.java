package com.raj.rentvideo.service;

import com.raj.rentvideo.dto.VideoEntityDto;
import com.raj.rentvideo.entity.UserEntity;
import com.raj.rentvideo.entity.VideoEntity;
import com.raj.rentvideo.enums.AvailableEnum;
import com.raj.rentvideo.exception.InvalidRequestException;
import com.raj.rentvideo.exception.NoDataFoundException;
import com.raj.rentvideo.exception.VideoRentalThresholdException;
import com.raj.rentvideo.exception.VideoUnavailableException;
import com.raj.rentvideo.exchange.RentVideoResponse;
import com.raj.rentvideo.exchange.RetrieveVideosResponse;
import com.raj.rentvideo.exchange.VideoResponse;
import com.raj.rentvideo.repository.UserEntityRepository;
import com.raj.rentvideo.repository.VideoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentVideoServiceImpl implements RentVideoService {

    @Autowired
    VideoEntityRepository videoEntityRepository;

    @Autowired
    UserEntityRepository userEntityRepository;


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

    @Override
    public RentVideoResponse rentVideoService(Long userId, Long videoId) throws VideoUnavailableException, NoDataFoundException, VideoRentalThresholdException {
        RentVideoResponse rentVideoResponse = new RentVideoResponse();
        if (userId != null && videoId != null){
            Optional<UserEntity> userOptional = userEntityRepository.findById(userId);
            Optional<VideoEntity> videoOptional = videoEntityRepository.findById(videoId);

            if (userOptional.isPresent() && videoOptional.isPresent()){
                UserEntity retrievedUser = userOptional.get();
                VideoEntity retrivedVideo = videoOptional.get();
                if (retrivedVideo.getAvailable().equals(AvailableEnum.UNAVAILABLE)){
                    throw new VideoUnavailableException("Video is currently unavailable");
                };
                // Find the list of videos by a particular user
                List<Long> videoIds = videoEntityRepository.findAllVideosByUserId(userId);
                System.out.println("videoIds :"+ videoIds);
                if (videoIds.size() == 2) throw new VideoRentalThresholdException("Maximum 2 video rentals allowed");
                retrivedVideo.setAvailable(AvailableEnum.UNAVAILABLE);
                retrievedUser.getRentalVideos().add(videoOptional.get());
                UserEntity savedUser = userEntityRepository.save(retrievedUser);
                //RentVideoResponse rentVideoResponse = new RentVideoResponse();
                rentVideoResponse.setFirstName(savedUser.getFirstName());
                rentVideoResponse.setLastName(savedUser.getLastName());
                rentVideoResponse.setEmail(savedUser.getEmail());
                // find all videos ids for a particular user
                List<Long> totalVideoIds = videoEntityRepository.findAllVideosByUserId(userId);
                System.out.println(" :"+ totalVideoIds);
                // add all videos to the RentVideoResponse
                rentVideoResponse.getRentalVideos().addAll(totalVideoIds);
                System.out.println(rentVideoResponse);
                return rentVideoResponse;

            }else throw new NoDataFoundException("Invalid user Id or video Id");

        }
        return rentVideoResponse;
    }

    @Override
    public RentVideoResponse returnVideoService(Long userId, Long videoId) throws InvalidRequestException, NoDataFoundException {

        RentVideoResponse rentVideoResponse = new RentVideoResponse();
        if (userId != null && videoId != null){
            Optional<UserEntity> userOptional = userEntityRepository.findById(userId);
            Optional<VideoEntity> videoOptional = videoEntityRepository.findById(videoId);

            if (userOptional.isPresent() && videoOptional.isPresent()){
                UserEntity retrievedUser = userOptional.get();
                VideoEntity retrivedVideo = videoOptional.get();
                if (retrivedVideo.getAvailable().equals(AvailableEnum.AVAILABLE)){
                    throw new InvalidRequestException("Video id is invalid");
                };
                // find all videos ids for a particular user
                List<Long> videoIds = videoEntityRepository.findAllVideosByUserId(userId);
                System.out.println(videoIds);
                if (!videoIds.isEmpty()) {
                    retrivedVideo.setAvailable(AvailableEnum.AVAILABLE);
                    retrievedUser.getRentalVideos().remove(retrivedVideo);
                    UserEntity savedUser = userEntityRepository.save(retrievedUser);
                    //RentVideoResponse rentVideoResponse = new RentVideoResponse();
                    rentVideoResponse.setFirstName(savedUser.getFirstName());
                    rentVideoResponse.setLastName(savedUser.getLastName());
                    rentVideoResponse.setEmail(savedUser.getEmail());
                    // Remaining videos after the return of video
                    List<Long> remainingVideoIds = videoEntityRepository.findAllVideosByUserId(userId);
                    System.out.println("remainingVideoIds :" + remainingVideoIds);
                    // add all videos to the RentVideoResponse
                    rentVideoResponse.getRentalVideos().addAll(remainingVideoIds);
                    System.out.println(rentVideoResponse);
                    return rentVideoResponse;
                }

            }else throw new NoDataFoundException("Invalid user Id or video Id");

        }
        return rentVideoResponse;
    }
}
