package com.study.party.comm.test;

import com.study.party.comm.test.vo.CommTestVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommTestService {

    private final CommTestDao commTestDao;

    public CommResultVo getTests(CommTestVo commTestVo) {
        return CommResultVo.builder()
                           .code(200)
                           .data(CommPaginationResVo.builder()
                                                    .totalItems(commTestDao.getTestsTotCnt(commTestVo))
                                                    .data(commTestDao.getTests(commTestVo))
                                                    .pageNo(commTestVo.getPageNo())
                                                    .limit(commTestVo.getLimit())
                                                    .build()
                                                    .pagination())
                           .build();
    }

    public CommResultVo getTest(CommTestVo commTestVo) {
        CommTestVo result = commTestDao.getTest(commTestVo);
        if ( isEmptyObj(result) ) {
            return CommResultVo.builder()
                               .code(400)
                               .msg("존재하지 않는 게시글입니다")
                               .build();
        }
        return CommResultVo.builder().code(200).data(result).build();
    }

    public CommResultVo createTest(CommTestVo commTestVo) {
        if ( commTestDao.createTest(commTestVo) < 1 ) {
            return CommResultVo.builder()
                               .code(500)
                               .msg("게시글 등록에 실패했습니다")
                               .build();

        }
        return CommResultVo.builder()
                           .code(200)
                           .msg("게시글 등록되었습니다")
                           .build();
    }

    public CommResultVo updateTest(CommTestVo commTestVo) {
        if ( commTestDao.updateTest(commTestVo) < 1 ) {
            return CommResultVo.builder()
                               .code(500)
                               .msg("게시글 수정에 실패했습니다")
                               .build();
        }
        return CommResultVo.builder()
                           .code(200)
                           .msg("게시글 수정되었습니다")
                           .build();
    }

    public CommResultVo deleteTest(CommTestVo commTestVo) {
        if ( commTestDao.deleteTest(commTestVo) < 1 ) {
            return CommResultVo.builder()
                               .code(500)
                               .msg("게시글 삭제에 실패했습니다")
                               .build();
        }
        return CommResultVo.builder()
                           .code(200)
                           .msg("게시글 삭제되었습니다")
                           .build();
    }

}
