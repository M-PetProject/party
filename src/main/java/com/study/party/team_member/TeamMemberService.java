package com.study.party.team_member;

import com.study.party.team_member.vo.TeamMemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberDao teamMemberDao;

    public List<TeamMemberVo> getMembersByTeamIdx(long teamIdx) {
        return getMembersByTeamIdx(TeamMemberVo.builder().teamIdx(teamIdx).build());
    }

    public List<TeamMemberVo> getMembersByTeamIdx(TeamMemberVo teamMemberVo) {
        return teamMemberDao.getMembersByTeamIdx(teamMemberVo);
    }

    public List<TeamMemberVo> getTeamsByMemberIdx(long memberIdx) {
        return getTeamsByMemberIdx(TeamMemberVo.builder().memberIdx(memberIdx).build());
    }

    public List<TeamMemberVo> getTeamsByMemberIdx(TeamMemberVo teamMemberVo) {
        return teamMemberDao.getTeamsByMemberIdx(teamMemberVo);
    }

    public TeamMemberVo getTeamMember(TeamMemberVo teamMemberVo) {
        return teamMemberDao.getTeamMember(teamMemberVo);
    }

    public int createTeamMember(TeamMemberVo teamMemberVo) {
        return teamMemberDao.createTeamMember(teamMemberVo);
    }

    public int updateTeamMember(TeamMemberVo teamMemberVo) {
        return teamMemberDao.updateTeamMember(teamMemberVo);
    }

    public int deleteTeamMember(TeamMemberVo teamMemberVo) {
        return teamMemberDao.deleteTeamMember(teamMemberVo);
    }

}
