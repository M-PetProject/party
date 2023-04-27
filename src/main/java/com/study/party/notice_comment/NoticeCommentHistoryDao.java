package com.study.party.notice_comment;

import com.study.party.notice_comment.vo.NoticeCommentHistoryVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeCommentHistoryDao {

    NoticeCommentHistoryVo getLikeHistory(NoticeCommentHistoryVo noticeCommentHistoryVo);
    int createLikeHistory(NoticeCommentHistoryVo noticeCommentHistoryVo);
    int deleteLikeHistory(NoticeCommentHistoryVo noticeCommentHistoryVo);
    NoticeCommentHistoryVo getUnlikeHistory(NoticeCommentHistoryVo noticeCommentHistoryVo);
    int createUnlikeHistory(NoticeCommentHistoryVo noticeCommentHistoryVo);
    int deleteUnlikeHistory(NoticeCommentHistoryVo noticeCommentHistoryVo);

}
