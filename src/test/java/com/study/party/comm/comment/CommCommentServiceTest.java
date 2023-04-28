package com.study.party.comm.comment;

import com.study.party.comm.comment.vo.CommCommentVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommCommentServiceTest {

    @Autowired
    private CommCommentService commCommentService;

    @Test
    public void getCommentsPagination() {
        commCommentService.getCommentsPagination(CommCommentVo.builder().memberIdx(1).pageNo(1).limit(5).build());
    }

    @Test
    public void commentLike() {
        commCommentService.commentLike(CommCommentVo.builder().commentIdx(1).memberIdx(1).build());
    }

    @Test
    public void commentLikeCancel() {
        commCommentService.commentLikeCancel(CommCommentVo.builder().commentIdx(1).memberIdx(1).build());
    }

    @Test
    public void commentUnlike() {
        commCommentService.commentUnlike(CommCommentVo.builder().commentIdx(1).memberIdx(1).build());
    }

    @Test
    public void commentUnlikeCancel() {
        commCommentService.commentUnlikeCancel(CommCommentVo.builder().commentIdx(1).memberIdx(1).build());
    }

}