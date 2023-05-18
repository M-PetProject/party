package com.study.party.vote;

import com.study.party.vote.vo.VoteListInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest
class VoteControllerTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void TEST_getListOfPlaceForVote(){
        try {
            VoteController voteController = ctx.getBean(VoteController.class);
            HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
            ResponseEntity<List<VoteListInfo>> listResponseEntity = voteController.getListOfPlaceForVote(mockHttpServletRequest, 111);
            System.out.println(String.format("TEST완료\n=>[%s]", listResponseEntity));
        }catch (Exception e){
            System.out.println(String.format("TEST 호출중 오류발생[%s]", e.getMessage()));
            throw e;
        }
    }

}