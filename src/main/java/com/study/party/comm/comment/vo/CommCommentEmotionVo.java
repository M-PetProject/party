package com.study.party.comm.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="공통 댓글 좋아요/싫어요 VO")
public class CommCommentEmotionVo {

    @Schema(description="공통 댓글 테이블 PK IDX", example="1")
    private long commentIdx;
    @Schema(description="공통 댓글 테이블 컬럼 회원_IDX", example="1")
    private long memberIdx;

}
