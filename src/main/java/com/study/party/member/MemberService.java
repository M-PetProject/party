package com.study.party.member;

import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    public CommPaginationResVo getMembers(MemberVo memberVo) {
        return CommPaginationResVo.builder()
                                  .totalItems(memberDao.getMembersTotCnt(memberVo))
                                  .data(memberDao.getMembers(memberVo))
                                  .pageNo(memberVo.getPageNo())
                                  .limit(memberVo.getLimit())
                                  .build()
                                  .pagination();
    }

    public MemberVo getMember(MemberVo memberVo) {
        return memberDao.getMember(memberVo);
    }

    public MemberVo getMemberByMemberId(MemberVo memberVo) {
        return memberDao.getMemberByMemberId(memberVo);
    }

    public int createMember(MemberVo memberVo) {
        return memberDao.createMember(memberVo);
    }

    public int updateMember(MemberVo memberVo) {
        return memberDao.updateMember(memberVo);
    }

    public int deleteMember(MemberVo memberVo) {
        return memberDao.deleteMember(memberVo);
    }

}
