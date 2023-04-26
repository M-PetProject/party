package com.study.party.comm.test;

import com.study.party.comm.test.vo.CommTestVo;
import com.study.party.comm.vo.CommPaginationResVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@RestController
@RequiredArgsConstructor
public class CommTestController {

    private final CommTestService commTestService;

    @Operation(summary = "테스트 목록 조회 메서드", description = "테이블 test 의 목록 정보를 가지고 옵니다")
    @GetMapping("/comm/tests")
    public ResponseEntity<CommPaginationResVo<List<CommTestVo>>> getTests(
        HttpServletRequest request,
        @Parameter(name="test1" , required=false, description="table test column"   ) @RequestParam(name="test1" , required=false) String test1,
        @Parameter(name="test2" , required=false, description="table test column"   ) @RequestParam(name="test2" , required=false) String test2,
        @Parameter(name="pageNo", required=false, description="pagination parameter") @RequestParam(name="pageNo", required=false, defaultValue="1") int pageNo,
        @Parameter(name="limit" , required=false, description="pagination parameter") @RequestParam(name="limit" , required=false, defaultValue="5") int limit
    ) {
        return CommResponseVo.builder()
                             .resultVo(commTestService.getTests(CommTestVo.builder()
                                                                          .test1(test1)
                                                                          .test2(test2)
                                                                          .pageNo(pageNo)
                                                                          .limit(limit)
                                                                          .build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "테스트 상세 조회 메서드", description = "테이블 test 의 pk 인 idx 를 파라미터로 전달 받아 데이터 1건을 조회합니다")
    @GetMapping("/comm/test")
    public ResponseEntity<CommTestVo> getTest(
        HttpServletRequest request,
        @RequestParam(name="idx", required=false, defaultValue="0") long idx
    ) {
        if ( idx < 1 ) throw new BadRequestException("필수입력값을 확인하세요");

        return CommResponseVo.builder()
                             .resultVo(commTestService.getTest(CommTestVo.builder().idx(idx).build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "테스트 생성 메서드", description = "테이블 test에 데이터 1건을 생성합니다")
    @PostMapping("/comm/test")
    public ResponseEntity createTest(
        HttpServletRequest request,
        @RequestBody CommTestVo commTestVo
    ) {
        if ( isEmptyObj(commTestVo.getTest1()) || isEmptyObj(commTestVo.getTest2()) ) throw new BadRequestException("필수입력값을 확인하세요");

        return CommResponseVo.builder()
                             .resultVo(commTestService.createTest(commTestVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "테스트 수정 메서드", description = "테이블 test에 데이터 1건을 수정합니다")
    @PutMapping("/comm/test")
    public ResponseEntity updateTest(
        HttpServletRequest request,
        @RequestBody CommTestVo commTestVo
    ) {
        if ( commTestVo.getIdx() == 0 || isEmptyObj(commTestVo.getTest1()) || isEmptyObj(commTestVo.getTest2()) ) throw new BadRequestException("필수입력값을 확인하세요");

        return CommResponseVo.builder()
                             .resultVo(commTestService.updateTest(commTestVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "테스트 삭제 메서드", description = "테이블 test 의 pk 인 idx 를 파라미터로 전달 받아 데이터 1건을 삭제합니다")
    @DeleteMapping("/comm/test")
    public ResponseEntity deleteTest(
        HttpServletRequest request,
        @RequestParam(name="idx", required=false, defaultValue="0") long idx
    ) {
        if ( idx < 1 ) throw new BadRequestException("필수입력값을 확인하세요");

        return CommResponseVo.builder()
                             .resultVo(commTestService.deleteTest(CommTestVo.builder().idx(idx).build()))
                             .build()
                             .toResponseEntity();
    }

}
