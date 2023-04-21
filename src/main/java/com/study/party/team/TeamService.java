package com.study.party.team;

import com.study.party.team.vo.TeamVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.study.party.comm.util.StringUtil.lPadding;
import static com.study.party.comm.util.StringUtil.nvl;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamDao teamDao;

    public List<TeamVo> getTeams(TeamVo teamVo) {
        return teamDao.getTeams(teamVo);
    }

    public TeamVo getTeam(TeamVo teamVo) {
        return teamDao.getTeam(teamVo);
    }

    public TeamVo getTeamByJoinCode(TeamVo teamVo) {
        return teamDao.getTeamByJoinCode(teamVo);
    }

    public int createTeam(TeamVo teamVo) {
        teamDao.createTeam(teamVo);
        teamVo.setJoinCode(lPadding(nvl(teamVo.getTeamIdx(), "0"), "0", 4));
        teamDao.updateTeam(teamVo);
        return 1;
    }

    public int updateTeam(TeamVo teamVo) {
        return teamDao.updateTeam(teamVo);
    }

    public int deleteTeam(TeamVo teamVo) {
        return teamDao.deleteTeam(teamVo);
    }

}
