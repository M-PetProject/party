package com.study.party.comm.comment;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.comment.vo.CommCommentSaveReqVo;
import com.study.party.comm.comment.vo.CommCommentVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.jpa.entity.cm_allergy.CmAllergyEntity;
import com.study.party.notice_comment.vo.NoticeCommentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comm")
public class CommCommentController {

    private final CommCommentService commCommentService;

    @Operation(summary = "공통 댓글 목록 정보 조회 API", description = "공통 댓글목록")
    @GetMapping("comment/{commentType}/{postIdx}")
    public ResponseEntity<List<CmAllergyEntity>> getCommComments(
            HttpServletRequest request,
            @Parameter(name="commentType", required=true, description="댓글 타입")    @PathVariable("commentType") String commentType,
            @Parameter(name="postIdx", required=true, description="댓글의 게시글 IDX") @PathVariable("postIdx")     long postIdx,
            @Parameter(name="pageNo", required=false, description="현재 페이지 번호")   @RequestParam(name="pageNo", required=false, defaultValue="1") int pageNo,
            @Parameter(name="limit", required=false, description="조회할 갯수 기본 5개") @RequestParam(name="limit", required=false, defaultValue="5") int limit
    ) {
        CommCommentVo commCommentVo = CommCommentVo.builder()
                .postIdx(postIdx)
                .commentCd(commentType)
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return CommResponseVo.builder()
                .body(commCommentService.getCommentsPagination(commCommentVo))
                .build().ok();
    }

    @Operation(summary = "공통 댓글 작성 API", description = "공통 댓글작성")
    @PostMapping("comment")
    public ResponseEntity<CommCommentVo> createCommComment(
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
            @RequestBody CommCommentSaveReqVo saveReqVo
    ) {
        CommCommentVo commCommentVo = CommCommentVo.builder()
                .commentCd(saveReqVo.getCommentCd())
                .postIdx(saveReqVo.getPostIdx())
                .memberIdx(customUserDetailsVo.getMemberIdx())
                .title(saveReqVo.getTitle())
                .content(saveReqVo.getContent())
                .useYn(saveReqVo.getUseYn())
                .build();
        commCommentService.createComment(commCommentVo);
        return CommResponseVo.builder()
                .resultVo(CommResultVo.builder().code(200).msg("작성 하였습니다").build())
                .build()
                .toResponseEntity();
    }
}
