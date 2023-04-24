package com.study.party.comm.food;

import com.study.party.comm.vo.CommResponseVo;
import com.study.party.jpa.entity.cm_food.CmFoodEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comm")
public class CommFoodController {

    private final CommFoodService commFoodService;

    @Operation(summary = "음식 목록 정보 조회 API", description = "사용자가 좋아하는/싫어하는 음식 정보를 LIKE 검색을 하여 데이터 목록 정보를 조회합니다")
    @GetMapping("food/{food_nm}")
    public ResponseEntity<List<CmFoodEntity>> getCmFoods(
        HttpServletRequest request,
        @PathVariable("food_nm") String food_nm
    ) {
        return CommResponseVo.builder().body(commFoodService.getCmFoods(food_nm)).build().ok();
    }

    @Operation(summary = "음식 목록 정보 조회 API", description = "사용자가 좋아하는/싫어하는 음식 정보를 LIKE 검색을 하여 데이터 목록 정보를 조회합니다")
    @PostMapping("food/{food_nm}")
    public ResponseEntity<CmFoodEntity> createCmFood(
        HttpServletRequest request,
        @PathVariable("food_nm") String food_nm
    ) {
        return CommResponseVo.builder().body(commFoodService.createCmFood(food_nm)).build().ok();
    }

}
