package com.study.party.comm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommTestServiceTest {

    @Autowired
    private CommTestService commTestService;

    @Test
    public void getTest() {
        commTestService.getTest();
    }

}