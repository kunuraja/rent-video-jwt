package com.raj.rentvideo.entity;

import com.raj.rentvideo.enums.AvailableEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "video")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;
    private String title;
    private String director;
    private String genre;
    private AvailableEnum available;

}
