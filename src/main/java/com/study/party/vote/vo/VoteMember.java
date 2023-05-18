package com.study.party.vote.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "투표자정보 VO")
public class VoteMember {
    @Schema(description="투표자명", example="뚜으니")
    private String name;

    @Schema(description="투표일자", example="20230518")
    private String voteDt;

}
