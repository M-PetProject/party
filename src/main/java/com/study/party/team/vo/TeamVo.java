package com.study.party.team.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Schema(description = "팀 VO")
public class TeamVo {

    @Schema(description="팀 table PK idx", example="1")
    private long teamIdx;

    @JsonIgnore
    private long memberIdx;

    @Schema(description="팀 table 컬럼 회원_유형", example="MASTER/MEMBER")
    private String memberType;

    @Schema(description="팀 table 컬럼 팀_이름", example="string")
    private String teamNm;

    @Schema(description="팀 table 컬럼 팀_설명", example="string")
    private String teamDesc;

    @Schema(description="팀 table 컬럼 팀_생성_일시", example="yyyy-mm-dd hh:mi:ss")
    private String createDate;

    @Schema(description="팀 table 컬럼 팀_참여_코드", example="0000")
    private String joinCode;

    @Schema(description="팀 table 컬럼 팀_1명당_금액", example="0")
    private long memberAmt;

    @Schema(description="팀에 소속된 팀_멤버 목록", example="List<TeamMemberVo>")
    List<TeamMemberVo> teamMemberVoList;

    public TeamMemberVo toTeamMemberVo() {
        return TeamMemberVo.builder().teamIdx(this.teamIdx).memberIdx(this.memberIdx).memberType(this.memberType).build();
    }
}
