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
@Schema(description = "공통 코드 VO")
public class CommCodeVo {

    @Schema(description = "공통 코드 PK grp_cd", example="test001")
    private String grpCd;
    @Schema(description = "공통 코드 PK cd", example="test001")
    private String cd;
    @Schema(description = "공통 코드 컬럼 cd_nm", example="코드명")
    private String cmNm;
    @Schema(description = "공통 코드 컬럼 cd_desc", example="코드설명")
    private String cdDesc;
    @Schema(description = "공통 코드 컬럼 cd_val", example="코드기본값")
    private String cdVal;
    @Schema(description = "공통 코드 컬럼 use_yn", example="Y")
    private String useYn;

}
