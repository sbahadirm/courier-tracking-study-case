package com.bahadirmemis.couriertrackingstudycase.dlv.entity;

import com.bahadirmemis.couriertrackingstudycase.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "DLV_DELIVERY_MOVEMENT")
@Getter
@Setter
public class DlvDeliveryMovement extends BaseEntity {

    @GeneratedValue(generator = "DlvDeliveryMovement")
    @SequenceGenerator(name = "DlvDeliveryMovement", sequenceName = "DLV_DELIVERY_ID_SEQ")
    @Id
    private Long id;

    @Column(name = "ID_DLV_DELIVERY")
    private Long dlvDeliveryId;

    private Double latitude;

    private Double longitude;

    @Column(name = "MOVEMENT_DATE", columnDefinition = "TIMESTAMP")
    private LocalDateTime movementDate;
}
