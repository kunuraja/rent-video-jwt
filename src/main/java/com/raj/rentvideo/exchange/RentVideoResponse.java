package com.raj.rentvideo.exchange;

import com.raj.rentvideo.entity.VideoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentVideoResponse {
    private String firstName;
    private String lastName;
    private String email;
    private List<Long> rentalVideos = new ArrayList<>();
}
