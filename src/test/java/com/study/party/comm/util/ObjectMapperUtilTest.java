package com.study.party.comm.util;

import com.study.party.comm.comment.vo.CommCommentVo;
import com.study.party.notice_comment.vo.NoticeCommentVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ObjectMapperUtilTest {

    @Test
    public void castClass() {
        NoticeCommentVo result = ObjectMapperUtil.castClass(CommCommentVo.builder().commentIdx(1).build(), new NoticeCommentVo());
        System.out.println(result.getCommentIdx());
    }
}