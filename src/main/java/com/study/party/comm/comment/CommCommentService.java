package com.study.party.comm.comment;

import com.study.party.comm.comment.vo.CommCommentEmotionVo;
import com.study.party.comm.comment.vo.CommCommentVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Service
@RequiredArgsConstructor
public class CommCommentService {

    private final CommCommentDao commCommentDao;

    public List<CommCommentVo> getComments(CommCommentVo commCommentVo) {
        return commCommentDao.getComments(commCommentVo);
    }

    public int getCommentsTotCnt(CommCommentVo commCommentVo) {
        return commCommentDao.getCommentsTotCnt(commCommentVo);
    }

    public CommPaginationResVo getCommentsPagination(CommCommentVo commCommentVo) {
        return CommPaginationResVo.builder()
                                  .totalItems(getCommentsTotCnt(commCommentVo))
                                  .data(getComments(commCommentVo))
                                  .pageNo(commCommentVo.getPageNo())
                                  .limit(commCommentVo.getLimit())
                                  .build()
                                  .pagination();
    }

    public CommCommentVo getComment(CommCommentVo commCommentVo) {
        CommCommentVo comment = commCommentDao.getComment(commCommentVo);
        if (isEmptyObj(comment)) throw new BadRequestException("존재하지 않는 댓글입니다");
        return comment;
    }

    public CommCommentVo createComment(CommCommentVo commCommentVo) {
        if (commCommentDao.createComment(commCommentVo) < 1) throw new InternalServerErrorException("댓글 작성 중 오류가 발생하였습니다");
        if (commCommentDao.createCommentInfo(commCommentVo) < 1) throw new InternalServerErrorException("댓글 작성 중 오류가 발생하였습니다");
        return commCommentVo;
    }

    public CommCommentVo updateComment(CommCommentVo commCommentVo) {
        if (isEmptyObj(commCommentVo.getTitle()) || isEmptyObj(commCommentVo.getContent()) || commCommentVo.getMemberIdx() < 1) throw new BadRequestException("필수입력값을 확인하세요");
        CommCommentVo comment = getComment(commCommentVo);
        if (comment.getMemberIdx() != commCommentVo.getMemberIdx()) throw new UnauthorizedException("댓글 수정은 작성자만 가능합니다");
        if (commCommentDao.updateComment(commCommentVo) < 1) throw new InternalServerErrorException("댓글 수정 중 오류가 발생하였습니다");
        return commCommentVo;
    }

    public CommCommentVo commentView(CommCommentVo commCommentVo) {
        CommCommentVo comment = getComment(commCommentVo); // 댓글 가져오기
        if ( comment.getMemberIdx() != commCommentVo.getMemberIdx() ) {
            comment.setViewCount(comment.getViewCount()+1);
            commCommentDao.updateCommentInfoViewCount(comment);
        }
        return comment;
    }

    @Transactional
    public CommCommentVo commentLike(CommCommentVo commCommentVo) {
        CommCommentVo comment = getComment(commCommentVo); // 댓글 가져오기

        CommCommentEmotionVo like = commCommentDao.getCommentLike(commCommentVo.toCommCommentEmotionVo());
        if (!isEmptyObj(like)) throw new BadRequestException("이미 좋아요 하셨습니다");

        CommCommentEmotionVo unlike = commCommentDao.getCommentUnlike(commCommentVo.toCommCommentEmotionVo());
        if (!isEmptyObj(unlike)) {
            commCommentDao.updateCommentInfoUnlikeCancel(commCommentVo); // 싫어요 갯수 감소
            commCommentDao.deleteCommentUnlike(commCommentVo.toCommCommentEmotionVo()); // 싫어요 삭제
            comment.setUnlikeCount(comment.getUnlikeCount()-1);
        }

        comment.setLikeCount(comment.getLikeCount()+1);
        if (commCommentDao.createCommentLike(commCommentVo.toCommCommentEmotionVo()) < 1) throw new InternalServerErrorException("댓글 좋아요 중 오류가 발생하였습니다");
        if (commCommentDao.updateCommentInfoLike(commCommentVo) < 1) throw new InternalServerErrorException("댓글 좋아요 중 오류가 발생하였습니다");
        return comment;
    }

    @Transactional
    public CommCommentVo commentLikeCancel(CommCommentVo commCommentVo) {
        CommCommentVo comment = getComment(commCommentVo); // 댓글 가져오기

        CommCommentEmotionVo like = commCommentDao.getCommentLike(commCommentVo.toCommCommentEmotionVo());
        if (isEmptyObj(like)) throw new BadRequestException("좋아요한 적이 없습니다");

        comment.setLikeCount(comment.getLikeCount()-1);
        if (commCommentDao.deleteCommentLike(commCommentVo.toCommCommentEmotionVo()) < 1) throw new InternalServerErrorException("댓글 좋아요 취소 중 오류가 발생하였습니다");
        if (commCommentDao.updateCommentInfoLikeCancel(commCommentVo) < 1) throw new InternalServerErrorException("댓글 좋아요 취소 중 오류가 발생하였습니다");
        return comment;
    }

    @Transactional
    public CommCommentVo commentUnlike(CommCommentVo commCommentVo) {
        CommCommentVo comment = getComment(commCommentVo); // 댓글 가져오기

        CommCommentEmotionVo unlike = commCommentDao.getCommentUnlike(commCommentVo.toCommCommentEmotionVo());
        if (!isEmptyObj(unlike)) throw new BadRequestException("이미 싫어요 하셨습니다");

        CommCommentEmotionVo like = commCommentDao.getCommentLike(commCommentVo.toCommCommentEmotionVo());
        if (!isEmptyObj(like)) {
            commCommentDao.deleteCommentLike(commCommentVo.toCommCommentEmotionVo()); // 좋아요 갯수 감소
            commCommentDao.updateCommentInfoLikeCancel(commCommentVo); // 좋아요 삭제
            comment.setLikeCount(comment.getLikeCount()-1);
        }

        comment.setUnlikeCount(comment.getUnlikeCount()+1);
        if(commCommentDao.createCommentUnlike(commCommentVo.toCommCommentEmotionVo()) < 1) throw new InternalServerErrorException("댓글 싫어요 중 오류가 발생하였습니다");
        if(commCommentDao.updateCommentInfoUnlike(commCommentVo) < 1) throw new InternalServerErrorException("댓글 싫어요 중 오류가 발생하였습니다");
        return comment;
    }

    @Transactional
    public CommCommentVo commentUnlikeCancel(CommCommentVo commCommentVo) {
        CommCommentVo comment = getComment(commCommentVo); // 댓글 가져오기

        CommCommentEmotionVo unlike = commCommentDao.getCommentUnlike(commCommentVo.toCommCommentEmotionVo());
        if (isEmptyObj(unlike)) throw new BadRequestException("싫어요한 적이 없습니다");

        comment.setUnlikeCount(comment.getUnlikeCount()+1);
        if (commCommentDao.deleteCommentUnlike(commCommentVo.toCommCommentEmotionVo()) < 1) throw new InternalServerErrorException("댓글 싫어요 취소 중 오류가 발생하였습니다");
        if (commCommentDao.updateCommentInfoUnlikeCancel(commCommentVo) < 1) throw new InternalServerErrorException("댓글 싫어요 취소 중 오류가 발생하였습니다");
        return comment;
    }
}
