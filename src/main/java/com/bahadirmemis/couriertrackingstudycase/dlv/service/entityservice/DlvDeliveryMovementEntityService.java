package com.bahadirmemis.couriertrackingstudycase.dlv.service.entityservice;

import com.bahadirmemis.couriertrackingstudycase.dlv.dao.DlvDeliveryMovementDao;
import com.bahadirmemis.couriertrackingstudycase.dlv.entity.DlvDeliveryMovement;
import com.bahadirmemis.couriertrackingstudycase.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Transactional
public class DlvDeliveryMovementEntityService extends BaseEntityService<DlvDeliveryMovement, DlvDeliveryMovementDao> {

    public DlvDeliveryMovementEntityService(DlvDeliveryMovementDao dao) {
        super(dao);
    }

    public DlvDeliveryMovement findLastDeliveryMovementByDeliveryId(Long dlvDeliveryId){
        return getDao().findFirstByDlvDeliveryIdOrderByMovementDateDesc(dlvDeliveryId);
    }

    public List<DlvDeliveryMovement> findAllByDlvDeliveryIdOrderByMovementDate(Long dlvDeliveryId){
        return getDao().findAllByDlvDeliveryIdOrderByMovementDate(dlvDeliveryId);
    }
}
