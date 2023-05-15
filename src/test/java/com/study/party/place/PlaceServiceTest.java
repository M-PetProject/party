package com.study.party.place;

import com.study.party.jpa.entity.place.PlaceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceServiceTest {


    @Autowired
    private PlaceService placeService;

    @Test
    void getPlaces() {
        List<PlaceEntity> places = placeService.getPlaces();
        System.out.println(places);
    }
}
