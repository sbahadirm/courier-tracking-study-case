package com.bahadirmemis.couriertrackingstudycase.dlv.dao;

import com.bahadirmemis.couriertrackingstudycase.dlv.entity.DlvDelivery;
import com.bahadirmemis.couriertrackingstudycase.dlv.enums.EnumDlvDeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Repository
public interface DlvDeliveryDao extends JpaRepository<DlvDelivery, Long> {

    DlvDelivery findByCtsCourierIdAndDlvDeliveryStatus(Long ctsCourierId, EnumDlvDeliveryStatus dlvDeliveryStatus);

    List<DlvDelivery> findAllByCtsCourierIdAndDlvDeliveryStatus(Long ctsCourierId, EnumDlvDeliveryStatus dlvDeliveryStatus);

    List<DlvDelivery> findAllByCtsCourierId(Long ctsCourierId);
}
