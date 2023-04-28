package com.study.party.notice_comment;


import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.comment.vo.CommCommentVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.exception.BadRequestException;
import com.study.party.notice_comment.vo.NoticeCommentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@RestController
@RequiredArgsConstructor
public class NoticeCommentController {

    private final NoticeCommentService noticeCommentService;

    @Operation(summary = "공지사항 댓글 목록 정보 조회 API", description = "공지사항 댓글 목록 정보를 조회합니다")
    @GetMapping("notice/{team_idx}/{notice_idx}/comments")
    public ResponseEntity<CommPaginationResVo<List<NoticeCommentVo>>> getNoticeComments(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx,
        @Parameter(name="notice_idx", required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="pageNo", required=false, description="현재 페이지 번호") @RequestParam(name="pageNo", required=false, defaultValue="1") int pageNo,
        @Parameter(name="limit", required=false, description="조회할 갯수 기본 5개") @RequestParam(name="limit", required=false, defaultValue="5") int limit
    ) {
        if(team_idx < 1 || notice_idx < 1) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");

        return CommResponseVo.builder()
                             .resultVo(noticeCommentService.getNoticeComments(NoticeCommentVo.builder()
                                                                                             .teamIdx(team_idx)
                                                                                             .noticeIdx(notice_idx)
                                                                                             .postIdx(notice_idx)
                                                                                             .memberIdx(customUserDetailsVo.getMemberIdx())
                                                                                             .pageNo(pageNo)
                                                                                             .limit(limit)
                                                                                             .build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 댓글 상세 정보 조회 API", description = "공지사항 댓글 상세 정보를 조회합니다")
    @GetMapping("notice/{team_idx}/{notice_idx}/comment/{comment_idx}")
    public ResponseEntity<CommCommentVo> getNoticeComment(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx,
        @Parameter(name="notice_idx", required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="comment_idx", required=true, description="공통 댓글 테이블 컬럼 comment_idx") @PathVariable(name="comment_idx") long comment_idx
    ) {
        if(team_idx < 1 || notice_idx < 1 || comment_idx < 1) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");

        return CommResponseVo.builder()
                             .resultVo(noticeCommentService.getNoticeComment(NoticeCommentVo.builder()
                                                                                            .teamIdx(team_idx)
                                                                                            .noticeIdx(notice_idx)
                                                                                            .postIdx(notice_idx)
                                                                                            .commentIdx(comment_idx)
                                                                                            .memberIdx(customUserDetailsVo.getMemberIdx())
                                                                                            .build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 댓글 정보 생성 API", description = "로그인 사용자의 정보와 공지사항 댓글 데이터를 전달받아 공지사항 댓글 데이터를 1건 생성합니다")
    @PostMapping("notice/{team_idx}/{notice_idx}/comment")
    public ResponseEntity<NoticeCommentVo> createNoticeComment(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx,
        @Parameter(name="notice_idx", required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @RequestBody NoticeCommentVo noticeCommentVo
    ) {
        if(team_idx < 1 || notice_idx < 1 || isEmptyObj(noticeCommentVo.getTitle()) || isEmptyObj(noticeCommentVo.getContent())) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");

        noticeCommentVo.setTeamIdx(team_idx);
        noticeCommentVo.setPostIdx(notice_idx);
        noticeCommentVo.setNoticeIdx(notice_idx);
        noticeCommentVo.setMemberIdx(customUserDetailsVo.getMemberIdx());
        return CommResponseVo.builder()
                             .resultVo(noticeCommentService.createNoticeComment(noticeCommentVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 댓글 정보 수정 API", description = "로그인 사용자의 정보와 공지사항 댓글 데이터를 전달받아 공지사항 댓글 데이터를 1건 수정합니다")
    @PutMapping("notice/{team_idx}/{notice_idx}/comment/{comment_idx}")
    public ResponseEntity<NoticeCommentVo> updateNoticeComment(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx,
        @Parameter(name="notice_idx", required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="comment_idx", required=true, description="공통 댓글 테이블 컬럼 comment_idx") @PathVariable(name="comment_idx") long comment_idx,
        @RequestBody NoticeCommentVo noticeCommentVo
    ) {
        if(team_idx < 1 || notice_idx < 1 || comment_idx < 1 || isEmptyObj(noticeCommentVo.getTitle()) || isEmptyObj(noticeCommentVo.getContent())) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");

        noticeCommentVo.setTeamIdx(team_idx);
        noticeCommentVo.setPostIdx(notice_idx);
        noticeCommentVo.setNoticeIdx(notice_idx);
        noticeCommentVo.setMemberIdx(customUserDetailsVo.getMemberIdx());
        noticeCommentVo.setCommentIdx(comment_idx);
        return CommResponseVo.builder()
                             .resultVo(noticeCommentService.updateNoticeComment(noticeCommentVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 댓글 좋아요 API", description = "로그인 사용자의 정보와 공지사항 댓글 데이터를 전달받아 공지사항 댓글 좋아요 기능을 수행합니다")
    @PostMapping("notice/{team_idx}/{notice_idx}/comment/{comment_idx}/like")
    public ResponseEntity<NoticeCommentVo> noticeCommentLike(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx,
        @Parameter(name="notice_idx", required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="comment_idx", required=true, description="공통 댓글 테이블 컬럼 comment_idx") @PathVariable(name="comment_idx") long comment_idx
    ) {
        if(team_idx < 1 || notice_idx < 1 || comment_idx < 1 ) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");

        return CommResponseVo.builder()
                             .resultVo(noticeCommentService.noticeCommentLike(NoticeCommentVo.builder()
                                                                                             .commentIdx(comment_idx)
                                                                                             .noticeIdx(notice_idx)
                                                                                             .teamIdx(team_idx)
                                                                                             .memberIdx(customUserDetailsVo.getMemberIdx())
                                                                                             .build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 댓글 좋아요 취소 API", description = "로그인 사용자의 정보와 공지사항 댓글 데이터를 전달받아 공지사항 댓글 좋아요 취소 기능을 수행합니다")
    @DeleteMapping("notice/{team_idx}/{notice_idx}/comment/{comment_idx}/like")
    public ResponseEntity<NoticeCommentVo> noticeCommentLikeCancel(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx,
        @Parameter(name="notice_idx", required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="comment_idx", required=true, description="공통 댓글 테이블 컬럼 comment_idx") @PathVariable(name="comment_idx") long comment_idx
    ) {
        if(team_idx < 1 || notice_idx < 1 || comment_idx < 1 ) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");

        return CommResponseVo.builder()
                             .resultVo(noticeCommentService.noticeCommentLikeCancel(NoticeCommentVo.builder()
                                                                                                   .commentIdx(comment_idx)
                                                                                                   .noticeIdx(notice_idx)
                                                                                                   .teamIdx(team_idx)
                                                                                                   .memberIdx(customUserDetailsVo.getMemberIdx())
                                                                                                   .build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 댓글 싫어요 API", description = "로그인 사용자의 정보와 공지사항 댓글 데이터를 전달받아 공지사항 댓글 싫어요 기능을 수행합니다")
    @PostMapping("notice/{team_idx}/{notice_idx}/comment/{comment_idx}/unlike")
    public ResponseEntity<NoticeCommentVo> noticeCommentUnlike(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx,
        @Parameter(name="notice_idx", required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="comment_idx", required=true, description="공통 댓글 테이블 컬럼 comment_idx") @PathVariable(name="comment_idx") long comment_idx
    ) {
        if(team_idx < 1 || notice_idx < 1 || comment_idx < 1 ) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");

        return CommResponseVo.builder()
                             .resultVo(noticeCommentService.noticeCommentUnlike(NoticeCommentVo.builder()
                                                                                             .commentIdx(comment_idx)
                                                                                             .noticeIdx(notice_idx)
                                                                                             .teamIdx(team_idx)
                                                                                             .memberIdx(customUserDetailsVo.getMemberIdx())
                                                                                             .build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 댓글 싫어요 취소 API", description = "로그인 사용자의 정보와 공지사항 댓글 데이터를 전달받아 공지사항 댓글 싫어요 취소 기능을 수행합니다")
    @DeleteMapping("notice/{team_idx}/{notice_idx}/comment/{comment_idx}/unlike")
    public ResponseEntity<NoticeCommentVo> noticeCommentUnlikeCancel(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx,
        @Parameter(name="notice_idx", required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="comment_idx", required=true, description="공지사항 댓글 테이블 컬럼 comment_idx") @PathVariable(name="comment_idx") long comment_idx
    ) {
        if(team_idx < 1 || notice_idx < 1 || comment_idx < 1 ) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");

        return CommResponseVo.builder()
                             .resultVo(noticeCommentService.noticeCommentUnlikeCancel(NoticeCommentVo.builder()
                                                                                                     .commentIdx(comment_idx)
                                                                                                     .noticeIdx(notice_idx)
                                                                                                     .teamIdx(team_idx)
                                                                                                     .memberIdx(customUserDetailsVo.getMemberIdx())
                                                                                                     .build()))
                             .build()
                             .toResponseEntity();
    }

}
