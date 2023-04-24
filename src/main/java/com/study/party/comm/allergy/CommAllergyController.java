package com.study.party.comm.allergy;

import com.study.party.comm.vo.CommResponseVo;
import com.study.party.jpa.entity.cm_allergy.CmAllergyEntity;
import com.study.party.jpa.entity.cm_food.CmFoodEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comm")
public class CommAllergyController {

    private final CommAllergyService commAllergyService;

    @Operation(summary = "알러지 목록 정보 조회 API", description = "사용자가 입력한 알러지 정보를 LIKE 검색을 하여 데이터 목록 정보를 조회합니다")
    @GetMapping("allergy/{allergy_nm}")
    public ResponseEntity<List<CmAllergyEntity>> getCmAllergies(
        HttpServletRequest request,
        @PathVariable("allergy_nm") String allergy_nm
    ) {
        return CommResponseVo.builder().body(commAllergyService.getCmAllergies(allergy_nm)).build().ok();
    }

    @Operation(summary = "음식 목록 정보 조회 API", description = "사용자가 좋아하는/싫어하는 음식 정보를 LIKE 검색을 하여 데이터 목록 정보를 조회합니다")
    @PostMapping("allergy/{allergy_nm}")
    public ResponseEntity<CmFoodEntity> createCmAllergy(
        HttpServletRequest request,
        @PathVariable("allergy_nm") String allergy_nm
    ) {
        return CommResponseVo.builder().body(commAllergyService.createCmAllergy(allergy_nm)).build().ok();
    }

}
