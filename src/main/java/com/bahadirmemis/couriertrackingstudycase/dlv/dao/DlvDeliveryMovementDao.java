package com.bahadirmemis.couriertrackingstudycase.dlv.dao;

import com.bahadirmemis.couriertrackingstudycase.dlv.entity.DlvDeliveryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Repository
public interface DlvDeliveryMovementDao extends JpaRepository<DlvDeliveryMovement, Long> {

    List<DlvDeliveryMovement> findAllByDlvDeliveryIdOrderByMovementDate(Long dlvDeliveryId);

    DlvDeliveryMovement findFirstByDlvDeliveryIdOrderByMovementDateDesc(Long dlvDeliveryId);

}
