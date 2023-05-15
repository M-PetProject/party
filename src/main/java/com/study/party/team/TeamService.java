package com.study.party.team;

import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.exception.UnauthorizedException;
import com.study.party.team.vo.TeamVo;
import com.study.party.team_member.TeamMemberService;
import com.study.party.team_member.vo.TeamMemberVo;
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
        TeamVo resultTeamVo = teamDao.getTeam(teamVo);
        resultTeamVo.setTeamMemberVoList(teamMemberService.getMembersByTeamIdx(teamVo.getTeamIdx()));
        return resultTeamVo;
    }

    public CommResultVo getTeamByJoinCode(TeamVo teamVo) {
        TeamVo team = teamDao.getTeamByJoinCode(teamVo);
        if (isEmptyObj(team)) throw new BadRequestException("모임의 참여코드를 확인하세요");
        return CommResultVo.builder().code(200).data(team).build();
    }

    @Transactional
    public CommResultVo createTeam(TeamVo teamVo) {
        if ( teamDao.createTeam(teamVo) < 1 ) throw new InternalServerErrorException("모임 생성에 실패하였습니다");
        teamVo.setJoinCode(lPadding(nvl(teamVo.getTeamIdx(), "0"), "0", 4));
        if ( teamDao.updateTeam(teamVo) < 1 ) throw new InternalServerErrorException("모임 생성에 실패하였습니다");
        if ( teamMemberService.createTeamMember(teamVo.toTeamMemberVo()) < 1 ) throw new InternalServerErrorException("모임 생성에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("모임이 생성 되었습니다").build();
    }

    @Transactional
    public CommResultVo joinTeam(TeamVo teamVo) {
        if (isEmptyObj(teamDao.getTeam(teamVo))) throw new BadRequestException("존재하지 않는 모임입니다");

        TeamMemberVo teamMember = teamMemberService.getTeamMember(TeamMemberVo.builder().teamIdx(teamVo.getTeamIdx()).memberIdx(teamVo.getMemberIdx()).build());
        if (!isEmptyObj(teamMember)) throw new BadRequestException("이미 가입한 모임입니다");

        if (teamMemberService.createTeamMember(teamVo.toTeamMemberVo()) < 1) throw new InternalServerErrorException("가입에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("가입완료 되었습니다").build();
    }

    @Transactional
    public CommResultVo updateTeam(TeamVo teamVo) {
        TeamVo team = teamDao.getTeam(teamVo);
        if (isEmptyObj(team)) throw new BadRequestException("존재하지 않는 모임입니다");

        TeamMemberVo teamMember = teamMemberService.getTeamMember(TeamMemberVo.builder().teamIdx(teamVo.getTeamIdx()).memberIdx(teamVo.getMemberIdx()).build());
        if ( isEmptyObj(teamMember) ) throw new UnauthorizedException("참여한 모임이 아닙니다");

        if(!"MASTER".equals(teamMember.getMemberType())) throw new UnauthorizedException("모임의 리더만 모임을 수정할 수 있습니다");

        if ( teamDao.updateTeam(teamVo) < 1 ) throw new InternalServerErrorException("모임 수정에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("수정되었습니다").build();
    }

    @Transactional
    public CommResultVo deleteTeam(TeamVo teamVo) {
        TeamVo team = teamDao.getTeam(teamVo);
        if (isEmptyObj(team)) throw new BadRequestException("존재하지 않는 모임입니다");

        TeamMemberVo teamMember = teamMemberService.getTeamMember(TeamMemberVo.builder().teamIdx(teamVo.getTeamIdx()).memberIdx(teamVo.getMemberIdx()).build());
        if ( isEmptyObj(teamMember) ) throw new UnauthorizedException("참여한 모임이 아닙니다");

        if(!"MASTER".equals(teamMember.getMemberType())) throw new UnauthorizedException("모임의 리더만 모임을 종료할 수 있습니다");

        if ( teamDao.deleteTeam(teamVo) < 1 ) throw new InternalServerErrorException("모임 삭제에 실패하였습니다");
        return CommResultVo.builder().code(200).msg("삭제되었습니다").build();
    }

    @Transactional
    public CommResultVo deleteTeamMember(TeamVo teamVo) {
        TeamVo team = teamDao.getTeam(teamVo);
        if (isEmptyObj(team)) throw new BadRequestException("존재하지 않는 모임입니다");

        TeamMemberVo teamMember = teamMemberService.getTeamMember(TeamMemberVo.builder().teamIdx(teamVo.getTeamIdx()).memberIdx(teamVo.getMemberIdx()).build());
        if ( isEmptyObj(teamMember) ) throw new UnauthorizedException("참여한 모임이 아닙니다");

        long deleteMember = teamVo.getTeamMemberVoList().get(0).getMemberIdx();
        if ( teamVo.getMemberIdx() == deleteMember ) {
            teamMemberService.deleteTeamMember(TeamMemberVo.builder().teamIdx(teamVo.getTeamIdx()).memberIdx(deleteMember).build());
            if("MASTER".equals(teamMember.getMemberType())) {
                List<TeamMemberVo> teamMembers = teamMemberService.getMembersByTeamIdx(teamVo.getTeamIdx());
                if (isEmptyObj(teamMembers)) {
                    teamDao.deleteTeam(teamVo);
                } else {
                    TeamMemberVo newMaster = teamMembers.get(0);
                    newMaster.setMemberType("MASTER");
                    teamMemberService.updateTeamMember(newMaster);
                }
            }
        } else {
            if(!"MASTER".equals(teamMember.getMemberType())) throw new UnauthorizedException("모임의 리더만 멤버를 탈퇴시킬 수 있습니다");
            teamMemberService.deleteTeamMember(TeamMemberVo.builder().teamIdx(teamVo.getTeamIdx()).memberIdx(deleteMember).build());
        }

        return CommResultVo.builder().code(200).msg("삭제되었습니다").build();
    }

}
