package com.study.party.notice;

import com.study.party.comm.comment.CommCommentService;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.exception.UnauthorizedException;
import com.study.party.notice.vo.NoticeDetailVo;
import com.study.party.notice.vo.NoticeHistoryVo;
import com.study.party.notice.vo.NoticeVo;
import com.study.party.notice_comment.NoticeCommentDao;
import com.study.party.notice_comment.vo.NoticeCommentVo;
import com.study.party.team_member.TeamMemberService;
import com.study.party.team_member.vo.TeamMemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeDao noticeDao;
    private final NoticeHistoryDao noticeHistoryDao;
    private final NoticeCommentDao noticeCommentDao;
    private final TeamMemberService teamMemberService;
    private final CommCommentService commCommentService;

    public CommResultVo getNotices(NoticeVo noticeVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeVo.getTeamIdx()).memberIdx(noticeVo.getMemberIdx()).build());

        return CommResultVo.builder()
                           .code(200)
                           .data(CommPaginationResVo.builder()
                                                    .totalItems(noticeDao.getNoticesTotCnt(noticeVo))
                                                    .data(noticeDao.getNotices(noticeVo))
                                                    .pageNo(noticeVo.getPageNo())
                                                    .limit(noticeVo.getLimit())
                                                    .build()
                                                    .pagination())
                           .build();
    }

    public CommResultVo getNotice(NoticeDetailVo noticeDetailVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeDetailVo.getTeamIdx()).memberIdx(noticeDetailVo.getMemberIdx()).build());

        NoticeVo notice = noticeDao.getNotice(noticeDetailVo.toNoticeVo());
        if (isEmptyObj(notice)) throw new BadRequestException("존재하지 않는 공지사항입니다");

        if ( notice.getMemberIdx() != noticeDetailVo.getMemberIdx() ) {
            notice.setViewCount(notice.getViewCount()+1);
            noticeDao.updateNoticeInfoViewCount(notice);
        }

        NoticeDetailVo result = notice.toNoticeDetailVo();
        result.setNoticeComments(commCommentService.getCommentsPagination(NoticeCommentVo.builder()
                                                                                         .postIdx(noticeDetailVo.getNoticeIdx())
                                                                                         .pageNo(noticeDetailVo.getPageNo())
                                                                                         .limit(noticeDetailVo.getLimit())
                                                                                         .build()));
        return CommResultVo.builder().code(200).data(result).build();
    }

    @Transactional
    public CommResultVo createNotice(NoticeVo noticeVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeVo.getTeamIdx()).memberIdx(noticeVo.getMemberIdx()).build());

        if ( noticeDao.createNotice(noticeVo) < 1 ) throw new InternalServerErrorException("공지사항 작성에 실패하였습니다");
        if ( noticeDao.createNoticeInfo(noticeVo) < 1 ) throw new InternalServerErrorException("공지사항 작성에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("작성 하였습니다").build();
    }

    @Transactional
    public CommResultVo updateNotice(NoticeVo noticeVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeVo.getTeamIdx()).memberIdx(noticeVo.getMemberIdx()).build());

        NoticeVo notice = noticeDao.getNotice(noticeVo);
        if (isEmptyObj(notice)) throw new BadRequestException("존재하지 않는 공지사항입니다");

        if ( notice.getMemberIdx() != noticeVo.getMemberIdx() ) throw new UnauthorizedException("수정은 작성자만 가능합니다");

        if (noticeDao.updateNotice(noticeVo) < 1) throw new InternalServerErrorException("공지사항 수정에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("수정 되었습니다").build();
    }

    @Transactional
    public CommResultVo noticeLike(NoticeVo noticeVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeVo.getTeamIdx()).memberIdx(noticeVo.getMemberIdx()).build());

        NoticeVo notice = noticeDao.getNotice(noticeVo);
        if (isEmptyObj(notice)) throw new BadRequestException("존재하지 않는 공지사항입니다");

        // 이미 좋아요를 한 경우
        NoticeHistoryVo likeHistory = noticeHistoryDao.getLikeHistory(noticeVo.toNoticeHistoryVo());
        if (!isEmptyObj(likeHistory)) throw new BadRequestException("이미 좋아요 하셨습니다");

        // 싫어요 이력이 있으면 싫어요 취소
        NoticeHistoryVo unlikeHistory = noticeHistoryDao.getUnlikeHistory(noticeVo.toNoticeHistoryVo());
        if (!isEmptyObj(unlikeHistory)) {
            noticeHistoryDao.deleteUnlikeHistory(noticeVo.toNoticeHistoryVo()); // 싫어요 이력 삭제
            noticeDao.updateNoticeInfoUnlikeCancel(noticeVo); // 싫어요 건수 감소
        }

        if ( noticeHistoryDao.createLikeHistory(noticeVo.toNoticeHistoryVo()) < 1 ) throw new InternalServerErrorException("오류가 발생하였습니다"); // 좋아요 이력 생성
        if ( noticeDao.updateNoticeInfoLike(notice) < 1 ) throw new InternalServerErrorException("오류가 발생하였습니다"); // 좋아요 건수 증가
        return CommResultVo.builder().code(200).msg("좋아요").build();
    }

    @Transactional
    public CommResultVo noticeLikeCancel(NoticeVo noticeVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeVo.getTeamIdx()).memberIdx(noticeVo.getMemberIdx()).build());

        NoticeVo notice = noticeDao.getNotice(noticeVo);
        if (isEmptyObj(notice)) throw new BadRequestException("존재하지 않는 공지사항입니다");

        // 좋아요 이력 확인
        NoticeHistoryVo likeHistory = noticeHistoryDao.getLikeHistory(noticeVo.toNoticeHistoryVo());
        if (isEmptyObj(likeHistory)) throw new BadRequestException("좋아요 한 적이 없습니다"); // 좋아요 이력이 없을 경우

        noticeHistoryDao.deleteLikeHistory(noticeVo.toNoticeHistoryVo()); // 좋아요 이력 삭제
        noticeDao.updateNoticeInfoLikeCancel(notice); // 좋아요 건수 감소
        return CommResultVo.builder().code(200).msg("좋아요 취소").build();
    }

    @Transactional
    public CommResultVo noticeUnlike(NoticeVo noticeVo) {
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeVo.getTeamIdx()).memberIdx(noticeVo.getMemberIdx()).build());

        NoticeVo notice = noticeDao.getNotice(noticeVo);
        if (isEmptyObj(notice)) throw new BadRequestException("존재하지 않는 공지사항입니다");

        // 이미 싫어요를 한 경우
        NoticeHistoryVo unlikeHistory = noticeHistoryDao.getUnlikeHistory(noticeVo.toNoticeHistoryVo());
        if (!isEmptyObj(unlikeHistory)) throw new BadRequestException("이미 싫어요 하셨습니다");

        // 좋아요 이력이 있으면 좋아요 취소
        NoticeHistoryVo likeHistory = noticeHistoryDao.getLikeHistory(noticeVo.toNoticeHistoryVo());
        if (!isEmptyObj(likeHistory)) {
            noticeHistoryDao.deleteLikeHistory(noticeVo.toNoticeHistoryVo()); // 좋아요 이력 삭제
            noticeDao.updateNoticeInfoLikeCancel(noticeVo); // 좋아요 건수 감소
        }

        if ( noticeHistoryDao.createUnlikeHistory(noticeVo.toNoticeHistoryVo()) < 1 ) { // 싫어요 이력 생성
            throw new InternalServerErrorException("오류가 발생하였습니다");
        }
        noticeDao.updateNoticeInfoUnlike(notice); // 싫어요 건수 증가
        return CommResultVo.builder().code(200).msg("싫어요").build();
    }

    @Transactional
    public CommResultVo noticeUnlikeCancel(NoticeVo noticeVo) {
        // 팀에 소속된 회원인지 확인
        checkTeamMember(TeamMemberVo.builder().teamIdx(noticeVo.getTeamIdx()).memberIdx(noticeVo.getMemberIdx()).build());

        // 공지사항 존재 여부 확인
        NoticeVo notice = noticeDao.getNotice(noticeVo);
        if (isEmptyObj(notice)) throw new BadRequestException("존재하지 않는 공지사항입니다");

        // 싫어요 이력 확인
        NoticeHistoryVo unlikeHistory = noticeHistoryDao.getUnlikeHistory(noticeVo.toNoticeHistoryVo());
        if (isEmptyObj(unlikeHistory)) throw new BadRequestException("싫어요 한 적이 없습니다");  // 싫어요 이력이 없을 경우

        noticeHistoryDao.deleteUnlikeHistory(noticeVo.toNoticeHistoryVo()); // 싫어요 이력 삭제
        noticeDao.updateNoticeInfoUnlikeCancel(notice); // 싫어요 건수 감소
        return CommResultVo.builder().code(200).msg("싫어요 취소").build();
    }

    private void checkTeamMember(TeamMemberVo teamMemberVo) {
        TeamMemberVo teamMember = teamMemberService.getTeamMember(teamMemberVo);
        if (isEmptyObj(teamMember)) throw new UnauthorizedException("참여하지 않은 팀의 공지사항입니다");
    }
}
