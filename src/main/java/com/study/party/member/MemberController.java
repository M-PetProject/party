package com.study.party.member;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.member.vo.MemberVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인 회원 정보 조회 API", description = "로그인 사용자의 Token 값 기반으로 테이블 member_info 의 데이터 1건의 정보를 조회합니다")
    @GetMapping("/member")
    public ResponseEntity<MemberVo> getMember(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo
    ) {
        return CommResponseVo.builder()
                             .resultVo(memberService.getMember(MemberVo.builder().memberIdx(customUserDetailsVo.getMemberIdx()).build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "로그인 회원 정보 수정 API", description = "로그인 사용자의 Token 값 기반으로 변경 정보를 파라미터로 전달 받아 테이블 member_info 의 데이터 1건의 정보를 수정합니다")
    @PutMapping("/member")
    public ResponseEntity<MemberVo> updateMember(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @RequestBody MemberVo memberVo
    ) {
        if ( isEmptyObj(memberVo.getMemberId()) || isEmptyObj(memberVo.getMemberName()) ) {
            return CommResponseVo.builder().body("필수입력값을 확인하세요").build().badRequest();
        }
        memberVo.setMemberIdx(customUserDetailsVo.getMemberIdx());
        return CommResponseVo.builder()
                             .resultVo(memberService.updateMember(memberVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "회원 ID 기반 정보 조회 API", description = "테이블 member_info 의 유니크 키인 member_id 를 파라미터로 전달받아 멤버 정보를 조회합니다")
    @GetMapping("/member/{member_id}")
    public ResponseEntity<MemberVo> getMemberByMemberId(
        HttpServletRequest request,
        @Parameter(name="member_id" , required=true, description="조회할 회원 ID") @PathVariable("member_id") String member_id
    ) {
        return CommResponseVo.builder()
                             .resultVo(memberService.getMemberByMemberId(MemberVo.builder().memberId(member_id).build()))
                             .build()
                             .toResponseEntity();
    }

}
