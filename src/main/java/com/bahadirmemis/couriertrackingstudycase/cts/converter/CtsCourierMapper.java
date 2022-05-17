package com.bahadirmemis.couriertrackingstudycase.cts.converter;

import com.bahadirmemis.couriertrackingstudycase.cts.dto.CtsCourierSaveRequestDto;
import com.bahadirmemis.couriertrackingstudycase.cts.entity.CtsCourier;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CtsCourierMapper {

    CtsCourierMapper INSTANCE = Mappers.getMapper(CtsCourierMapper.class);

    CtsCourier convertToCtsCourier(CtsCourierSaveRequestDto ctsCourierSaveRequestDto);
}
