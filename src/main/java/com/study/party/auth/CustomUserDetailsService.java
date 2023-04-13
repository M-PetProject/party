package com.study.party.auth;

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
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberVo dbMember = memberService.getMemberByMemberId(MemberVo.builder().memberId(memberId).build());
        if ( isEmptyObj(dbMember) ) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        return createUserDetails(dbMember);
    }

    private UserDetails createUserDetails(MemberVo dbMember) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(dbMember.getMemberId());
        return new User(
                dbMember.getMemberName(), dbMember.getMemberPassword(), Collections.singleton(grantedAuthority)
        );
    }

}
