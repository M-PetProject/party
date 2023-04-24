package com.study.party.auth.vo;

import com.study.party.member.vo.MemberAllergyVo;
import com.study.party.member.vo.MemberHateFoodVo;
import com.study.party.member.vo.MemberLikeFoodVo;
import com.study.party.member.vo.MemberVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 가입 요청 VO")
public class SignupReqVo {


    @Schema(description="회원 가입 요청 회원 ID", required=true)
    private String memberId;
    @Schema(description="회원 가입 요청 회원 비밀번호", required=true)
    private String memberPassword;
    @Schema(description="회원 가입 요청 회원 이름", required=true)
    private String memberName;

    @Schema(description="회원 알러지 목록")
    private List<MemberAllergyVo> memberAllergyVos;
    @Schema(description="회원 싫어하는 음식 목록")
    private List<MemberHateFoodVo> memberHateFoodVos;
    @Schema(description="회원 좋아하는 음식 목록")
    private List<MemberLikeFoodVo> memberLikeFoodVos;

    public MemberVo toMember(PasswordEncoder passwordEncoder) {
        return MemberVo.builder()
                       .memberId(this.memberId)
                       .memberName(this.memberName)
                       .memberPassword(passwordEncoder.encode(this.memberPassword))
                       .memberAllergyVos(this.memberAllergyVos)
                       .memberHateFoodVos(this.memberHateFoodVos)
                       .memberLikeFoodVos(this.memberLikeFoodVos)
                       .build();
    }
}
