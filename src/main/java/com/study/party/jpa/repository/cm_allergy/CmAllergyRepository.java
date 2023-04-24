package com.study.party.jpa.repository.cm_allergy;

import com.study.party.jpa.entity.cm_allergy.CmAllergyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmAllergyRepository extends JpaRepository<CmAllergyEntity, Long> {

    List<CmAllergyEntity> findByAllergyNmLikeOrderByAllergyIdxDesc(String foodNm);
    CmAllergyEntity findByAllergyNm(String foodNm);

}
