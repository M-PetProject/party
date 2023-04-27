package com.study.party.team;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.exception.BadRequestException;
import com.study.party.team.vo.TeamVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "팀 목록 조회 API", description = "테이블 team에 등록된 목록 정보를 조회합니다")
    @GetMapping("/teams")
    public ResponseEntity<List<TeamVo>> getTeams(
        HttpServletRequest request,
        @RequestParam(name="team_nm", required=false, defaultValue="") String team_nm
    ) {
        return CommResponseVo.builder().body(teamService.getTeams(TeamVo.builder().teamNm(team_nm).build())).build().ok();
    }

    @Operation(summary = "팀 team_idx 기반 단건 조회 API", description = "테이블 team 의 PK 인 team_idx를 파라미터로 전달 받아 데이터 1건을 조회합니다")
    @GetMapping("/team/{team_idx}")
    public ResponseEntity<TeamVo> getTeam(
        HttpServletRequest request,
        @PathVariable(name="team_idx") long team_idx
    ) {
        if ( team_idx < 1 ) throw new BadRequestException("필수입력값을 확인하세요");

        return CommResponseVo.builder().body(teamService.getTeam(TeamVo.builder().teamIdx(team_idx).build())).build().ok();
    }

    @Operation(summary = "팀 team_idx 기반 단건 조회 API", description = "테이블 team 의 PK 인 team_idx를 파라미터로 전달 받아 데이터 1건을 조회합니다")
    @GetMapping("/team/join-code/{join_code}")
    public ResponseEntity<TeamVo> getTeamByJoinCode(
        HttpServletRequest request,
        @Parameter(name="team_idx" , required=true, description="팀 테이블 컬럼 join_code") @PathVariable(name="join_code") String join_code
    ) {
        if (isEmptyObj(join_code) || join_code.length() < 4) throw new BadRequestException("참여코드를 확인하세요");

        return CommResponseVo.builder()
                             .resultVo(teamService.getTeamByJoinCode(TeamVo.builder()
                                                                           .joinCode(join_code)
                                                                           .build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "팀 생성 API", description = "테이블 team 에 데이터 1건을 생성합니다")
    @PostMapping("team")
    public ResponseEntity createTeam(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @RequestBody TeamVo teamVo
    ) {
        if ( isEmptyObj(teamVo.getTeamNm()) || isEmptyObj(teamVo.getTeamDesc()) ) throw new BadRequestException("필수입력값을 확인하세요");

        teamVo.setMemberIdx(customUserDetailsVo.getMemberIdx());
        teamVo.setMemberType("MASTER");
        return CommResponseVo.builder().resultVo(teamService.createTeam(teamVo)).build().toResponseEntity();
    }

    @Operation(summary = "팀 참가 API", description = "테이블 team 에 데이터 1건을 생성합니다")
    @PostMapping("team/{team_idx}")
    public ResponseEntity joinTeam(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="team_idx" , required=true, description="공지사항 테이블 컬럼 team_idx") @PathVariable(name="team_idx") long team_idx
    ) {
        if ( team_idx < 1 )  throw new BadRequestException("필수입력값을 확인하세요");

        return CommResponseVo.builder()
                             .resultVo(teamService.joinTeam(TeamVo.builder()
                                                                  .teamIdx(team_idx)
                                                                  .memberIdx(customUserDetailsVo.getMemberIdx())
                                                                  .memberType("MEMBER").build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "팀 수정 API", description = "테이블 team 에 데이터 1건을 수정합니다")
    @PutMapping("team")
    public ResponseEntity updateTeam(
        HttpServletRequest request,
        @RequestBody TeamVo teamVo
    ) {
        if ( isEmptyObj(teamVo.getTeamIdx()) || teamVo.getTeamIdx() < 1 || isEmptyObj(teamVo.getTeamNm()) || isEmptyObj(teamVo.getTeamDesc()) )  throw new BadRequestException("필수입력값을 확인하세요");

        return CommResponseVo.builder().resultVo(teamService.updateTeam(teamVo)).build().toResponseEntity();
    }

    @Operation(summary = "팀 수정 API", description = "테이블 team 에 데이터 1건을 수정합니다")
    @DeleteMapping("team/{team_idx}")
    public ResponseEntity deleteTeam(
        HttpServletRequest request,
        @PathVariable(name="team_idx") long team_idx
    ) {
        if ( team_idx < 1 )  throw new BadRequestException("필수입력값을 확인하세요");

        return CommResponseVo.builder().resultVo(teamService.deleteTeam(TeamVo.builder().teamIdx(team_idx).build())).build().toResponseEntity();
    }


}
