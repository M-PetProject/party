package com.study.party.jpa.entity.diningMain;


import com.study.party.jpa.entity.comm.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "dining_joiner")
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_idx")
    private long memberIdx;

    @Column(name="team_idx")
    private long teamIdx;
    //team 테이블과 연관관계 있음. 그러나 지금 dining main 과 관련하여 생성하였기 때문에 연관관계를 따로 맺지 않았음. 추후 본 도메인 개발자가 수정.

    @Column(name = "team_nm")
    private String teamNm;

    @Column(name = "member_id")
    private String memberId;
    @Column(name = "member_name")
    private String memberName;
    @Column(name = "member_type")
    private String memberType;

    @Builder
    public Member(
            long memberIdx,
            long teamIdx,
            String teamNm,
            String memberId,
            String memberName,
            String memberType
    ) {
        super();
        this.teamIdx = teamIdx;
        this.teamNm = teamNm;
        this.memberIdx = memberIdx;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberType = memberType;
    }




}
