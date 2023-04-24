package com.study.party.auth.vo;

import com.study.party.member.vo.MemberAllergyVo;
import com.study.party.member.vo.MemberHateFoodVo;
import com.study.party.member.vo.MemberLikeFoodVo;
import com.study.party.team_member.vo.TeamMemberVo;
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
@Schema(description = "회원 로그인 응답 VO")
public class LoginResVo {
    @Schema(description="회원 로그인 응답 회원 IDX", required=true)
    private long memberIdx;
    @Schema(description="회원 로그인 응답 회원 ID", required=true)
    private String memberId;
    @Schema(description="회원 로그인 응답 회원 이름", required=true)
    private String memberName;
    @Schema(description="회원 로그인 응답 JWT", required=true)
    private String accessToken;
    @Schema(description="회원 로그인 응답 Refresh Token", required=true)
    private String refreshToken;

    @Schema(description="회원 알러지 목록")
    private List<MemberAllergyVo> memberAllergyVos;
    @Schema(description="회원 싫어하는 음식 목록")
    private List<MemberHateFoodVo> memberHateFoodVos;
    @Schema(description="회원 좋아하는 음식 목록")
    private List<MemberLikeFoodVo> memberLikeFoodVos;

    @Schema(description="회원 소속 팀 목록")
    private List<TeamMemberVo> teamMemberVos;

}
