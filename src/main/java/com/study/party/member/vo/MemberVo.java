package com.study.party.member.vo;

import com.study.party.comm.vo.CommPaginationReqVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVo extends CommPaginationReqVo {

    private String memberIdx;
    private String memberId;
    private String memberPassword;
    private String memberName;

}
