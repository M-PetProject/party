package com.study.party.notice.vo;

import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.notice_comment.vo.NoticeCommentVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="공지사항 VO")
public class NoticeDetailVo extends NoticeVo {

    @Schema(description="공지사항_댓글 테이블 목록")
    private CommPaginationResVo<List<NoticeCommentVo>> NoticeComments;

    public NoticeVo toNoticeVo() {
        return NoticeVo.builder().noticeIdx(getNoticeIdx()).build();
    }

    public NoticeCommentVo toNoticeCommentVo() {
        return NoticeCommentVo.builder().noticeIdx(getNoticeIdx()).pageNo(getPageNo()).limit(getLimit()).build();
    }
}
