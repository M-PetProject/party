package com.study.party.place.vo;

import com.study.party.jpa.entity.place.PlaceEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;

@Data
public class PlaceVo {

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
