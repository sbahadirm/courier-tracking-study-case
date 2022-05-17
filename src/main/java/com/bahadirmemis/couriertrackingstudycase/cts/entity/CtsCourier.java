package com.bahadirmemis.couriertrackingstudycase.cts.entity;

import com.bahadirmemis.couriertrackingstudycase.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "CTS_COURIER")
@Getter
@Setter
public class CtsCourier extends BaseEntity {

    @GeneratedValue(generator = "CtsCourier")
    @SequenceGenerator(name = "CtsCourier", sequenceName = "CTS_COURIER_ID_SEQ")
    @Id
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;
}
