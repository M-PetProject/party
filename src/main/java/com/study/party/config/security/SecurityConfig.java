package com.study.party.config.security;

import com.study.party.config.jwt.JwtAccessDeniedHandler;
import com.study.party.config.jwt.JwtAuthenticationEntryPoint;
import com.study.party.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf().disable()
                   // exception handling 할 때 우리가 만든 클래스를 추가
                   .exceptionHandling()
                   .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                   .accessDeniedHandler(jwtAccessDeniedHandler)
                   .and()
                   .headers()
                   .frameOptions()
                   .sameOrigin()
                   // 시큐리티는 기본적으로 세션을 사용
                   // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                   .and()
                   .sessionManagement()
                   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                   .and()
                   .authorizeRequests()
                   .antMatchers("/","/comm/**", "/sample/**", "/v/**","/resources/**","/auth/**","/swagger/**","/swagger-resources/**","/webjars/**","/v2/api-docs","/v3/api-docs/**","/swagger-ui/**", "/api/v2/**", "/api/v3/**","/health" ).permitAll()
                   .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
                   // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                   .and()
                   .apply(new JwtSecurityConfig(tokenProvider));
    }

}
