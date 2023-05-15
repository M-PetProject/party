package com.study.party.place;

import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.jpa.entity.place.PlaceEntity;
import com.study.party.jpa.repository.place.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public List<PlaceEntity> getPlaces() {
        return placeRepository.findAll();
    }

    @Transactional
    public CommResultVo createPlace(PlaceEntity placeEntity) {
        placeRepository.save(placeEntity);
        return CommResultVo.builder().code(200).msg("장소가 등록되었습니다.").build();
    }

    @Transactional
    public CommResultVo updatePlace(PlaceEntity placeEntity) {
        placeRepository.save(placeEntity);
        return CommResultVo.builder().code(200).msg("장소가 수정되었습니다.").build();
    }
    @Transactional
    public CommResultVo deletePlace(PlaceEntity placeEntity) {
        Optional<PlaceEntity> place = placeRepository.findById(placeEntity.getPlaceBasicInfoIdx());
        if(!place.isEmpty()) {
            placeRepository.delete(place.get());
        } else {
            throw new InternalServerErrorException("장소 삭제에 실패했습니다.");
        }

        return CommResultVo.builder().code(200).msg("장소가 삭제되었습니다.").build();
    }
}
