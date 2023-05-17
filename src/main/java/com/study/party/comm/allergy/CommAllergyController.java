package com.study.party.comm.allergy;

import com.study.party.comm.vo.CommResponseVo;
import com.study.party.jpa.entity.cm_allergy.CmAllergyEntity;
import com.study.party.jpa.entity.cm_food.CmFoodEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name="comm-allergy-controller", description="공통 알러지 정보 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("comm")
public class CommAllergyController {

    private final CommAllergyService commAllergyService;

    @Operation(summary = "알러지 목록 정보 조회 API", description = "사용자가 입력한 알러지 정보를 LIKE 검색을 하여 데이터 목록 정보를 조회합니다<br>"
                                                              + "사용자가 입력한 알러지 정보가 DB에 존재하지 않을 경우 해당 정보를 DB에 등록 후 Return 합니다")
    @GetMapping("allergy/{allergy_nm}")
    public ResponseEntity<List<CmAllergyEntity>> getCmAllergies(
        HttpServletRequest request,
        @PathVariable("allergy_nm") String allergy_nm
    ) {
        return CommResponseVo.builder().body(commAllergyService.getCmAllergies(allergy_nm)).build().ok();
    }

    @Operation(summary = "알러지 목록 정보 생성 API", description = "사용자가 입력한 알러지 정보를 전달받아 등록 후 해당 정보를 전달합니다")
    @PostMapping("allergy/{allergy_nm}")
    public ResponseEntity<CmFoodEntity> createCmAllergy(
        HttpServletRequest request,
        @PathVariable("allergy_nm") String allergy_nm
    ) {
        return CommResponseVo.builder().body(commAllergyService.createCmAllergy(allergy_nm)).build().ok();
    }

}
