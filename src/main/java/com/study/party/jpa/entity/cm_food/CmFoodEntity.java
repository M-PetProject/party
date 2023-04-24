package com.study.party.jpa.entity.cm_food;


import com.study.party.jpa.entity.comm.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cm_food")
@Entity
public class CmFoodEntity extends DefaultEntity {

    private static final long serialVersionUID = 145828767031252846L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long foodIdx;
    private String foodNm;
}
