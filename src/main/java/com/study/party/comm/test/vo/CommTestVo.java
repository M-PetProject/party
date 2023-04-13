package com.study.party.comm.test.vo;

import com.study.party.comm.vo.CommPaginationReqVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "테스트 VO")
public class CommTestVo extends CommPaginationReqVo {

    @Schema(description="table PK idx")
    private long idx;
    @Schema(description="table column test_1")
    private String test1;
    @Schema(description="table column test_2")
    private String test2;

}
