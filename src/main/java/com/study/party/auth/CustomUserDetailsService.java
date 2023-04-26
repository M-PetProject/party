package com.study.party.auth;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.exception.BadRequestException;
import com.study.party.member.MemberService;
import com.study.party.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.study.party.comm.util.StringUtil.isEmptyObj;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
//    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
    public CustomUserDetailsVo loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberVo dbMember = memberService.checkMember(MemberVo.builder().memberId(memberId).build());
        if ( isEmptyObj(dbMember) ) {
            throw new BadRequestException("존재하지 않는 회원입니다");
        }

        return createUserDetails(dbMember);
    }

//    private UserDetails createUserDetails(MemberVo dbMember) {
    private CustomUserDetailsVo createUserDetails(MemberVo dbMember) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(dbMember.getMemberId());
        return new CustomUserDetailsVo(
                dbMember.getMemberIdx(),
                dbMember.getMemberId(),
                dbMember.getMemberName(),
                dbMember.getMemberPassword(),
                Collections.singleton(grantedAuthority)
        );
//        return new User(
//                dbMember.getMemberId(), dbMember.getMemberPassword(), Collections.singleton(grantedAuthority)
//        );
    }

}
