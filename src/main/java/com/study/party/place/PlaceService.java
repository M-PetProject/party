package com.study.party.place;

import com.study.party.jpa.entity.place.PlaceEntity;
import com.study.party.jpa.repository.place.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public List<PlaceEntity> getPlaces() {
        return placeRepository.findAll();
    }
}
