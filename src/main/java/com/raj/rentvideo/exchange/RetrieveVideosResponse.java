package com.raj.rentvideo.exchange;

import com.raj.rentvideo.dto.VideoEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveVideosResponse {
    List<VideoEntityDto> videoEntityDtoList = new ArrayList<>();
}
