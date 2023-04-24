package com.study.party.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "멤버 싫어하는 음식 VO")
public class MemberHateFoodVo {

    @JsonIgnore
    @Schema(description="회원 싫어하는 음식 table PK idx", example="1")
    private long memberHateFoodIdx;
    @Schema(description="회원 싫어하는 음식 table 회원 idx", example="1")
    private long memberIdx;
    @Schema(description="회원 싫어하는 음식 table 음식 idx", example="1")
    private long foodIdx;
    @Schema(description="회원 싫어하는 음식 table 음식 이름", example="음식")
    private String foodNm;

}
