package com.study.party.notice_comment.vo;

import com.study.party.comm.comment.vo.CommCommentVo;
import com.study.party.notice.vo.NoticeVo;
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
public class NoticeCommentVo extends CommCommentVo {

    @Schema(description="공지사항 댓글 테이블 컬럼 공지사항_IDX", example="1")
    private long teamIdx;

    @Schema(description="공지사항 댓글 테이블 컬럼 공지사항_IDX", example="1")
    private long noticeIdx;

    public String getCommentCd() {
        return "NOTC";
    }

    public NoticeVo toNoticeVo() {
        return NoticeVo.builder().noticeIdx(getNoticeIdx()).build();
    }

}
