package com.study.party.jpa.entity.cm_food;


import com.study.party.jpa.entity.comm.DefaultEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cm_food")
@Entity
@Schema(description="사용자가 등록한 음식 정보")
public class CmFoodEntity extends DefaultEntity {

    private static final long serialVersionUID = 145828767031252846L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description="사용자가 등록한 음식 정보 IDX")
    private long foodIdx;

    @Schema(description="사용자가 등록한 음식 이름")
    private String foodNm;

}
