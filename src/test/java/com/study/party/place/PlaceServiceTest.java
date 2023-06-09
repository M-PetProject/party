package com.study.party.place;

import com.study.party.place.dto.PlaceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PlaceServiceTest {


    @Autowired
    private PlaceService placeService;

    @Test
    void getPlaces() {
        List<PlaceDto> places = placeService.getPlaces();
        System.out.println(places);
    }

    @Test
    void createPlace() {
//        PlaceVo placeVo = PlaceVo.builder()
//                .creatorMemberIdx(1)
//                .name("다디치1")
//                .intro("intro")
//                .rating(3.4)
//                .businessHours("pm 6")
//                .imageUrl("image url")
//                .extUrl("ext Url")
//                .publicYn("Y")
//                .deleteYn("N")
//                .build();
//
//        placeService.createPlace(placeVo);
    }

    @Test
    void updatePlace() {
//        PlaceVo placeVo = PlaceVo.builder()
//                .placeBasicInfoIdx(1)
//                .creatorMemberIdx(1)
//                .name("다디치2")
//                .intro("intro2")
//                .rating(53.0)
//                .businessHours("pm 6")
//                .imageUrl("image url")
//                .extUrl("ext Url")
//                .publicYn("Y")
//                .deleteYn("N")
//                .build();

//        placeService.updatePlace(placeVo, 1);
    }

    @Test
    void deletePlace() {
//        PlaceVo placeVo = PlaceVo.builder()
//                .placeBasicInfoIdx(1)
//                .build();

//        placeService.deletePlace(placeVo, 1);
    }

    @Test
    void getPlace(){
        long idx = 2;
        PlaceDto place = placeService.getPlace(idx);
        System.out.println(place);
    }


}
