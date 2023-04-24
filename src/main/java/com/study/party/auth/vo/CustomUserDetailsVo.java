package com.study.party.auth.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
public class CustomUserDetailsVo extends User {

    private long memberIdx;
    private String memberId;
    private String memberName;

    public CustomUserDetailsVo(String memberName, String password, Collection<? extends GrantedAuthority> authorities) {
        super(memberName, password, authorities);
    }

    public CustomUserDetailsVo(long memberIdx, String memberId, String memberName, String password, Collection<? extends GrantedAuthority> authorities) {
        super(memberName, password, authorities);
        this.memberIdx = memberIdx;
        this.memberId = memberId;
        this.memberName = memberName;
    }

}
