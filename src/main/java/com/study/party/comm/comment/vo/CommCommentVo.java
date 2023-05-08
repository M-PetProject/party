package com.study.party.comm.comment.vo;

import com.study.party.comm.vo.CommPaginationReqVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="공통 댓글 VO")
public class CommCommentVo extends CommPaginationReqVo {

    @Schema(description="공통 댓글 테이블 PK IDX", example="1")
    private long commentIdx;
    @Schema(description="공통 댓글 테이블 컬럼 댓글_코드(G:CMMT)", example="NOTC")
    private String commentCd;
    @Schema(description="공통 댓글 테이블 컬럼 댓글_코드_명", example="공지사항")
    private String commentCdNm;
    @Schema(description="공통 댓글 테이블 컬럼 게시글_IDX", example="1")
    private long postIdx;
    @Schema(description="공통 댓글 테이블 컬럼 회원_IDX", example="1")
    private long memberIdx;
    @Schema(description="공통 댓글 테이블 컬럼 회원_ID", example="admin")
    private String memberId;
    @Schema(description="공통 댓글 테이블 컬럼 회원_이름", example="홍길동")
    private String memberName;
    @Schema(description="공통 댓글 테이블 컬럼 제목", example="제목입니다")
    private String title;
    @Schema(description="공통 댓글 테이블 컬럼 내용", example="내용입니다")
    private String content;
    @Schema(description="공통 댓글 테이블 컬럼 상위_댓글_IDX", example="0")
    private long parentCommentIdx;
    @Schema(description="공통 댓글 테이블 컬럼 사용_여부", example="Y")
    private String useYn;
    @Schema(description="공통 댓글 테이블 컬럼 등록일자", example="yyyyMMddHHmmss")
    private String regDate;
    @Schema(description="공통 댓글 테이블 컬럼 조회수", example="0")
    private long viewCount;
    @Schema(description="공통 댓글 테이블 컬럼 좋아요 수", example="0")
    private long likeCount;
    @Schema(description="공통 댓글 테이블 컬럼 싫어요 수", example="0")
    private long unlikeCount;

    @Schema(description="공통 댓글 하위 댓글 수", example="0")
    private long childrenCnt;

    @Schema(description="공통 댓글 좋아요 여부", example="N")
    private String likeYn;

    @Schema(description="공통 댓글 싫어요 여부", example="N")
    private String unlikeYn;

    public CommCommentEmotionVo toCommCommentEmotionVo() {
        return CommCommentEmotionVo.builder().commentIdx(this.commentIdx).memberIdx(this.memberIdx).build();
    }

}
