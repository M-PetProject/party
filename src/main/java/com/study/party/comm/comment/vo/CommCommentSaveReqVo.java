package com.study.party.comm.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="공통 댓글 작성 VO")
public class CommCommentSaveReqVo {

    @Schema(description="공통 댓글 테이블 컬럼 댓글_코드(G:CMMT)", example="NOTC")
    private String commentCd;
    @Schema(description="공통 댓글 테이블 컬럼 게시글_IDX", example="1")
    private long postIdx;
    @Schema(description="공통 댓글 테이블 컬럼 회원_IDX", example="1")
    private long memberIdx;
    @Schema(description="공통 댓글 테이블 컬럼 제목", example="제목입니다")
    private String title;
    @Schema(description="공통 댓글 테이블 컬럼 내용", example="내용입니다")
    private String content;
    @Schema(description="공통 댓글 테이블 컬럼 사용_여부", example="Y")
    private String useYn;
}
