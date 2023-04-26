package com.study.party.notice;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResultVo;
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

    public CommResultVo getNotices(NoticeVo noticeVo) {
        return CommResultVo.builder().code(200).data(CommPaginationResVo.builder()
                                                                        .totalItems(noticeDao.getNoticesTotCnt(noticeVo))
                                                                        .data(noticeDao.getNotices(noticeVo))
                                                                        .pageNo(noticeVo.getPageNo())
                                                                        .limit(noticeVo.getLimit())
                                                                        .build()
                                                                        .pagination()).build();
    }

    public CommResultVo getNotice(NoticeDetailVo noticeDetailVo, CustomUserDetailsVo customUserDetailsVo) {
        NoticeVo notice = noticeDao.getNotice(noticeDetailVo.toNoticeVo());
        if (isEmptyObj(notice)) {
            return CommResultVo.builder().code(400).msg("존재하지 않는 공지사항입니다").build();
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
        return CommResultVo.builder().code(200).data(result).build();
    }


    public CommResultVo createNotice(NoticeVo noticeVo) {
        if ( noticeDao.createNotice(noticeVo) < 0 ) {
            return CommResultVo.builder().code(500).msg("공지사항 작성에 실패하였습니다").build();
        }

        if ( noticeDao.createNoticeInfo(noticeVo) < 0 ) {
            noticeDao.deleteNotice(noticeVo);
            return CommResultVo.builder().code(500).msg("공지사항 작성에 실패하였습니다").build();
        }
        return CommResultVo.builder().code(200).msg("작성 하였습니다").build();
    }


    public CommResultVo updateNotice(NoticeVo noticeVo) {
        NoticeVo notice = noticeDao.getNotice(noticeVo);
        if (isEmptyObj(notice)) {
            return CommResultVo.builder().code(400).msg("존재하지 않는 공지사항입니다").build();
        }

        if ( notice.getMemberIdx() != noticeVo.getMemberIdx() ) {
            return CommResultVo.builder().code(401).msg("수정은 작성자만 가능합니다").build();
        }

        noticeDao.updateNotice(noticeVo);
        return CommResultVo.builder().code(200).msg("수정 되었습니다").build();
    }
}
