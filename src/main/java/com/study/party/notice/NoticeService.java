package com.study.party.notice;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.notice.vo.NoticeCommentVo;
import com.study.party.notice.vo.NoticeDetailVo;
import com.study.party.notice.vo.NoticeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeDao noticeDao;

    private final NoticeCommentDao noticeCommentDao;

    public CommPaginationResVo getNotices(NoticeVo noticeVo) {
        return CommPaginationResVo.builder()
                                  .totalItems(noticeDao.getNoticesTotCnt(noticeVo))
                                  .data(noticeDao.getNotices(noticeVo))
                                  .pageNo(noticeVo.getPageNo())
                                  .limit(noticeVo.getLimit())
                                  .build()
                                  .pagination();

    }

    public NoticeDetailVo getNotice(NoticeDetailVo noticeDetailVo, CustomUserDetailsVo customUserDetailsVo) {
        NoticeVo notice = noticeDao.getNotice(noticeDetailVo.toNoticeVo());
        if (isEmptyObj(notice)) {
            return null;
        }
        if ( notice.getMemberIdx() != customUserDetailsVo.getMemberIdx() ) {
            notice.setViewCount(notice.getViewCount()+1);
            noticeDao.updateNoticeInfoViewCount(notice);
        }

        NoticeDetailVo result = notice.toNoticeDetailVo();
        result.setNoticeComments(CommPaginationResVo.builder()
                                                    .totalItems(noticeCommentDao.getNoticeCommentsTotCnt(noticeDetailVo.toNoticeCommentVo()))
                                                    .data(noticeCommentDao.getNoticeComments(noticeDetailVo.toNoticeCommentVo()))
                                                    .pageNo(noticeDetailVo.getPageNo())
                                                    .limit(noticeDetailVo.getLimit())
                                                    .build()
                                                    .pagination());
        return result;
    }


    public int createNotice(NoticeVo noticeVo) {
        noticeDao.createNotice(noticeVo);
        noticeDao.createNoticeInfo(noticeVo);
        return 1;
    }
}
