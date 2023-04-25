package com.study.party.jpa.entity.cm_grpcd;

import com.study.party.jpa.entity.comm.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cm_grp_cd")
@Entity
public class CmGrpCdEntity extends DefaultEntity {

    private static final long serialVersionUID = 929086865875164181L;

    @Id
    private String grpCd;
    private String grpCdNm;
    private String grpCdDesc;
    private String useYn;

}
