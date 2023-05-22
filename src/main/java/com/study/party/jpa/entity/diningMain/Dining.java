package com.study.party.jpa.entity.diningMain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "dining")
public class Dining {

    @Id
    @Column(name = "dining_idx")
    private long idx;

    @Column(name = "team_idx")
    private long teamIdx;
    // dining main 도메인에서 team idx 만 필요함. team 도메인에서 entity를 만들어 jpa로 진행하지 않기 때문에, 연관 관계없이 진행.

    private String name;

    private String details;

    @Column(name = "number_participants")
    private int numberOfParticipants;
}
