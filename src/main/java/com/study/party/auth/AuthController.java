package com.study.party.auth;

import com.study.party.auth.vo.LoginReqVo;
import com.study.party.auth.vo.LoginResVo;
import com.study.party.auth.vo.SignupReqVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.comm.vo.TokenVo;
import com.study.party.exception.BadRequestException;
import com.study.party.member.MemberService;
import com.study.party.member.vo.MemberVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.study.party.comm.util.StringUtil.isEmptyObj;


@Tag(name="auth-controller", description="로그인, 회원가입, 회원가입 시 ID 중복체크")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final MemberService memberService;

    @Operation(summary = "회원 가입 시 ID 중보체크를 위한 회원 ID 기반 정보 조회 API", description = "테이블 member_info 의 유니크 키인 member_id 를 패스로 전달받아 멤버 정보를 조회합니다")
    @GetMapping("/member/{member_id}")
    public ResponseEntity<MemberVo> getMemberByMemberId(
        HttpServletRequest request,
        @PathVariable("member_id") String member_id
    ) {
        return CommResponseVo.builder()
                             .body(memberService.getMemberByMemberId(MemberVo.builder().memberId(member_id).build()))
                             .build()
                             .ok();
    }

    @Operation(summary = "회원 가입 API", description = "사용자 ID, 비밀번호, 이름을 Table 에 등록 후 로그인할 수 있게 한다")
    @PostMapping("signup")
    public ResponseEntity<String> signup(
        HttpServletRequest request,
        @Parameter(name="signupReqVo", required=true, description="회원가입을 위한 사용자 ID, 비밀번호, 이름") @RequestBody SignupReqVo signupReqVo
    ) {
        if ( isEmptyObj(signupReqVo.getMemberId()) || isEmptyObj(signupReqVo.getMemberName()) || isEmptyObj(signupReqVo.getMemberPassword()) ) throw new BadRequestException("필수입력값을 확인하세요");
        return authService.signup(signupReqVo);
    }

    @Operation(summary = "회원 로그인 API", description = "사용자 ID, 비밀번호를 전달 받아 해당 사용자가 등록되어 있는지 확인 후 JWT 토큰을 발급한다")
    @PostMapping("login")
    public ResponseEntity<LoginResVo> login(
        HttpServletRequest request,
        @Parameter(name="loginReqVo", required=true, description="로그인을 위한 사용자 ID, 비밀번호") @RequestBody LoginReqVo loginReqVo
    ) {
        if ( isEmptyObj(loginReqVo.getMemberId()) || isEmptyObj(loginReqVo.getMemberPassword()) ) throw new BadRequestException("필수입력값을 확인하세요");

        return authService.login(loginReqVo);
    }

    @Operation(summary = "토큰 재발행", description = "AT가 만료되었을 경우, RT로 AT를 재발행한다.")
    @PostMapping("reissue")
    public ResponseEntity<TokenVo> reissue(
        HttpServletRequest request,
        @RequestBody TokenVo tokenVo
    ) {
        if ( isEmptyObj(tokenVo.getAccessToken()) || isEmptyObj(tokenVo.getRefreshToken()) ) throw new BadRequestException("필수입력값을 확인하세요");

        return authService.reissue(tokenVo);
    }

}
