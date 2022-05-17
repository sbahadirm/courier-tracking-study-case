package com.bahadirmemis.couriertrackingstudycase.cts.dao;

import com.bahadirmemis.couriertrackingstudycase.cts.entity.CtsCourier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Repository
public interface CtsCourierDao extends JpaRepository<CtsCourier, Long> {

}
