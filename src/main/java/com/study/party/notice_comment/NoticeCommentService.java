package com.study.party.notice_comment;

import com.study.party.comm.comment.CommCommentService;
import com.study.party.comm.comment.vo.CommCommentVo;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.UnauthorizedException;
import com.study.party.notice.NoticeDao;
import com.study.party.notice.vo.NoticeVo;
import com.study.party.notice_comment.vo.NoticeCommentVo;
import com.study.party.team_member.TeamMemberService;
import com.study.party.team_member.vo.TeamMemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Service
@RequiredArgsConstructor
public class NoticeCommentService {

    private final NoticeDao noticeDao;
    private final TeamMemberService teamMemberService;
    private final CommCommentService commCommentService;

    public CommResultVo getNoticeComments(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(
                TeamMemberVo
                                .builder()
                                .teamIdx(noticeCommentVo.getTeamIdx())
                                .memberIdx(noticeCommentVo.getMemberIdx())
                                .build()
        );
        checkNotice(noticeCommentVo.toNoticeVo());

        return CommResultVo.builder()
                           .code(200)
                           .data(commCommentService.getCommentsPagination(noticeCommentVo))
                           .build();
    }

    public CommResultVo getNoticeComment(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        CommCommentVo noticeComment = commCommentService.getComment(noticeCommentVo); // 댓글 가져오기
        commCommentService.commentView(noticeCommentVo); // 조회수 증가

        return CommResultVo.builder()
                           .code(200)
                           .data(noticeComment)
                           .build();
    }

    @Transactional
    public CommResultVo createNoticeComment(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        commCommentService.createComment(noticeCommentVo);
        return CommResultVo.builder().code(200).msg("작성 하였습니다").build();
    }

    @Transactional
    public CommResultVo updateNoticeComment(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        commCommentService.updateComment(noticeCommentVo);
        return CommResultVo.builder().code(200).msg("수정 되었습니다").build();
    }

    @Transactional
    public CommResultVo updateNoticeCommentUseYn(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        commCommentService.updateCommentUseYn(noticeCommentVo);
        return CommResultVo.builder().code(200).msg("수정 되었습니다").build();
    }

    @Transactional
    public CommResultVo noticeCommentLike(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        commCommentService.commentLike(noticeCommentVo);
        return CommResultVo.builder().code(200).msg("좋아요").build();
    }

    @Transactional
    public CommResultVo noticeCommentLikeCancel(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        commCommentService.commentLikeCancel(noticeCommentVo);
        return CommResultVo.builder().code(200).msg("좋아요 취소").build();
    }

    @Transactional
    public CommResultVo noticeCommentUnlike(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        commCommentService.commentUnlike(noticeCommentVo);
        return CommResultVo.builder().code(200).msg("싫어요").build();
    }

    @Transactional
    public CommResultVo noticeCommentUnlikeCancel(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        commCommentService.commentUnlikeCancel(noticeCommentVo);
        return CommResultVo.builder().code(200).msg("싫어요 취소").build();
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
