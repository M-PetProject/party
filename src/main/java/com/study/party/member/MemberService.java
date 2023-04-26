package com.study.party.member;

import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.member.vo.MemberAllergyVo;
import com.study.party.member.vo.MemberHateFoodVo;
import com.study.party.member.vo.MemberLikeFoodVo;
import com.study.party.member.vo.MemberVo;
import com.study.party.team_member.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

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

    public CommResultVo getMember(MemberVo memberVo) {
        MemberVo result = memberDao.getMember(memberVo);
        if ( isEmptyObj(result) ) {
            return CommResultVo.builder().code(404).msg("존재하지 않는 회원입니다").build();
        }
        getMemberInfoEtc(result);

        return CommResultVo.builder().code(200).data(result).build();
    }

    public CommResultVo getMemberByMemberId(MemberVo memberVo) {
        MemberVo result = memberDao.getMemberByMemberId(memberVo);
        if ( isEmptyObj(result) ) {
            return CommResultVo.builder().code(404).msg("존재하지 않는 회원입니다").build();
        }
        getMemberInfoEtc(result);
        return CommResultVo.builder().code(200).data(result).build();
    }

    public MemberVo checkMember(MemberVo memberVo) {
        return memberDao.getMemberByMemberId(memberVo);
    }

    private MemberVo getMemberInfoEtc(MemberVo memberVo) {
        memberVo.setTeamMemberVos(teamMemberService.getTeamsByMemberIdx(memberVo.getMemberIdx()));
        memberVo.setMemberAllergyVos(memberDao.getMemberAllergyVos(memberVo.toAllergyVo()));
        memberVo.setMemberHateFoodVos(memberDao.getMemberHateFoodVos(memberVo.toHateFoodVo()));
        memberVo.setMemberLikeFoodVos(memberDao.getMemberLikeFoodVos(memberVo.toLikeFoodVo()));
        return memberVo;
    }

    @Transactional
    public int createMember(MemberVo memberVo) {
        memberDao.createMember(memberVo);
        createMemberEtc(memberVo);
        return 1;
    }

    public CommResultVo updateMember(MemberVo memberVo) {
        memberDao.updateMember(memberVo);
        memberDao.deleteMemberAllergyVo(memberVo.toAllergyVo());
        memberDao.deleteMemberHateFoodVo(memberVo.toHateFoodVo());
        memberDao.deleteMemberLikeFoodVo(memberVo.toLikeFoodVo());
        createMemberEtc(memberVo);
        return CommResultVo.builder().code(200).msg("수정완료되었습니다").build();
    }

    public int createMemberEtc(MemberVo memberVo) {
        if(memberVo.getMemberAllergyVos() != null) {
            for (MemberAllergyVo memberAllergyVo : memberVo.getMemberAllergyVos() ) {
                memberAllergyVo.setMemberIdx(memberVo.getMemberIdx());
                memberDao.createMemberAllergyVo(memberAllergyVo);
            }
        }

        if(memberVo.getMemberHateFoodVos() != null) {
            for (MemberHateFoodVo memberHateFoodVo : memberVo.getMemberHateFoodVos()) {
                memberHateFoodVo.setMemberIdx(memberVo.getMemberIdx());
                memberDao.createMemberHateFoodVo(memberHateFoodVo);
            }
        }

        if(memberVo.getMemberLikeFoodVos() != null) {
            for (MemberLikeFoodVo memberLikeFoodVo : memberVo.getMemberLikeFoodVos()) {
                memberLikeFoodVo.setMemberIdx(memberVo.getMemberIdx());
                memberDao.createMemberLikeFoodVo(memberLikeFoodVo);
            }
        }
        return 1;
    }

    public int deleteMember(MemberVo memberVo) {
        return memberDao.deleteMember(memberVo);
    }

}
