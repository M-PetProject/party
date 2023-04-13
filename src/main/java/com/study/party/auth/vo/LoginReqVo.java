package com.study.party.auth.vo;

import com.study.party.member.vo.MemberVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 로그인 요청 VO")
public class LoginReqVo {

    private String memberId;
    private String memberPassword;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberId, memberPassword);
    }


    public MemberVo toMember() {
        return MemberVo.builder()
                       .memberId(this.memberId)
                       .build();
    }
}
