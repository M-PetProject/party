package com.study.party.notice;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.BadRequestException;
import com.study.party.notice.vo.NoticeCommentVo;
import com.study.party.notice.vo.NoticeDetailVo;
import com.study.party.notice.vo.NoticeHistoryVo;
import com.study.party.notice.vo.NoticeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Service
@RequiredArgsConstructor
public class NoticeCommentService {

    private final NoticeDao noticeDao;
    private final NoticeCommentDao noticeCommentDao;

    public CommResultVo getNoticeComments(NoticeCommentVo noticeCommentVo) {
        NoticeVo notice = noticeDao.getNotice(noticeCommentVo.toNoticeVo());
        if (isEmptyObj(notice)) throw new BadRequestException("존재하지 않는 공지사항입니다");

        return CommResultVo.builder().build();
    }

}
