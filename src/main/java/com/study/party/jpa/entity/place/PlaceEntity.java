package com.study.party.jpa.entity.place;

import com.study.party.jpa.entity.comm.BaseEntity;
import com.study.party.jpa.entity.comm.DefaultEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "place_basic_info")
@Schema(description="장소 정보")
public class PlaceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema
    @Column(name = "place_basic_info_idx")
    private long placeBasicInfoIdx;

    @Schema @Column(name = "creator_member_idx")
    private long creatorMemberIdx;
    @Schema @Column(name = "name")
    private String name;
    @Schema @Column(name = "intro")
    private String intro;
    @Schema @Column(name = "rating")
    private double rating;
    @Schema @Column(name = "business_hours")
    private String businessHours;
    @Schema @Column(name = "image_url")
    private String imageUrl;
    @Schema @Column(name = "ext_url")
    private String extUrl;
    @Schema @Column(name = "public_yn")
    private String publicYn;
    @Schema @Column(name = "delete_yn")
    private String deleteYn;



}
