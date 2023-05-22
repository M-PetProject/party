package com.study.party.jpa.entity.diningMain;

import com.study.party.jpa.entity.comm.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "dining_joiner")
public class DiningJoiner extends BaseEntity {

    @Id
    @Column(name="dining_joiner_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @ManyToOne
    @JoinColumn(name = "member_idx", nullable = false, referencedColumnName = "member_idx")
    private Member member;

    @Column(name = "join_yn", length = 1)
    private String joinYn;

    @Column(name = "place_yn", length = 1)
    private String placeYn;

    @Column(name = "time_yn", length = 1)
    private String timeYn;

    private DiningJoiner(
            Member member,
            String joinYn,
            String placeYn,
            String timeYn
    ) {
        super();
        this.member = member;
        this.joinYn = joinYn;
        this.placeYn = placeYn;
        this.timeYn = timeYn;
    }
    @Builder
    public DiningJoiner(
            long idx,
            Member member,
            String joinYn,
            String placeYn,
            String timeYn
    ) {
        super();
        this.idx = idx;
        this.member = member;
        this.joinYn = joinYn;
        this.placeYn = placeYn;
        this.timeYn = timeYn;
    }

    public static DiningJoiner of(
            Member member,
            String joinYn,
            String placeYn,
            String timeYn
    ) {
        return new DiningJoiner(member, joinYn, placeYn, timeYn);
    }
}
