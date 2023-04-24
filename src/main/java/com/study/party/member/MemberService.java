package com.study.party.member;

import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.member.vo.MemberVo;
import com.study.party.team_member.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final TeamMemberService teamMemberService;
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
        MemberVo result = memberDao.getMember(memberVo);
        result.setTeamMemberVos(teamMemberService.getTeamsByMemberIdx(result.getMemberIdx()));
        return result;
    }

    public MemberVo getMemberByMemberId(MemberVo memberVo) {
        MemberVo result = memberDao.getMemberByMemberId(memberVo);
        result.setTeamMemberVos(teamMemberService.getTeamsByMemberIdx(result.getMemberIdx()));
        return result;
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
