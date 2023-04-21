package com.study.party.team;

import com.study.party.team.vo.TeamVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamDao {

    List<TeamVo> getTeams(TeamVo teamVo);
    TeamVo getTeam(TeamVo teamVo);
    TeamVo getTeamByJoinCode(TeamVo teamVo);
    int createTeam(TeamVo teamVo);
    int updateTeam(TeamVo teamVo);
    int deleteTeam(TeamVo teamVo);


}
