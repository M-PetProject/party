package com.study.party.place.dto;

import lombok.*;

@Data
public class PlaceDto {

    private long placeBasicInfoIdx;

    private long creatorMemberIdx;
    private String creatorMemberName;

    private String name;
    private String intro;
    private double rating;
    private String businessHours;
    private String imageUrl;
    private String extUrl;

    private String publicYn;
    private String deleteYn;

    private long commentCount;
}
