package com.study.party.comm.code;

import com.study.party.comm.code.vo.CommCodeVo;
import com.study.party.comm.code.vo.CommGrpCodeVo;
import com.study.party.jpa.entity.cd.CmCdEntity;
import com.study.party.jpa.repository.CmCdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommCodeService {

    private final CmCdRepository cmCdRepository;
    private final CommCodeDao commCodeDao;

    public List<CmCdEntity> findByGrpCdOrderByCd(String grpCd) {
        return cmCdRepository.findByGrpCdOrderByCd(grpCd);
    }

    public List<CommGrpCodeVo> getCommGrpCodes(CommGrpCodeVo commGrpCodeVo) {
        return commCodeDao.getCommGrpCodes(commGrpCodeVo);
    }
    public CommGrpCodeVo getCommGrpCode(CommGrpCodeVo commGrpCodeVo) {
        return commCodeDao.getCommGrpCode(commGrpCodeVo);
    }
    public int createCommGrpCode(CommGrpCodeVo commGrpCodeVo) {
        return commCodeDao.createCommGrpCode(commGrpCodeVo);
    }
    public int updateCommGrpCode(CommGrpCodeVo commGrpCodeVo) {
        return commCodeDao.updateCommGrpCode(commGrpCodeVo);
    }
    public int deleteCommGrpCode(CommGrpCodeVo commGrpCodeVo) {
        return commCodeDao.deleteCommGrpCode(commGrpCodeVo);
    }
    public List<CommCodeVo> getCommCodes(CommCodeVo commCodeVo) {
        return commCodeDao.getCommCodes(commCodeVo);
    }
    public CommCodeVo getCommCode(CommCodeVo commCodeVo) {
        return commCodeDao.getCommCode(commCodeVo);
    }
    public int createCommCode(CommCodeVo commCodeVo) {
        return commCodeDao.createCommCode(commCodeVo);
    }
    public int updateCommCode(CommCodeVo commCodeVo) {
        return commCodeDao.updateCommCode(commCodeVo);
    }
    public int deleteCommCode(CommCodeVo commCodeVo) {
        return commCodeDao.deleteCommCode(commCodeVo);
    }

}
