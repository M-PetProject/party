package com.study.party.jpa.entity.cd;

import com.study.party.jpa.entity.comm.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cm_cd")
@Entity
@IdClass(CmCdEntityPk.class)
public class CmCdEntity extends DefaultEntity {

    private static final long serialVersionUID = 4212692836776014643L;

    @Id
    private String grpCd;
    @Id
    private String cd;
    private String cdNm;
    private String cdDesc;
    private String cdVal;
    private String useYn;

}
