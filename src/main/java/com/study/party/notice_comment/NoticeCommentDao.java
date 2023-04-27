package com.study.party.notice_comment;

import com.study.party.notice_comment.vo.NoticeCommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeCommentDao {

    List<NoticeCommentVo> getNoticeComments(NoticeCommentVo noticeCommentVo);
    int getNoticeCommentsTotCnt(NoticeCommentVo noticeCommentVo);
    NoticeCommentVo getNoticeComment(NoticeCommentVo noticeCommentVo);
    int createNoticeComment(NoticeCommentVo noticeCommentVo);
    int createNoticeCommentInfo(NoticeCommentVo noticeCommentVo);
    int updateNoticeComment(NoticeCommentVo noticeCommentVo);
    int updateNoticeCommentInfoViewCount(NoticeCommentVo noticeCommentVo);
    int updateNoticeCommentInfoLike(NoticeCommentVo noticeCommentVo);
    int updateNoticeCommentInfoLikeCancel(NoticeCommentVo noticeCommentVo);
    int updateNoticeCommentInfoUnlike(NoticeCommentVo noticeCommentVo);
    int updateNoticeCommentInfoUnlikeCancel(NoticeCommentVo noticeCommentVo);
    int deleteNoticeComment(NoticeCommentVo noticeCommentVo);
    int deleteNoticeCommentInfo(NoticeCommentVo noticeCommentVo);

}
