package com.bahadirmemis.couriertrackingstudycase.cts.service;

import com.bahadirmemis.couriertrackingstudycase.dlv.service.DlvDistanceCalculatorService;
import com.bahadirmemis.couriertrackingstudycase.str.dto.StrStoreInfoDto;
import com.bahadirmemis.couriertrackingstudycase.str.helper.StrStoreHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Transactional
public class CtsLocationControlService {

    private static final  String STORES_JSON_PATH = "/static/stores.json";;

    public boolean isReentered(LocalDateTime time, LocalDateTime lastMovementDate) {

        long secondsDiff = SECONDS.between(lastMovementDate, time);

        secondsDiff = Math.abs(secondsDiff);

        boolean isReentered = secondsDiff <= DlvDistanceCalculatorService.MIN_SECOND_FOR_NEW_ENTRY;

        return isReentered;
    }

    public boolean isEntered(StrStoreInfoDto strStoreInfoDto, Double longitude, Double latitude){

        Double eachLatitude = strStoreInfoDto.getLatitude();
        Double eachLongitude = strStoreInfoDto.getLongitude();

        double distance = DlvDistanceCalculatorService.distance(latitude, eachLatitude, longitude, eachLongitude);

        boolean isEnteredStore = distance <= DlvDistanceCalculatorService.MIN_DISTANCE_FROM_STORE;

        return isEnteredStore;
    }

    public Optional<StrStoreInfoDto> getClosestStoreToTheLocation(Double longitude, Double latitude) {
        List<StrStoreInfoDto> strStoreInfoDtoList = StrStoreHelper.getStrStoreInfoDtoList(STORES_JSON_PATH);

        Optional<StrStoreInfoDto> storeInfoDtoOptional = strStoreInfoDtoList.stream()
                .filter(strStoreInfoDto -> isEntered(strStoreInfoDto, longitude, latitude))
                .findFirst();
        return storeInfoDtoOptional;
    }
}
