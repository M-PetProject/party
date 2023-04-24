package com.study.party.jpa.repository.cm_food;

import com.study.party.jpa.entity.cm_food.CmFoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmFoodRepository extends JpaRepository<CmFoodEntity, Long> {

    List<CmFoodEntity> findByFoodNmLikeOrderByFoodIdxDesc(String foodNm);
    CmFoodEntity findByFoodNm(String foodNm);

}
