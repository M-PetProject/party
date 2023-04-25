package com.study.party.notice.vo;

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

    @Schema(description="공지사항 댓글 테이블 PK IDX", defaultValue="1")
    private long noticeCommentIdx;

    @Schema(description="공지사항 댓글 테이블 컬럼 공지사항_IDX", defaultValue="1")
    private long noticeIdx;

    @Schema(description="공지사항 댓글 테이블 컬럼 멤버_IDX(작성자)", defaultValue="1")
    private long memberIdx;

    @Schema(description="공지사항 댓글 테이블 컬럼 제목", defaultValue="제목")
    private String title;

    @Schema(description="공지사항 댓글 테이블 컬럼 내용", defaultValue="내용")
    private String content;

    @Schema(description="공지사항 댓글 테이블 컬럼 사용여부", defaultValue="Y")
    private String useYn;

    @Schema(description="공지사항 댓글_정보 테이블 컬럼 조회수", defaultValue="1")
    private long viewCount;

    @Schema(description="공지사항 댓글_정보 테이블 컬럼 좋아요 수", defaultValue="1")
    private long likeCount;

    @Schema(description="공지사항 댓글_정보 테이블 컬럼 싫어요 수", defaultValue="1")
    private long unlikeCount;

}
