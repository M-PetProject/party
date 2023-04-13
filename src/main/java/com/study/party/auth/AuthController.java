package com.study.party.auth;

import com.study.party.auth.vo.LoginReqVo;
import com.study.party.auth.vo.SignupReqVo;
import com.study.party.comm.vo.CommResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "회원 가입 API", description = "사용자 ID, 비밀번호, 이름을 Table 에 등록 후 로그인할 수 있게 한다")
    @PostMapping("signup")
    public ResponseEntity signup(
        HttpServletRequest request,
        @Parameter(name="signupReqVo", required=true, description="회원가입을 위한 사용자 ID, 비밀번호, 이름") @RequestBody SignupReqVo signupReqVo
    ) {
        return authService.signup(signupReqVo);
    }

    @Operation(summary = "회원 로그인 API", description = "사용자 ID, 비밀번호를 전달 받아 해당 사용자가 등록되어 있는지 확인 후 JWT 토큰을 발급한다")
    @PostMapping("login")
    public ResponseEntity login(
        HttpServletRequest request,
        @Parameter(name="loginReqVo", required=true, description="로그인을 위한 사용자 ID, 비밀번호") @RequestBody LoginReqVo loginReqVo
    ) {
        return authService.login(loginReqVo);
    }
}
