package com.study.party.comm.code;

import com.study.party.comm.code.vo.CommCodeVo;
import com.study.party.comm.code.vo.CommGrpCodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommCodeDao {

    List<CommGrpCodeVo> getCommGrpCodes(CommGrpCodeVo commGrpCodeVo);
    CommGrpCodeVo getCommGrpCode(CommGrpCodeVo commGrpCodeVo);
    int createCommGrpCode(CommGrpCodeVo commGrpCodeVo);
    int updateCommGrpCode(CommGrpCodeVo commGrpCodeVo);
    int deleteCommGrpCode(CommGrpCodeVo commGrpCodeVo);
    List<CommCodeVo> getCommCodes(CommCodeVo commCodeVo);
    CommCodeVo getCommCode(CommCodeVo commCodeVo);
    int createCommCode(CommCodeVo commCodeVo);
    int updateCommCode(CommCodeVo commCodeVo);
    int deleteCommCode(CommCodeVo commCodeVo);

}
