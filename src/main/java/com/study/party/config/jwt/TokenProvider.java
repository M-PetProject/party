package com.study.party.config.jwt;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.TokenVo;
import com.study.party.member.vo.MemberVo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.study.party.comm.util.StringUtil.nvl;
import static com.study.party.config.jwt.secret.JwtSecret.getKey;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    //    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;       // 24시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key key;

    //    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
    public TokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(getKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenVo generateEntityToken(Authentication authentication, MemberVo memberVo) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(memberVo.getMemberIdx()));
        claims.put("member_idx"  , memberVo.getMemberIdx());
        claims.put("member_id"   , memberVo.getMemberId() );
        claims.put("member_name" , memberVo.getMemberName() );
        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                                 .setSubject(authentication.getName())       // payload "sub": "name"
                                 .setClaims(claims)
                                 .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                                 .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                                 .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                                  .setSubject(authentication.getName())       // payload "sub": "name"
                                  .setClaims(claims)
                                  .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                                  .signWith(key, SignatureAlgorithm.HS512)
                                  .compact();

        return TokenVo.builder()
                      .memberIdx(memberVo.getMemberIdx())
                      .grantType(BEARER_TYPE)
                      .accessToken(accessToken)
                      .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                      .refreshToken(refreshToken)
                      .build();
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        // UserDetails 객체를 만들어서 Authentication 리턴
//        UserDetails principal = new User(claims.getSubject(), "", Arrays.asList());
        CustomUserDetailsVo principal = new CustomUserDetailsVo(
                Long.parseLong(nvl(claims.get("member_idx"), "0")),
                nvl(claims.get("member_id"), ""),
                nvl(claims.get("member_name"), ""),
                "",
                Arrays.asList()
        );

        return new UsernamePasswordAuthenticationToken(principal, "", Arrays.asList());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
