package com.study.party.member.vo;

import com.study.party.comm.vo.CommPaginationReqVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "멤버 VO")
public class MemberVo extends CommPaginationReqVo {

    @Schema(description="회원 table PK idx", example="1")
    private long memberIdx;
    @Schema(description="회원 table 컬럼 member_id", example="string")
    private String memberId;
    @Schema(description="회원 table 컬럼 member_password", example="string")
    private String memberPassword;
    @Schema(description="회원 table 컬럼 member_name", example="string")
    private String memberName;

    @Schema(description="회원 알러지 목록")
    private List<MemberAllergy> memberAllergies;
    @Schema(description="회원 싫어하는 음식 목록")
    private List<MemberHateFood> memberHateFoods;
    @Schema(description="회원 좋아하는 음식 목록")
    private List<MemberLikeFood> memberLikeFoods;
}
