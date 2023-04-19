package com.study.party.member;


import com.study.party.comm.code.vo.CommGrpCodeVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.member.vo.MemberVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiResponses({
            @ApiResponse(responseCode="200", description="OK"),
            @ApiResponse(responseCode="400", description="BAD Request"),
            @ApiResponse(responseCode="404", description="NOT FOUND"),
            @ApiResponse(responseCode="500", description="INTERNAL SERVER ERROR"),
    })
    @Operation(summary = "회원 ID 기반 정보 조회 API", description = "테이블 member_info 의 유니크 키인 member_id 를 파라미터로 전달받아 멤버 정보를 조회합니다")
    @GetMapping("/member/{member_id}")
    public ResponseEntity getMemberByMemberId(
        HttpServletRequest request,
        @PathVariable("member_id") String member_id
    ) {
        return CommResponseVo.builder()
                             .body(memberService.getMemberByMemberId(MemberVo.builder().memberId(member_id).build()))
                             .build()
                             .ok();
    }

}
