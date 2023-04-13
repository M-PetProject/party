package com.study.party.auth.vo;

import com.study.party.member.vo.MemberVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 가입 요청 VO")
public class SignupReqVo {

    private String memberId;
    private String memberPassword;
    private String memberName;

    public MemberVo toMember(PasswordEncoder passwordEncoder) {
        return MemberVo.builder()
                       .memberId(this.memberId)
                       .memberName(this.memberName)
                       .memberPassword(passwordEncoder.encode(this.memberPassword))
                       .build();
    }
}
