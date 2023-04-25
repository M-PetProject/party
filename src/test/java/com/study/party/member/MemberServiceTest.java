package com.study.party.member;

import com.study.party.member.vo.MemberVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void getMember() {
        memberService.getMember(MemberVo.builder().memberIdx(1).build());
    }

}