package com.study.party.notice;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.exception.UnauthorizedException;
import com.study.party.notice.vo.NoticeCommentVo;
import com.study.party.notice.vo.NoticeDetailVo;
import com.study.party.notice.vo.NoticeHistoryVo;
import com.study.party.notice.vo.NoticeVo;
import com.study.party.team_member.TeamMemberService;
import com.study.party.team_member.vo.TeamMemberVo;
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

    private final TeamMemberService teamMemberService;

    public CommResultVo getNoticeComments(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        return CommResultVo.builder()
                           .code(200)
                           .data(CommPaginationResVo.builder()
                                                    .totalItems(noticeCommentDao.getNoticeCommentsTotCnt(noticeCommentVo))
                                                    .data(noticeCommentDao.getNoticeComments(noticeCommentVo))
                                                    .pageNo(noticeCommentVo.getPageNo())
                                                    .limit(noticeCommentVo.getLimit())
                                                    .build()
                                                    .pagination())
                           .build();
    }

    public CommResultVo getNoticeComment(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        NoticeCommentVo noticeComment = noticeCommentDao.getNoticeComment(noticeCommentVo);
        if (isEmptyObj(noticeComment)) throw new BadRequestException("존재하지 않는 공지사항 댓글입니다");

        if (noticeComment.getMemberIdx() != noticeCommentVo.getMemberIdx()) {
            noticeComment.setViewCount(noticeComment.getViewCount()+1);
            noticeCommentDao.updateNoticeCommentInfoViewCount(noticeComment);
        }

        return CommResultVo.builder()
                           .code(200)
                           .data(noticeComment)
                           .build();
    }

    @Transactional
    public CommResultVo createNoticeComment(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        if ( noticeCommentDao.createNoticeComment(noticeCommentVo) < 1 ) throw new InternalServerErrorException("공지사항 댓글 작성에 실패하였습니다");
        if ( noticeCommentDao.createNoticeCommentInfo(noticeCommentVo) < 1 ) throw new InternalServerErrorException("공지사항 댓글 작성에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("작성 하였습니다").build();
    }

    private void checkTeamMember(TeamMemberVo teamMemberVo) {
        TeamMemberVo teamMember = teamMemberService.getTeamMember(teamMemberVo);
        if (isEmptyObj(teamMember)) throw new UnauthorizedException("참여하지 않은 팀의 공지사항입니다");
    }

    private void checkNotice(NoticeVo noticeVo) {
        NoticeVo notice = noticeDao.getNotice(noticeVo);
        if (isEmptyObj(notice)) throw new BadRequestException("존재하지 않는 공지사항입니다");
    }

}
