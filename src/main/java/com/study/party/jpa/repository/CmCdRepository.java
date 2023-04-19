package com.study.party.jpa.repository;

import com.study.party.jpa.entity.cd.CmCdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmCdRepository extends JpaRepository<CmCdEntity, String> {

    List<CmCdEntity> findByGrpCdOrderByCd(String grpCd);
    CmCdEntity findByGrpCdAndCd(String grpcd, String cd);

}
