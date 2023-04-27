package com.study.party.team;

import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.team.vo.TeamVo;
import com.study.party.team_member.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.study.party.comm.util.StringUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamDao teamDao;

    private final TeamMemberService teamMemberService;

    public List<TeamVo> getTeams(TeamVo teamVo) {
        return teamDao.getTeams(teamVo);
    }

    public TeamVo getTeam(TeamVo teamVo) {
        return teamDao.getTeam(teamVo);
    }

    public CommResultVo getTeamByJoinCode(TeamVo teamVo) {
        TeamVo team = teamDao.getTeamByJoinCode(teamVo);
        if (isEmptyObj(team)) throw new BadRequestException("참여코드를 확인하세요");
        return CommResultVo.builder().code(200).data(team).build();
    }

    @Transactional
    public CommResultVo createTeam(TeamVo teamVo) {
        if ( teamDao.createTeam(teamVo) < 1 ) throw new InternalServerErrorException("팀 생성에 실패하였습니다");
        teamVo.setJoinCode(lPadding(nvl(teamVo.getTeamIdx(), "0"), "0", 4));
        if ( teamDao.updateTeam(teamVo) < 1 ) throw new InternalServerErrorException("팀 생성에 실패하였습니다");
        if ( teamMemberService.createTeamMember(teamVo.toTeamMemberVo()) < 1 ) throw new InternalServerErrorException("팀 생성에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("팀 생성 되었습니다").build();
    }

    @Transactional
    public CommResultVo joinTeam(TeamVo teamVo) {
        if (isEmptyObj(teamDao.getTeam(teamVo))) throw new BadRequestException("존재하지 않는 팀입니다");
        if (teamMemberService.createTeamMember(teamVo.toTeamMemberVo()) < 1) throw new InternalServerErrorException("가입에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("가입완료 되었습니다").build();
    }

    @Transactional
    public CommResultVo updateTeam(TeamVo teamVo) {
        if ( teamDao.updateTeam(teamVo) < 1 ) throw new InternalServerErrorException("팀 수정에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("수정되었습니다").build();

    }

    @Transactional
    public CommResultVo deleteTeam(TeamVo teamVo) {
        if ( teamDao.deleteTeam(teamVo) < 1 ) throw new InternalServerErrorException("팀 삭제에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("삭제되었습니다").build();
    }

}
