package com.study.party.team.vo;

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
public class TeamVo {

    @Schema(description="팀 table PK idx", example="1")
    private long teamIdx;
    @Schema(description="팀 table 컬럼 팀_이름", example="string")
    private String teamNm;
    @Schema(description="팀 table 컬럼 팀_설명", example="string")
    private String teamDesc;
    @Schema(description="팀 table 컬럼 팀_생성_일시", example="yyyy-mm-dd hh:mi:ss")
    private String createDate;
    @Schema(description="팀 table 컬럼 팀_참여_코드", example="0000")
    private String joinCode;

}
