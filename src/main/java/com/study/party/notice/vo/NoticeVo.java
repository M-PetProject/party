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
@Schema(description="공지사항 VO")
public class NoticeVo extends CommPaginationReqVo {

    @Schema(description="공지사항 테이블 PK IDX", example="1")
    private long noticeIdx;

    @Schema(description="공지사항 테이블 컬럼 팀_IDX", example="1")
    private long teamIdx;

    @Schema(description="공지사항 테이블 컬럼 멤버_IDX(작성자)", example="1")
    private long memberIdx;

    @Schema(description="공지사항 테이블 컬럼 제목", example="제목")
    private String title;

    @Schema(description="공지사항 테이블 컬럼 내용", example="내용")
    private String content;

    @Schema(description="공지사항 테이블 컬럼 공지사항 게시 시작일자", example="20000101000000")
    private String noticeDtStart;

    @Schema(description="공지사항 테이블 컬럼 공지사항 게시 종료일자", example="99991231235959")
    private String noticeDtEnd;

    @Schema(description="공지사항 테이블 컬럼 공지사항 사용여부", example="Y")
    private String useYn;

    @Schema(description="공지사항_정보 테이블 컬럼 조회수", example="1")
    private long viewCount;

    @Schema(description="공지사항_정보 테이블 컬럼 좋아요 수", example="1")
    private long likeCount;

    @Schema(description="공지사항_정보 테이블 컬럼 싫어요 수", example="1")
    private long unlikeCount;

    public NoticeDetailVo toNoticeDetailVo() {
        return NoticeDetailVo.builder()
                             .noticeIdx(this.noticeIdx)
                             .teamIdx(this.teamIdx)
                             .memberIdx(this.memberIdx)
                             .title(this.title)
                             .noticeDtStart(this.noticeDtStart)
                             .noticeDtEnd(this.noticeDtEnd)
                             .useYn(this.useYn)
                             .viewCount(this.viewCount)
                             .likeCount(this.likeCount)
                             .unlikeCount(this.unlikeCount)
                             .build();
    }
}
