package com.study.party.jpa.entity.cm_cd;

import lombok.Data;

import java.io.Serializable;

@Data
public class CmCdEntityPk implements Serializable {

    private static final long serialVersionUID = 4103006653445751386L;

    private String grpCd; /* varchar-그룹_코드 */
    private String cd;     /* varchar-코드 */

}
