package com.raj.rentvideo.dto;

import com.raj.rentvideo.enums.AvailableEnum;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.raj.rentvideo.entity.VideoEntity} entity
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "video")
public class VideoEntityDto {
    private  String title;
    private  String director;
    private  String genre;
    private AvailableEnum available;
}