package com.study.party.comm.code.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공통 그룹 코드 VO")
public class CommGrpCodeVo {

    @Schema(description = "공통 그룹 코드 PK grp_cd", example="test001")
    private String grpCd;
    @Schema(description = "공통 그룹 코드 컬럼 grp_cd_nm", example="그룹코드명")
    private String grpCdNm;
    @Schema(description = "공통 그룹 코드 컬럼 grp_cd_desc", example="그룹코드설명")
    private String grpCdDesc;
    @Schema(description = "공통 그룹 코드 컬럼 use_yn", example="Y")
    private String useYn;

}
