package com.study.party.vote.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "투표목록정보 VO")
public class VoteListInfo {
    @Schema(description="조회투표키", example="1")
    private long voteIdx;

    @Schema(description="장소명", example="망원회관")
    private String placeNm;

    @Schema(description="투표자정보", example="Array")
    private List<VoteMember> voteMemberList = new ArrayList<>();

}
