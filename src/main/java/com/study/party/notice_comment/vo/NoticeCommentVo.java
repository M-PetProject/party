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

    public NoticeCommentHistoryVo toNoticeCommentHistoryVo() {
        return NoticeCommentHistoryVo.builder()
                                     .noticeCommentIdx(getCommentIdx())
                                     .memberIdx(getMemberIdx())
                                     .build();
    }

    public static NoticeCommentVo fromCommCommentVo(CommCommentVo commCommentVo) {
        return NoticeCommentVo.builder()
                              .commentIdx(commCommentVo.getCommentIdx())
                              .commentCd(commCommentVo.getCommentCd())
                              .commentCdNm(commCommentVo.getCommentCdNm())
                              .postIdx(commCommentVo.getPostIdx())
                              .memberIdx(commCommentVo.getMemberIdx())
                              .title(commCommentVo.getTitle())
                              .content(commCommentVo.getContent())
                              .parentCommentIdx(commCommentVo.getParentCommentIdx())
                              .useYn(commCommentVo.getUseYn())
                              .regDate(commCommentVo.getRegDate())
                              .viewCount(commCommentVo.getViewCount())
                              .likeCount(commCommentVo.getLikeCount())
                              .unlikeCount(commCommentVo.getUnlikeCount())
                              .childrenCnt(commCommentVo.getChildrenCnt())
                              .likeYn(commCommentVo.getLikeYn())
                              .unlikeYn(commCommentVo.getUnlikeYn())
                              .noticeIdx(commCommentVo.getPostIdx())
                              .build();
    }
}
