package com.bahadirmemis.couriertrackingstudycase.cts.service.entityservice;

import com.bahadirmemis.couriertrackingstudycase.cts.dao.CtsCourierDao;
import com.bahadirmemis.couriertrackingstudycase.cts.entity.CtsCourier;
import com.bahadirmemis.couriertrackingstudycase.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Transactional
public class CtsCourierEntityService extends BaseEntityService<CtsCourier, CtsCourierDao> {

    public CtsCourierEntityService(CtsCourierDao dao) {
        super(dao);
    }

}
