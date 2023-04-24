package com.study.party.comm.test;

import com.study.party.comm.test.vo.CommTestVo;
import com.study.party.comm.vo.CommPaginationResVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommTestService {

    private final CommTestDao commTestDao;

    public CommPaginationResVo<List<CommTestVo>> getTests(CommTestVo commTestVo) {
        return CommPaginationResVo.builder()
                                  .totalItems(commTestDao.getTestsTotCnt(commTestVo))
                                  .data(commTestDao.getTests(commTestVo))
                                  .pageNo(commTestVo.getPageNo())
                                  .limit(commTestVo.getLimit())
                                  .build()
                                  .pagination();
    }

    public CommTestVo getTest(CommTestVo commTestVo) {
        return commTestDao.getTest(commTestVo);
    }

    public int createTest(CommTestVo commTestVo) {
        return commTestDao.createTest(commTestVo);
    }

    public int updateTest(CommTestVo commTestVo) {
        return commTestDao.updateTest(commTestVo);
    }

    public int deleteTest(CommTestVo commTestVo) {
        return commTestDao.deleteTest(commTestVo);
    }

}
