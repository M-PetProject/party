package com.study.party.comm.comment;

import com.study.party.comm.comment.vo.CommCommentEmotionVo;
import com.study.party.comm.comment.vo.CommCommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommCommentDao {

    List<CommCommentVo> getComments(CommCommentVo commCommentVo);
    int getCommentsTotCnt(CommCommentVo commCommentVo);
    CommCommentVo getComment(CommCommentVo commCommentVo);
    int createComment(CommCommentVo commCommentVo);
    int createCommentInfo(CommCommentVo commCommentVo);
    int updateComment(CommCommentVo commCommentVo);
    int updateCommentUseYn(CommCommentVo commCommentVo);
    int updateCommentInfoViewCount(CommCommentVo commCommentVo);
    int updateCommentInfoLike(CommCommentVo commCommentVo);
    int updateCommentInfoLikeCancel(CommCommentVo commCommentVo);
    int updateCommentInfoUnlike(CommCommentVo commCommentVo);
    int updateCommentInfoUnlikeCancel(CommCommentVo commCommentVo);
    CommCommentEmotionVo getCommentLike(CommCommentEmotionVo commCommentEmotionVo);
    CommCommentEmotionVo getCommentUnlike(CommCommentEmotionVo commCommentEmotionVo);
    int createCommentLike(CommCommentEmotionVo commCommentEmotionVo);
    int createCommentUnlike(CommCommentEmotionVo commCommentEmotionVo);
    int deleteCommentLike(CommCommentEmotionVo commCommentEmotionVo);
    int deleteCommentUnlike(CommCommentEmotionVo commCommentEmotionVo);
}
