package com.study.party.place;

import com.study.party.comm.vo.CommResponseVo;
import com.study.party.jpa.entity.place.PlaceEntity;
import com.study.party.place.vo.PlaceVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<PlaceVo>> getPlaces(
        HttpServletRequest request
    ) {
        return CommResponseVo.builder()
                .body(placeService.getPlaces())
                .build().ok();
    }

    @Operation(summary = "장소정보 상세조회 API", description = "장소정보")
    @GetMapping("place/{idx}")
    public ResponseEntity<List<PlaceVo>> getPlace(
            HttpServletRequest request,
            @PathVariable long idx
    ) {
        return CommResponseVo.builder()
                .body(placeService.getPlace(idx))
                .build().ok();
    }

    @Operation(summary = "장소정보 등록 API", description = "장소등록")
    @PostMapping("place")
    public ResponseEntity<List<PlaceEntity>> createPlace(
        HttpServletRequest request,
        @RequestBody PlaceEntity placeEntity
    ) {
        return CommResponseVo.builder()
                .resultVo(placeService.createPlace(placeEntity))
                .build().toResponseEntity();
    }

}
