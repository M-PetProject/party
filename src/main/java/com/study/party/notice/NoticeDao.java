package com.study.party.notice;

import com.study.party.notice.vo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {

    List<NoticeVo> getNotices(NoticeVo noticeVo);
    int getNoticesTotCnt(NoticeVo noticeVo);
    NoticeVo getNotice(NoticeVo noticeVo);
    int createNotice(NoticeVo noticeVo);
    int createNoticeInfo(NoticeVo noticeVo);
    int updateNotice(NoticeVo noticeVo);
    int updateNoticeInfoViewCount(NoticeVo noticeVo);
    int updateNoticeInfoLike(NoticeVo noticeVo);
    int updateNoticeInfoLikeCancel(NoticeVo noticeVo);
    int updateNoticeInfoUnlike(NoticeVo noticeVo);
    int updateNoticeInfoUnlikeCancel(NoticeVo noticeVo);
    int updateNoticeUseYn(NoticeVo noticeVo);

}
