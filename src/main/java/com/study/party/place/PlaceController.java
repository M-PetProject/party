package com.study.party.place;

import com.study.party.comm.vo.CommResponseVo;
import com.study.party.jpa.entity.place.PlaceEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name="place-controller", description="장소 정보 API")
@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "장소정보 목록조회 API", description = "장소목록")
    @GetMapping("places")
    public ResponseEntity<List<PlaceEntity>> getPlaces(
        HttpServletRequest request
    ) {
        return CommResponseVo.builder()
                .body(placeService.getPlaces())
                .build().ok();
    }
}
