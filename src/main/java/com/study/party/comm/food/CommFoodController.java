package com.study.party.comm.food;

import com.study.party.comm.vo.CommResponseVo;
import com.study.party.jpa.entity.cm_food.CmFoodEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name="comm-food-controller", description="공통 음식 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("comm")
public class CommFoodController {

    private final CommFoodService commFoodService;

    @Operation(summary = "음식 목록 정보 조회 API", description = "사용자가 좋아하는/싫어하는 음식 정보를 LIKE 검색을 하여 데이터 목록 정보를 조회합니다<br>"
                                                             + "사용자가 입력한 좋아하는/싫어하는 음식의 정보가 DB에 존재하지 않을 경우 해당 정보를 DB에 등록 후 Return 합니다")
    @GetMapping("food/{food_nm}")
    public ResponseEntity<List<CmFoodEntity>> getCmFoods(
        HttpServletRequest request,
        @PathVariable("food_nm") String food_nm
    ) {
        return CommResponseVo.builder().body(commFoodService.getCmFoods(food_nm)).build().ok();
    }

    @Operation(summary = "음식 목록 정보 조회 API", description = "사용자가 입력한 좋아하는/싫어하는 음식 정보를 전달받아 등록 후 해당 정보를 전달합니다")
    @PostMapping("food/{food_nm}")
    public ResponseEntity<CmFoodEntity> createCmFood(
        HttpServletRequest request,
        @PathVariable("food_nm") String food_nm
    ) {
        return CommResponseVo.builder().body(commFoodService.createCmFood(food_nm)).build().ok();
    }

}
