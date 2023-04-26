package com.study.party.notice;


import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.exception.BadRequestException;
import com.study.party.notice.vo.NoticeCommentVo;
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
public class NoticeCommentController {

    private final NoticeCommentService noticeCommentService;

    @Operation(summary = "공지사항 댓글 목록 정보 조회 API", description = "공지사항 댓글 목록 정보를 조회합니다")
    @GetMapping("notice/{notice_idx}/comments")
    public ResponseEntity<CommPaginationResVo<List<NoticeCommentVo>>> getNoticeComments(
        HttpServletRequest request,
        @Parameter(name="notice_idx" , required=true, description="공지사항 댓글 테이블 컬럼 notice_idx") @PathVariable(name="notice_idx") long notice_idx,
        @Parameter(name="pageNo", required=false, description="현재 페이지 번호") @RequestParam(name="pageNo", required=false, defaultValue="1") int pageNo,
        @Parameter(name="limit" , required=false, description="조회할 갯수 기본 5개") @RequestParam(name="limit" , required=false, defaultValue="5") int limit
    ) {
        if(notice_idx < 1) throw new BadRequestException("올바르지 않은 정보가 전달되었습니다");
        return CommResponseVo.builder().build().toResponseEntity();
    }
}
