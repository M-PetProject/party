package com.study.party.comm;

import com.study.party.comm.test.CommTestService;
import com.study.party.comm.test.vo.CommTestVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommTestServiceTest {

    @Autowired
    private CommTestService commTestService;

    @Test
    public void getTest() {
        commTestService.getTest(CommTestVo.builder().idx(0).build());
    }

}