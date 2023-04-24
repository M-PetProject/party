package com.study.party.member;

import com.study.party.member.vo.MemberAllergyVo;
import com.study.party.member.vo.MemberHateFoodVo;
import com.study.party.member.vo.MemberLikeFoodVo;
import com.study.party.member.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

    List<MemberVo> getMembers(MemberVo memberVo);
    int getMembersTotCnt(MemberVo memberVo);
    MemberVo getMember(MemberVo memberVo);
    MemberVo getMemberByMemberId(MemberVo memberVo);
    int createMember(MemberVo memberVo);
    int updateMember(MemberVo memberVo);
    int deleteMember(MemberVo memberVo);

    List<MemberAllergyVo> getMemberAllergyVos(MemberAllergyVo memberAllergyVo);
    List<MemberHateFoodVo> getMemberHateFoodVos(MemberHateFoodVo memberHateFoodVo);
    List<MemberLikeFoodVo> getMemberLikeFoodVos(MemberLikeFoodVo memberLikeFoodVo);
}
