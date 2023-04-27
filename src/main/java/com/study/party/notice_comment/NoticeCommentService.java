package com.study.party.notice_comment;

import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.exception.UnauthorizedException;
import com.study.party.notice.NoticeDao;
import com.study.party.notice.vo.NoticeVo;
import com.study.party.notice_comment.vo.NoticeCommentHistoryVo;
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
    private final NoticeCommentDao noticeCommentDao;
    private final NoticeCommentHistoryDao noticeCommentHistoryDao;

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

    @Transactional
    public CommResultVo updateNoticeComment(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        NoticeCommentVo noticeComment = noticeCommentDao.getNoticeComment(noticeCommentVo);
        if (isEmptyObj(noticeComment)) throw new BadRequestException("존재하지 않는 공지사항 댓글입니다");

        if (noticeComment.getMemberIdx() != noticeCommentVo.getMemberIdx()) throw new UnauthorizedException("수정은 작성자만 가능합니다");

        if ( noticeCommentDao.updateNoticeComment(noticeCommentVo) < 1 ) throw new InternalServerErrorException("공지사항 댓글 작성에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("수정 되었습니다").build();
    }

    @Transactional
    public CommResultVo noticeCommentLike(NoticeCommentVo noticeCommentVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeCommentVo.getTeamIdx()).memberIdx(noticeCommentVo.getMemberIdx()).build());
        checkNotice(noticeCommentVo.toNoticeVo());

        NoticeCommentVo noticeComment = noticeCommentDao.getNoticeComment(noticeCommentVo);
        if (isEmptyObj(noticeComment)) throw new BadRequestException("존재하지 않는 공지사항 댓글입니다");

        // 이미 좋아요를 한 경우
        NoticeCommentHistoryVo likeHistory = noticeCommentHistoryDao.getLikeHistory(noticeCommentVo.toNoticeCommentHistoryVo());
        if (!isEmptyObj(likeHistory)) throw new BadRequestException("이미 좋아요 하셨습니다");

        // 싫어요 이력이 있으면 싫어요 취소
        NoticeCommentHistoryVo unlikeHistory = noticeCommentHistoryDao.getUnlikeHistory(noticeCommentVo.toNoticeCommentHistoryVo());
        if (!isEmptyObj(unlikeHistory)) {
            noticeCommentHistoryDao.deleteUnlikeHistory(noticeCommentVo.toNoticeCommentHistoryVo()); // 싫어요 이력 삭제
            noticeCommentDao.updateNoticeCommentInfoUnlikeCancel(noticeCommentVo); // 싫어요 건수 감소
        }

        if (noticeCommentHistoryDao.createLikeHistory(noticeCommentVo.toNoticeCommentHistoryVo()) < 1) throw new InternalServerErrorException("오류가 발생하였습니다"); // 좋아요 이력 생성
        if (noticeCommentDao.updateNoticeCommentInfoLike(noticeCommentVo) < 1) throw new InternalServerErrorException("오류가 발생하였습니다"); // 좋아요 건수 증가
        return CommResultVo.builder().code(200).msg("좋아요").build();
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
