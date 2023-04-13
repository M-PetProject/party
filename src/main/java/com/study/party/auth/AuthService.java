package com.study.party.auth;

import com.study.party.auth.vo.LoginReqVo;
import com.study.party.auth.vo.LoginResVo;
import com.study.party.auth.vo.SignupReqVo;
import com.study.party.auth.vo.SignupResVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.comm.vo.TokenVo;
import com.study.party.config.jwt.TokenProvider;
import com.study.party.member.MemberService;
import com.study.party.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final AuthDao authDao;

    public ResponseEntity signup(SignupReqVo signupReqVo) {
        MemberVo memberVoReq = signupReqVo.toMember(passwordEncoder);
        MemberVo checkMemberIdResult = memberService.getMemberByMemberId(memberVoReq);
        if ( !isEmptyObj(checkMemberIdResult) ) {
            return CommResponseVo.builder().body("이미 가입되어 있는 사용자입니다").build().badRequest();
        }

        System.out.println("memberVoReq :: "+memberVoReq);
        if ( memberService.createMember(memberVoReq) > 0 ) {
            return CommResponseVo.builder().body("가입 완료되었습니다").build().ok();
        } else {
            return CommResponseVo.builder().body("가입에 실패하였습니다 잠시후 다시 시도해주세요").build().internalServerError();
        }
    }

    public ResponseEntity login(LoginReqVo loginReqVo) {
        UsernamePasswordAuthenticationToken authenticationToken = loginReqVo.toAuthentication();
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            return CommResponseVo.builder().body("로그인 정보가 일치하지 않습니다").build().badRequest();
        }
        MemberVo dbMember = memberService.getMemberByMemberId(loginReqVo.toMember());
        TokenVo tokenVo = tokenProvider.generateEntityToken(authentication);
        return CommResponseVo.builder()
                             .body(LoginResVo.builder()
                                             .memberId(loginReqVo.getMemberId())
                                             .memberName(dbMember.getMemberName())
                                             .accessToken(tokenVo.getAccessToken())
                                             .refreshToken(tokenVo.getRefreshToken())
                                             .build())
                             .build()
                             .ok();
    }


}