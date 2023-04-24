package com.study.party.comm.food;

import com.study.party.jpa.entity.cm_food.CmFoodEntity;
import com.study.party.jpa.repository.cm_food.CmFoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommFoodService {

    private final CmFoodRepository cmFoodRepository;

    @Transactional
    public List<CmFoodEntity> getCmFood(String foodNm) {
        List<CmFoodEntity> result = cmFoodRepository.findByFoodNmLikeOrderByFoodIdxDesc("%"+foodNm+"%");
        if (isEmptyObj(result)) {
            CmFoodEntity cmFoodEntity = CmFoodEntity.builder().foodNm(foodNm).build();
            cmFoodRepository.save(cmFoodEntity);
            return Arrays.asList(cmFoodEntity);
        } else {
            return result;
        }
    }

    @Transactional
    public CmFoodEntity createCmFood(String foodNm) {
        CmFoodEntity result = cmFoodRepository.findByFoodNm(foodNm);
        if (isEmptyObj(result)) {
            CmFoodEntity cmFoodEntity = CmFoodEntity.builder().foodNm(foodNm).build();
            cmFoodRepository.save(cmFoodEntity);
            return cmFoodEntity;
        } else {
            return result;
        }
    }

}
