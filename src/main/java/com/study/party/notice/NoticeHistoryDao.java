package com.study.party.notice;

import com.study.party.notice.vo.NoticeHistoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeHistoryDao {

    NoticeHistoryVo getLikeHistory(NoticeHistoryVo noticeHistoryVo);
    int createLikeHistory(NoticeHistoryVo noticeHistoryVo);
    int deleteLikeHistory(NoticeHistoryVo noticeHistoryVo);
    NoticeHistoryVo getUnlikeHistory(NoticeHistoryVo noticeHistoryVo);
    int createUnlikeHistory(NoticeHistoryVo noticeHistoryVo);
    int deleteUnlikeHistory(NoticeHistoryVo noticeHistoryVo);


}
