package com.study.party.comm.allergy;

import com.study.party.jpa.entity.cm_allergy.CmAllergyEntity;
import com.study.party.jpa.entity.cm_food.CmFoodEntity;
import com.study.party.jpa.repository.cm_allergy.CmAllergyRepository;
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
public class CommAllergyService {

    private final CmAllergyRepository cmAllergyRepository;

    @Transactional
    public List<CmAllergyEntity> getCmAllergies(String allergy_nm) {
        List<CmAllergyEntity> result = cmAllergyRepository.findByAllergyNmLikeOrderByAllergyIdxDesc("%"+allergy_nm+"%");
        if (isEmptyObj(result)) {
            CmAllergyEntity cmAllergyEntity = CmAllergyEntity.builder().allergyNm(allergy_nm).build();
            cmAllergyRepository.save(cmAllergyEntity);
            return Arrays.asList(cmAllergyEntity);
        } else {
            return result;
        }
    }

    @Transactional
    public CmAllergyEntity createCmAllergy(String allergy_nm) {
        CmAllergyEntity result = cmAllergyRepository.findByAllergyNm(allergy_nm);
        if (isEmptyObj(result)) {
            CmAllergyEntity cmAllergyEntity = CmAllergyEntity.builder().allergyNm(allergy_nm).build();
            cmAllergyRepository.save(cmAllergyEntity);
            return cmAllergyEntity;
        } else {
            return result;
        }
    }

}
