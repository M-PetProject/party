package com.study.party.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "멤버 알러지 VO")
public class MemberAllergyVo {
    @Schema(description="회원 알러지 table PK 알러지 IDX", example="1")
    private long memberAllergyIdx;
    @Schema(description="회원 알러지 table 회원 idx", example="1")
    private long memberIdx;
    @Schema(description="회원 알러지 table 알러지 idx", example="1")
    private long allergyIdx;
    @Schema(description="회원 알러지 table 알러지 이름", example="알러지")
    private String allergyNm;
}
