package com.study.party.team_member;

import com.study.party.team_member.vo.TeamMemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamMemberDao {

    List<TeamMemberVo> getMembersByTeamIdx(TeamMemberVo teamMemberVo); // 팀 기준 회원 팀멤버 목록
    List<TeamMemberVo> getTeamsByMemberIdx(TeamMemberVo teamMemberVo); // 회원 기준 팀 팀멤버 목록
    TeamMemberVo getTeamMember(TeamMemberVo teamMemberVo); // 회원, 팀 기준 팀멤버

    int createTeamMember(TeamMemberVo teamMemberVo); // 회원, 팀 기준 팀멤버
    int updateTeamMember(TeamMemberVo teamMemberVo); // 회원, 팀 기준 팀멤버
    int deleteTeamMember(TeamMemberVo teamMemberVo); // 회원, 팀 기준 팀멤버


}
