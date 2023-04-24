package com.study.party.member;

import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.member.vo.MemberAllergyVo;
import com.study.party.member.vo.MemberHateFoodVo;
import com.study.party.member.vo.MemberLikeFoodVo;
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
        getMemberInfoEtc(result);
        return result;
    }

    public MemberVo getMemberByMemberId(MemberVo memberVo) {
        MemberVo result = memberDao.getMemberByMemberId(memberVo);
        getMemberInfoEtc(result);
        return result;
    }

    private MemberVo getMemberInfoEtc(MemberVo memberVo) {
        memberVo.setTeamMemberVos(teamMemberService.getTeamsByMemberIdx(memberVo.getMemberIdx()));
        memberVo.setMemberAllergyVos(memberDao.getMemberAllergyVos(memberVo.toAllergyVo()));
        memberVo.setMemberHateFoodVos(memberDao.getMemberHateFoodVos(memberVo.toHateFoodVo()));
        memberVo.setMemberLikeFoodVos(memberDao.getMemberLikeFoodVos(memberVo.toLikeFoodVo()));
        return memberVo;
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
