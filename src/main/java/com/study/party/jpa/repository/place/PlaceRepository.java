package com.study.party.jpa.repository.place;

import com.study.party.jpa.entity.place.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {
    List<PlaceEntity> findAllByDeleteYn(String deleteYn);
}
