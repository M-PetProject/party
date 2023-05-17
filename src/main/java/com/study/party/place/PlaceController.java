package com.study.party.place;

import com.study.party.auth.vo.CustomUserDetailsVo;
import com.study.party.comm.vo.CommResponseVo;
import com.study.party.place.dto.PlaceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name="place-controller", description="장소 정보 API")
@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "장소정보 목록조회 API", description = "장소목록")
    @GetMapping("places")
    public ResponseEntity<List<PlaceDto>> getPlaces(
        HttpServletRequest request
    ) {
        return CommResponseVo.builder()
                .body(placeService.getPlaces())
                .build().ok();
    }

    @Operation(summary = "장소정보 상세조회 API", description = "장소정보")
    @GetMapping("place/{idx}")
    public ResponseEntity<List<PlaceDto>> getPlace(
            HttpServletRequest request,
            @PathVariable long idx
    ) {
        return CommResponseVo.builder()
                .body(placeService.getPlace(idx))
                .build().ok();
    }

    @Operation(summary = "장소정보 등록 API", description = "장소등록")
    @PostMapping("place")
    public ResponseEntity createPlace(
        HttpServletRequest request,
        @RequestBody PlaceDto placeDto
    ) {
        return CommResponseVo.builder()
                .resultVo(placeService.createPlace(placeDto))
                .build().toResponseEntity();
    }

    @Operation(summary = "장소정보 수정 API", description = "장소수정")
    @PutMapping("place")
    public ResponseEntity updatePlace(
        HttpServletRequest request,
        @RequestBody PlaceDto placeDto,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo
    ) {
        return CommResponseVo.builder()
                .resultVo(placeService.updatePlace(placeDto, customUserDetailsVo.getMemberIdx()))
                .build().toResponseEntity();
    }

    @Operation(summary = "장소정보 삭제 API", description = "장소삭제")
    @DeleteMapping("place/{placeBasicInfoIdx}")
    public ResponseEntity deletePlace(
        HttpServletRequest request,
        @PathVariable long placeBasicInfoIdx,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo
    ) {
        return CommResponseVo.builder()
                .resultVo(placeService.deletePlace(placeBasicInfoIdx, customUserDetailsVo.getMemberIdx()))
                .build().toResponseEntity();
    }

}
