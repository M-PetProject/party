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
    @Schema(description="회원 로그인 응답 회원 IDX", required=true)
    private long memberIdx;
    @Schema(description="회원 로그인 응답 회원 ID", required=true)
    private String memberId;
    @Schema(description="회원 로그인 응답 회원 이름", required=true)
    private String memberName;
    @Schema(description="회원 로그인 응답 JWT", required=true)
    private String accessToken;
    @Schema(description="회원 로그인 응답 Refresh Token", required=true)
    private String refreshToken;
}
