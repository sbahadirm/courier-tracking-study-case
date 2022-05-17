package com.bahadirmemis.couriertrackingstudycase.dlv.service.entityservice;

import com.bahadirmemis.couriertrackingstudycase.dlv.dao.DlvDeliveryDao;
import com.bahadirmemis.couriertrackingstudycase.dlv.entity.DlvDelivery;
import com.bahadirmemis.couriertrackingstudycase.dlv.enums.EnumDlvDeliveryStatus;
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
public class DlvDeliveryEntityService extends BaseEntityService<DlvDelivery, DlvDeliveryDao> {

    public DlvDeliveryEntityService(DlvDeliveryDao dao) {
        super(dao);
    }

    public DlvDelivery findActiveDeliveryByCourierId(Long dlvCourierId){
        return getDao().findByCtsCourierIdAndDlvDeliveryStatus(dlvCourierId, EnumDlvDeliveryStatus.ACTIVE);
    }

    public List<DlvDelivery> findAllCompletedDeliveryListByCtsCourierId(Long ctsCourierId){
        return getDao().findAllByCtsCourierIdAndDlvDeliveryStatus(ctsCourierId, EnumDlvDeliveryStatus.COMPLETED);
    }
}
