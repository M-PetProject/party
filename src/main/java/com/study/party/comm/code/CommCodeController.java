package com.study.party.comm.code;

import com.study.party.comm.code.vo.CommCodeVo;
import com.study.party.comm.code.vo.CommGrpCodeVo;
import com.study.party.comm.vo.CommResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Tag(name="comm-code-controller", description="공통 코드 정보 API")
@RestController
@RequestMapping("comm")
@RequiredArgsConstructor
public class CommCodeController {

    private final CommCodeService commCodeService;

    @Operation(summary = "공통 그룹 코드 목록 조회 API", description = "테이블 cm_grp_cd 의 목록 정보를 가지고 옵니다")
    @GetMapping("/grp-cds")
    public ResponseEntity<List<CommGrpCodeVo>> getGrpCds(
        HttpServletRequest request
    ) {
        return CommResponseVo.builder()
                             .body(commCodeService.getCommGrpCodes(CommGrpCodeVo.builder().build()))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 그룹 코드 상세 조회 API", description = "테이블 cm_grp_cd 의 PK grp_cd 를 파라미터로 전달받아 데이터 1건을 조회합니다")
    @GetMapping("/grp-cd")
    public ResponseEntity<CommGrpCodeVo> getGrpCd(
        HttpServletRequest request,
        @RequestParam(name="grp_cd", required=false, defaultValue="") String grp_cd
    ) {
        if (isEmptyObj(grp_cd)) return CommResponseVo.builder().body("grp_cd 는 필수 값 입니다").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.getCommGrpCode(CommGrpCodeVo.builder().grpCd(grp_cd).build()))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 그룹 코드 생성 API", description = "테이블 cm_grp_cd 에 데이터 1건을 생성합니다")
    @PostMapping("/grp-cd")
    public ResponseEntity createGrpCd(
        HttpServletRequest request,
        @RequestBody CommGrpCodeVo commGrpCodeVo
    ) {
        if (isEmptyObj(commGrpCodeVo.getGrpCd()) || isEmptyObj(commGrpCodeVo.getGrpCdNm()) || isEmptyObj(commGrpCodeVo.getUseYn())) return CommResponseVo.builder().body("필수 값을 확인하세요").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.createCommGrpCode(commGrpCodeVo))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 그룹 코드 수정 API", description = "테이블 cm_grp_cd 에 데이터 1건을 수정합니다")
    @PutMapping("/grp-cd")
    public ResponseEntity updateGrpCd(
        HttpServletRequest request,
        @RequestBody CommGrpCodeVo commGrpCodeVo
    ) {
        if (isEmptyObj(commGrpCodeVo.getGrpCd()) || isEmptyObj(commGrpCodeVo.getGrpCdNm()) || isEmptyObj(commGrpCodeVo.getUseYn())) return CommResponseVo.builder().body("필수 값을 확인하세요").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.updateCommGrpCode(commGrpCodeVo))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 그룹 코드 삭제 API", description = "테이블 cm_grp_cd 의 PK 인 grp_cd를 파라미터로 전달받아 데이터 1건을 삭제합니다")
    @DeleteMapping("/grp-cd")
    public ResponseEntity deleteGrpCd(
        HttpServletRequest request,
        @RequestParam(name="grp_cd", required=false, defaultValue="test001") String grp_cd
    ) {
        if (isEmptyObj(grp_cd)) return CommResponseVo.builder().body("grp_cd 는 필수 값 입니다").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.deleteCommGrpCode(CommGrpCodeVo.builder().grpCd(grp_cd).build()))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 코드 목록 조회 API", description = "테이블 cm_grp_cd 의 목록 정보를 가지고 옵니다")
    @GetMapping("/cds")
    public ResponseEntity<List<CommCodeVo>> getCds(
        HttpServletRequest request
    ) {
        return CommResponseVo.builder()
                             .body(commCodeService.getCommCodes(CommCodeVo.builder().build()))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 코드 상세 조회 API", description = "테이블 cm_grp_cd 의 PK 인 grp_cd 와 cd 를 파라미터로 전달받아 데이터 1건을 조회합니다")
    @GetMapping("/cd")
    public ResponseEntity<CommCodeVo> getCd(
        HttpServletRequest request,
        @RequestParam(name="grp_cd", required=false, defaultValue="") String grp_cd,
        @RequestParam(name="cd", required=false, defaultValue="") String cd
    ) {
        if (isEmptyObj(grp_cd) || isEmptyObj(cd)) return CommResponseVo.builder().body("grp_cd 는 필수 값 입니다").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.getCommCode(CommCodeVo.builder().grpCd(grp_cd).cd(cd).build()))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 코드 생성 API", description = "테이블 cm_grp_cd 에 데이터 1건을 생성합니다")
    @PostMapping("/cd")
    public ResponseEntity createCd(
        HttpServletRequest request,
        @RequestBody CommCodeVo commCodeVo
    ) {
        if (isEmptyObj(commCodeVo.getGrpCd()) ||  isEmptyObj(commCodeVo.getCd()) || isEmptyObj(commCodeVo.getCmNm()) || isEmptyObj(commCodeVo.getUseYn())) return CommResponseVo.builder().body("필수 값을 확인하세요").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.createCommCode(commCodeVo))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 코드 수정 API", description = "테이블 cm_grp_cd 에 데이터 1건을 수정합니다")
    @PutMapping("/cd")
    public ResponseEntity updateCd(
        HttpServletRequest request,
        @RequestBody CommCodeVo commCodeVo
    ) {
        if (isEmptyObj(commCodeVo.getGrpCd()) ||  isEmptyObj(commCodeVo.getCd()) || isEmptyObj(commCodeVo.getCmNm()) || isEmptyObj(commCodeVo.getUseYn())) return CommResponseVo.builder().body("필수 값을 확인하세요").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.updateCommCode(commCodeVo))
                             .build()
                             .ok();
    }

    @Operation(summary = "공통 코드 삭제 API", description = "테이블 cm_cd 의 PK 인 grp_cd 와 cd 를 파라미터로 전달받아 데이터 1건을 삭제합니다")
    @DeleteMapping("/cd")
    public ResponseEntity deleteGrpCd(
        HttpServletRequest request,
        @RequestParam(name="grp_cd", required=false, defaultValue="") String grp_cd,
        @RequestParam(name="cd", required=false, defaultValue="") String cd
    ) {
        if (isEmptyObj(grp_cd) || isEmptyObj(cd)) return CommResponseVo.builder().body("grp_cd, cd 는 필수 값 입니다").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.deleteCommCode(CommCodeVo.builder().grpCd(grp_cd).cd(cd).build()))
                             .build()
                             .ok();
    }

    @Operation(summary = "JPA를 사용한 공통 코드 목록 조회 API", description = "테이블 cm_cd 의 목록을 JPA로 조회합니다")
    @GetMapping("jpa/cds")
    public ResponseEntity<List<CommCodeVo>> getJpaCds(
        HttpServletRequest request,
        @RequestParam(name="grp_cd", required=false, defaultValue="") String grp_cd
    ) {
        if (isEmptyObj(grp_cd)) return CommResponseVo.builder().body("grp_cd 는 필수 값 입니다").build().badRequest();

        return CommResponseVo.builder()
                             .body(commCodeService.findByGrpCdOrderByCd(grp_cd))
                             .build()
                             .ok();
    }
}
