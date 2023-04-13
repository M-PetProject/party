package com.study.party.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 로그인 응답 VO")
public class LoginResVo {
    private String memberId;
    private String memberName;
    private String accessToken;
    private String refreshToken;
}
