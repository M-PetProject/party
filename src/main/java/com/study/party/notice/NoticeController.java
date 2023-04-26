package com.study.party.notice;


import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.notice.vo.NoticeDetailVo;
import com.study.party.notice.vo.NoticeVo;
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
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 목록 정보 조회 API", description = "공지사항 목록 정보를 조회합니다")
    @GetMapping("notices/{team_idx}")
    public ResponseEntity<CommPaginationResVo<List<NoticeVo>>> getNotices(
        HttpServletRequest request,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") Long team_idx,
        @Parameter(name="title" , required=false, description="공지사항 테이블 제목") @RequestParam(name="title" , required=false) String title,
        @Parameter(name="pageNo", required=false, description="현재 페이지 번호") @RequestParam(name="pageNo", required=false, defaultValue="1") int pageNo,
        @Parameter(name="limit" , required=false, description="조회할 갯수 기본 5개") @RequestParam(name="limit" , required=false, defaultValue="5") int limit
    ) {
        if ( isEmptyObj(team_idx) || team_idx < 1 ) return CommResponseVo.builder().body("올바르지 않은 정보가 전달되었습니다").build().badRequest();
        return CommResponseVo.builder()
                             .resultVo(noticeService.getNotices(NoticeVo.builder()
                                                                        .title(title)
                                                                        .teamIdx(team_idx)
                                                                        .pageNo(pageNo)
                                                                        .limit(limit)
                                                                        .build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 상세 조회 API", description = "공지사항 테이블의 PK인 notice_idx를 패스로 전달받아 데이터 1건의 정보를 조회합니다")
    @GetMapping("notice/{notice_idx}")
    public ResponseEntity<NoticeDetailVo> getNotice(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="notice_idx" , required=true, description="공지사항 테이블 PK notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="commentPageNo", required=false, description="현재 페이지 번호") @RequestParam(name="commentPageNo", required=false, defaultValue="1") int commentPageNo,
        @Parameter(name="commentLimit" , required=false, description="조회할 갯수 기본 5개") @RequestParam(name="commentLimit" , required=false, defaultValue="5") int commentLimit
    ) {
        if ( notice_idx < 1 ) return CommResponseVo.builder().body("올바르지 않은 정보가 전달되었습니다").build().badRequest();

        return CommResponseVo.builder()
                             .resultVo(noticeService.getNotice(NoticeDetailVo.builder()
                                                                             .noticeIdx(notice_idx)
                                                                             .pageNo(commentPageNo)
                                                                             .limit(commentLimit)
                                                                             .build(), customUserDetailsVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 정보 생성 API", description = "로그인 사용자의 정보와 공지사항 데이터를 전달받아 공지사항 테이블에 1건의 정보를 생성합니다")
    @PostMapping("notice")
    public ResponseEntity<NoticeVo> createNotice(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @RequestBody NoticeVo noticeVo
    ) {
        if ( isEmptyObj(noticeVo.getTitle()) || isEmptyObj(noticeVo.getContent()) ) return CommResponseVo.builder().body("필수입력값을 확인하세요").build().badRequest();
        noticeVo.setMemberIdx(customUserDetailsVo.getMemberIdx());

        return CommResponseVo.builder()
                             .resultVo(noticeService.createNotice(noticeVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "공지사항 정보 수정 API", description = "로그인 사용자의 정보와 공지사항 데이터를 전달받아 공지사항 테이블에 1건의 정보를 수정합니다")
    @PutMapping("notice")
    public ResponseEntity<NoticeVo> updateNotice(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @RequestBody NoticeVo noticeVo
    ) {
        if ( noticeVo.getNoticeIdx() < 1 || isEmptyObj(noticeVo.getTitle()) || isEmptyObj(noticeVo.getContent()) ) return CommResponseVo.builder().body("필수입력값을 확인하세요").build().badRequest();
        noticeVo.setNoticeDtStart(isEmptyObj(noticeVo.getNoticeDtStart()) ? "20000101000000" : noticeVo.getNoticeDtStart());
        noticeVo.setNoticeDtEnd(  isEmptyObj(noticeVo.getNoticeDtEnd())   ? "99991231235959" : noticeVo.getNoticeDtEnd()  );
        noticeVo.setMemberIdx(customUserDetailsVo.getMemberIdx());
        return CommResponseVo.builder()
                             .resultVo(noticeService.updateNotice(noticeVo))
                             .build()
                             .toResponseEntity();
    }
}
