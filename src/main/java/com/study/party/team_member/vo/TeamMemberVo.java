package com.study.party.team_member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "팀 VO")
public class TeamMemberVo {

    @Schema(description="팀 소속 멤버 table PK idx", example="1")
    private long teamIdx;

    @Schema(description="팀 table 컬럼 팀_이름", example="string")
    private String teamNm;

    @Schema(description="팀 소속 멤버 table FK 멤버 idx", example="1")
    private long memberIdx;

    @Schema(description="회원 table 컬럼 member_id", example="string")
    private String memberId;

    @Schema(description="회원 table 컬럼 member_name", example="string")
    private String memberName;

    @Schema(description="팀 소속 멤버 table 컬럼 팀 소속 멤버 타입", example="MEMBER")
    private String memberType;

}
