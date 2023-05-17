package com.study.party.auth;

import com.study.party.auth.vo.*;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.comm.vo.TokenVo;
import com.study.party.config.jwt.TokenProvider;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.member.MemberService;
import com.study.party.member.vo.MemberVo;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.study.party.comm.util.StringUtil.isEmptyObj;
import static com.study.party.comm.util.StringUtil.nvl;

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
        MemberVo checkMemberIdResult = memberService.checkMember(memberVoReq);
        if ( !isEmptyObj(checkMemberIdResult) ) {
            throw new BadRequestException("이미 가입되어 있는 사용자입니다");
        }

        if ( memberService.createMember(memberVoReq) > 0 ) {
            return CommResponseVo.builder().body("가입 완료되었습니다").build().ok();
        } else {
            throw new InternalServerErrorException("가입에 실패하였습니다 잠시후 다시 시도해주세요");
        }
    }

    public ResponseEntity login(LoginReqVo loginReqVo) {
        UsernamePasswordAuthenticationToken authenticationToken = loginReqVo.toAuthentication();
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            throw new BadRequestException("로그인 정보가 일치하지 않습니다");
        }
        CommResultVo<MemberVo> memberResult = memberService.getMemberByMemberId(loginReqVo.toMember());
        MemberVo dbMember = memberResult.getData();
        TokenVo tokenVo = tokenProvider.generateEntityToken(authentication, dbMember);
        return CommResponseVo.builder()
                             .body(LoginResVo.builder()
                                             .memberIdx(dbMember.getMemberIdx())
                                             .memberId(loginReqVo.getMemberId())
                                             .memberName(dbMember.getMemberName())
                                             .accessToken(tokenVo.getAccessToken())
                                             .refreshToken(tokenVo.getRefreshToken())
                                             .memberAllergyVos(dbMember.getMemberAllergyVos())
                                             .memberHateFoodVos(dbMember.getMemberHateFoodVos())
                                             .memberLikeFoodVos(dbMember.getMemberLikeFoodVos())
                                             .teamMemberVos(dbMember.getTeamMemberVos())
                                             .build())
                             .build()
                             .ok();
    }


    public ResponseEntity reissue(TokenVo tokenVo) {
        System.out.println("토큰 재발급");
        // 1. 전달받은 토큰 검증 (RT 유효시간만큼, 서비스 접속이 없을 경우, RT 만료)
        if(!tokenProvider.validateToken(tokenVo.getRefreshToken())) {
            throw new BadRequestException("Refresh Token 만료");
        }

        Claims claims = tokenProvider.parseClaims(tokenVo.getRefreshToken());
        MemberVo memberVo = MemberVo.builder()
                .memberIdx(Long.parseLong(nvl(claims.get("member_idx"), "0")))
                .memberId(nvl(claims.get("member_id"), ""))
                .memberName(nvl(claims.get("member_name"), ""))
                .build();

        Authentication authentication = tokenProvider.getAuthentication(tokenVo.getRefreshToken());

        TokenVo reissuedToken = tokenProvider.generateEntityToken(authentication, memberVo);

        return CommResponseVo.builder()
                .body(LoginResVo.builder()
                        .accessToken(reissuedToken.getAccessToken())
                        .refreshToken(reissuedToken.getRefreshToken())
                        .build())
                .build()
                .ok();
    }
}
