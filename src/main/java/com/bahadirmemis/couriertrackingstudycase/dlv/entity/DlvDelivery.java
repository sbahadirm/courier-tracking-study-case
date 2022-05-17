package com.bahadirmemis.couriertrackingstudycase.dlv.entity;

import com.bahadirmemis.couriertrackingstudycase.dlv.enums.EnumDlvDeliveryStatus;
import com.bahadirmemis.couriertrackingstudycase.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "DLV_DELIVERY")
@Getter
@Setter
public class DlvDelivery extends BaseEntity {

    @GeneratedValue(generator = "DlvDelivery")
    @SequenceGenerator(name = "DlvDelivery", sequenceName = "DLV_DELIVERY_ID_SEQ")
    @Id
    private Long id;

    @Column(name = "ID_CTS_COURIER")
    private Long ctsCourierId;

    @Column(name = "STORE_NAME", length = 100)
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "DELIVERY_STATUS", length = 30)
    private EnumDlvDeliveryStatus dlvDeliveryStatus;

    @Column(name = "DISTANCE", precision = 19, scale = 2)
    private Double distance;

}
