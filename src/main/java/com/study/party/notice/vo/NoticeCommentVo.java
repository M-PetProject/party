package com.study.party.notice.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Schema(description="공지사항 댓글 VO")
public class NoticeCommentVo extends CommPaginationReqVo {

    @Schema(description="공지사항 댓글 테이블 PK IDX", example="1")
    private long noticeCommentIdx;

    @JsonIgnore
    private long teamIdx;

    @Schema(description="공지사항 댓글 테이블 컬럼 공지사항_IDX", example="1")
    private long noticeIdx;

    @Schema(description="공지사항 댓글 테이블 컬럼 멤버_IDX(작성자)", example="1")
    private long memberIdx;

    @Schema(description="공지사항 댓글 테이블 컬럼 제목", example="제목")
    private String title;

    @Schema(description="공지사항 댓글 테이블 컬럼 내용", example="내용")
    private String content;

    @Schema(description="공지사항 댓글 테이블 컬럼 사용여부", example="Y")
    private String useYn;

    @Schema(description="공지사항 댓글_정보 테이블 컬럼 조회수", example="1")
    private long viewCount;

    @Schema(description="공지사항 댓글_정보 테이블 컬럼 좋아요 수", example="1")
    private long likeCount;

    @Schema(description="공지사항 댓글_정보 테이블 컬럼 싫어요 수", example="1")
    private long unlikeCount;


    public NoticeVo toNoticeVo() {
        return NoticeVo.builder().noticeIdx(getNoticeIdx()).build();
    }

}
