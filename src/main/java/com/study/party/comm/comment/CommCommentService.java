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

    /**
     * 댓글 목록 조회
     * @param commCommentVo
     * @return
     */
    public List<CommCommentVo> getComments(CommCommentVo commCommentVo) {
        return commCommentDao.getComments(commCommentVo);
    }

    /**
     * 댓글 목록 총건수 조회
     * @param commCommentVo
     * @return
     */
    public int getCommentsTotCnt(CommCommentVo commCommentVo) {
        return commCommentDao.getCommentsTotCnt(commCommentVo);
    }

    /**
     * 댓글 목록 페이징 목록 조회
     * @param commCommentVo <br>
     *     pageNo : (필수입력값)현재 페이지 번호<br>
     *     limit : (필수입력값)한 페이지에 보여줄 건수<br>
     *     parent_comment_idx(parentCommentIdx) : 상위_댓글_IDX <br>
     *     comment_cd(commentCd) : 댓글_코드(G:CMMT)<br>
     *     post_idx(postIdx) : 게시글_IDX(공지사항, 투표, 댓글, 회식)<br>
     *     member_idx(memberIdx) : 작성자_IDX<br>
     *     title(title) : 제목<br>
     *     content(content) : 내용<br>
     * @return
     */
    public CommPaginationResVo getCommentsPagination(CommCommentVo commCommentVo) {
        if ( commCommentVo.getPageNo() < 1 || commCommentVo.getLimit() < 1 ) throw new BadRequestException("필수입력값을 확인하세요");
        return CommPaginationResVo.builder()
                                  .totalItems(getCommentsTotCnt(commCommentVo))
                                  .data(getComments(commCommentVo))
                                  .pageNo(commCommentVo.getPageNo())
                                  .limit(commCommentVo.getLimit())
                                  .build()
                                  .pagination();
    }

    /**
     * 댓글 상세 조회
     * @param commCommentVo <br>
     *     comment_idx(commentIdx) : 댓글_IDX<br>
     * @return
     */
    public CommCommentVo getComment(CommCommentVo commCommentVo) {
        CommCommentVo comment = commCommentDao.getComment(commCommentVo);
        if (isEmptyObj(comment)) throw new BadRequestException("존재하지 않는 댓글입니다");
        return comment;
    }

    /**
     * 댓글 생성
     * @param commCommentVo <br>
     *     comment_cd(commentCd) : (필수입력값) 댓글_코드(G:CMMT) <br>
     *     post_idx(postIdx) : (필수입력값) 원본 게시글_IDX(공지사항, 장소, 투표, 회식, ...) 1~ <br>
     *     member_idx(memberIdx) : (필수입력값) 댓글 작성 요청 사용자_IDX 1~ <br>
     *     title(title) : 제목(제목이나 내용 둘 중에 하나는 값이 있어야 합니다) <br>
     *     content(content) : 내용(제목이나 내용 둘 중에 하나는 값이 있어야 합니다) <br>
     *     parent_comment_idx(parentCommentIdx) : (필수입력값) 상위_댓글_IDX 0~ <br>
     * @return
     */
    public CommCommentVo createComment(CommCommentVo commCommentVo) {
        if (isEmptyObj(commCommentVo.getCommentCd()) ||
            commCommentVo.getPostIdx() < 1 ||
            commCommentVo.getMemberIdx() < 1 ||
            commCommentVo.getParentCommentIdx() < 0 ||
            isEmptyObj(commCommentVo.getUseYn()) ||
            (isEmptyObj(commCommentVo.getTitle()) && isEmptyObj(commCommentVo.getContent()))
        ) throw new BadRequestException("필수입력값을 확인하세요");

        commCommentVo.setUseYn("Y");
        if (commCommentDao.createComment(commCommentVo) < 1) throw new InternalServerErrorException("댓글 작성 중 오류가 발생하였습니다");
        if (commCommentDao.createCommentInfo(commCommentVo) < 1) throw new InternalServerErrorException("댓글 작성 중 오류가 발생하였습니다");
        return commCommentVo;
    }

    /**
     * 댓글 수정
     * @param commCommentVo <br>
     *     comment_idx(commentIdx) : (필수입력값) 수정 대상 댓글_IDX<br>
     *     member_idx(memberIdx) : (필수입력값) 수정 요청 사용자_IDX 1~<br>
     *     title(title) : 제목(제목이나 내용 둘 중에 하나는 값이 있어야 합니다)<br>
     *     content(content) : 내용(제목이나 내용 둘 중에 하나는 값이 있어야 합니다)<br>
     * @return
     */
    public CommCommentVo updateComment(CommCommentVo commCommentVo) {
        if (commCommentVo.getCommentIdx() < 1 ||
            commCommentVo.getMemberIdx() < 1 ||
            (isEmptyObj(commCommentVo.getTitle()) && isEmptyObj(commCommentVo.getContent()))
        ) throw new BadRequestException("필수입력값을 확인하세요");

        CommCommentVo comment = getComment(commCommentVo);
        if (comment.getMemberIdx() != commCommentVo.getMemberIdx()) throw new UnauthorizedException("댓글 수정은 작성자만 가능합니다");
        if (commCommentDao.updateComment(commCommentVo) < 1) throw new InternalServerErrorException("댓글 수정 중 오류가 발생하였습니다");
        return commCommentVo;
    }

    /**
     * 댓글 삭제/복원 처리(사용여부 Y, N)
     * @param commCommentVo <br>
     *     comment_idx(commentIdx) : (필수입력값) 수정 대상 댓글_IDX <br>
     *     member_idx(memberIdx) : (필수입력값) 수정 요청 사용자_IDX 1~ <br>
     *     use_yn(useYn) : (필수입력값) 사용여부 "Y" : 복원, "N" : 삭제 <br>
     * @return
     */
    public CommCommentVo updateCommentUseYn(CommCommentVo commCommentVo) {
        if (commCommentVo.getCommentIdx() < 1 ||
            commCommentVo.getMemberIdx() < 1 ||
            isEmptyObj(commCommentVo.getUseYn()) ||
            (!"Y".equals(commCommentVo.getUseYn()) && !"N".equals(commCommentVo.getContent()))
        ) throw new BadRequestException("필수입력값을 확인하세요");

        CommCommentVo comment = getComment(commCommentVo);
        if (comment.getMemberIdx() != commCommentVo.getMemberIdx()) throw new UnauthorizedException("댓글 수정은 작성자만 가능합니다");
        if (commCommentDao.updateCommentUseYn(commCommentVo) < 1) throw new InternalServerErrorException("댓글 수정 중 오류가 발생하였습니다");
        return commCommentVo;
    }

    /**
     * 댓글 삭제/복원 처리(사용여부 Y, N)
     * @param commCommentVo <br>
     *     comment_cd(comment_cd) : (필수입력값) 댓글_코드(G:CMMT) <br>
     *     post_idx(postIdx) : (필수입력값) 원본 게시글_IDX(공지사항, 장소, 투표, 회식, ...) 1~ <br>
     * @return
     */
    public CommCommentVo deleteCommentByOrgPost(CommCommentVo commCommentVo) {
        if (isEmptyObj(commCommentVo.getCommentCd()) ||
            commCommentVo.getPostIdx() < 1
        ) throw new BadRequestException("필수입력값을 확인하세요");

        commCommentDao.deleteCommentInfoByOrgPost(commCommentVo);
        commCommentDao.deleteCommentByOrgPost(commCommentVo);
        return commCommentVo;
    }

    /**
     * 댓글 조회수 증가 처리 <br>
     * 작성자가 아닌 사용자가 요청을 할 경우 조회수 +1 증가 <br>
     * @param commCommentVo <br>
     *     comment_idx(commentIdx) : (필수입력값) 수정 대상 댓글_IDX <br>
     *     member_idx(memberIdx) : (필수입력값) 수정 요청 사용자_IDX 1~ <br>
     * @return
     */
    public CommCommentVo commentView(CommCommentVo commCommentVo) {
        CommCommentVo comment = getComment(commCommentVo); // 댓글 가져오기
        if ( comment.getMemberIdx() != commCommentVo.getMemberIdx() ) {
            comment.setViewCount(comment.getViewCount()+1);
            commCommentDao.updateCommentInfoViewCount(comment);
        }
        return comment;
    }

    /**
     * 댓글 좋아요 증가 처리 <br>
     * 좋아요 요청 사용자가 좋아요 요청을 할 경우 해당 댓글의 좋아요 + 1 <br>
     * 좋아요 요청 사용자가 해당 댓글에 싫어요를 한 경우 해당 댓글의 싫어요 - 1 <br>
     * @param commCommentVo <br>
     *     comment_idx(commentIdx) : (필수입력값) 수정 대상 댓글_IDX <br>
     *     member_idx(memberIdx) : (필수입력값) 수정 요청 사용자_IDX 1~ <br>
     * @return
     */
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

    /**
     * 댓글 좋아요 취소 처리 <br>
     * 좋아요 요청 사용자가 좋아요 취소 요청을 할 경우 해당 댓글의 좋아요 - 1 <br>
     * @param commCommentVo <br>
     *     comment_idx(commentIdx) : (필수입력값) 수정 대상 댓글_IDX <br>
     *     member_idx(memberIdx) : (필수입력값) 수정 요청 사용자_IDX 1~ <br>
     * @return
     */
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

    /**
     * 댓글 싫어요 처리 <br>
     * 싫어요 요청 사용자가 싫어요 요청을 할 경우 해당 댓글의 싫어요 + 1 <br>
     * 싫어요 요청 사용자가 해당 댓글에 좋아요를 한 경우 해당 댓글의 좋아요 - 1 <br>
     * @param commCommentVo <br>
     *     comment_idx(commentIdx) : (필수입력값) 수정 대상 댓글_IDX <br>
     *     member_idx(memberIdx) : (필수입력값) 수정 요청 사용자_IDX 1~ <br>
     * @return
     */
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

    /**
     * 댓글 싫어요 취소 처리 <br>
     * 싫어요 요청 사용자가 싫어요 취소 요청을 할 경우 해당 댓글의 싫어요 - 1 <br>
     * @param commCommentVo <br>
     *     comment_idx(commentIdx) : (필수입력값) 수정 대상 댓글_IDX <br>
     *     member_idx(memberIdx) : (필수입력값) 수정 요청 사용자_IDX 1~ <br>
     * @return
     */
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
