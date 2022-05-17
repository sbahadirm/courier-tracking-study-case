package com.bahadirmemis.couriertrackingstudycase.cts.service;

import com.bahadirmemis.couriertrackingstudycase.cts.converter.CtsCourierMapper;
import com.bahadirmemis.couriertrackingstudycase.cts.dto.CtsCourierLocationInfoDto;
import com.bahadirmemis.couriertrackingstudycase.cts.dto.CtsCourierSaveRequestDto;
import com.bahadirmemis.couriertrackingstudycase.cts.entity.CtsCourier;
import com.bahadirmemis.couriertrackingstudycase.cts.service.entityservice.CtsCourierEntityService;
import com.bahadirmemis.couriertrackingstudycase.dlv.entity.DlvDelivery;
import com.bahadirmemis.couriertrackingstudycase.dlv.entity.DlvDeliveryMovement;
import com.bahadirmemis.couriertrackingstudycase.dlv.enums.EnumDlvDeliveryStatus;
import com.bahadirmemis.couriertrackingstudycase.dlv.service.DlvDistanceCalculatorService;
import com.bahadirmemis.couriertrackingstudycase.dlv.service.entityservice.DlvDeliveryEntityService;
import com.bahadirmemis.couriertrackingstudycase.dlv.service.entityservice.DlvDeliveryMovementEntityService;
import com.bahadirmemis.couriertrackingstudycase.str.dto.StrStoreInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CtsCourierTrackingService {

    private final CtsCourierEntityService ctsCourierEntityService;
    private final DlvDeliveryEntityService dlvDeliveryEntityService;
    private final DlvDeliveryMovementEntityService dlvDeliveryMovementEntityService;
    private final CtsLocationControlService ctsLocationControlService;

    public CtsCourier saveCourier(CtsCourierSaveRequestDto ctsCourierSaveRequestDto){

        CtsCourier ctsCourier = CtsCourierMapper.INSTANCE.convertToCtsCourier(ctsCourierSaveRequestDto);

        ctsCourier = ctsCourierEntityService.save(ctsCourier);

        return ctsCourier;
    }

    public void saveCourierPosition(CtsCourierLocationInfoDto ctsCourierLocationInfoDto) {

        Long courierId = ctsCourierLocationInfoDto.getCourierId();

        DlvDelivery activeDelivery = dlvDeliveryEntityService.findActiveDeliveryByCourierId(courierId);

        if (activeDelivery == null){
            DlvDelivery dlvDelivery = createNewActiveDeliveryForCourier(ctsCourierLocationInfoDto);

            createNewMovement(dlvDelivery.getId(), ctsCourierLocationInfoDto);

        } else {

            LocalDateTime lastMovementDate = getLastMovementDate(activeDelivery);

            createNewMovement(activeDelivery.getId(), ctsCourierLocationInfoDto);

            controlAndCompleteDelivery(ctsCourierLocationInfoDto, activeDelivery, lastMovementDate);
        }

    }

    public Double getTotalDistanceOfCourier(Long courierId) {

        List<DlvDelivery> dlvDeliveryList = dlvDeliveryEntityService.findAllCompletedDeliveryListByCtsCourierId(courierId);

        Double totalDistance = dlvDeliveryList.stream()
                .filter(dlvDelivery -> dlvDelivery.getDistance() != null)
                .map(dlvDelivery -> dlvDelivery.getDistance())
                .collect(Collectors.summingDouble(Double::doubleValue));

        return totalDistance;
    }

    private DlvDelivery createNewActiveDeliveryForCourier(CtsCourierLocationInfoDto ctsCourierLocationInfoDto) {

        Long courierId = ctsCourierLocationInfoDto.getCourierId();

        DlvDelivery dlvDelivery = new DlvDelivery();
        dlvDelivery.setCtsCourierId(courierId);
        dlvDelivery.setDlvDeliveryStatus(EnumDlvDeliveryStatus.ACTIVE);

        dlvDelivery = dlvDeliveryEntityService.save(dlvDelivery);

        return dlvDelivery;
    }

    private DlvDeliveryMovement createNewMovement(Long ctsDeliveryId, CtsCourierLocationInfoDto ctsCourierLocationInfoDto) {

        DlvDeliveryMovement dlvDeliveryMovement = new DlvDeliveryMovement();
        dlvDeliveryMovement.setDlvDeliveryId(ctsDeliveryId);
        dlvDeliveryMovement.setMovementDate(ctsCourierLocationInfoDto.getTime());
        dlvDeliveryMovement.setLongitude(ctsCourierLocationInfoDto.getLongitude());
        dlvDeliveryMovement.setLatitude(ctsCourierLocationInfoDto.getLatitude());

        return dlvDeliveryMovementEntityService.save(dlvDeliveryMovement);
    }

    private void controlAndCompleteDelivery(CtsCourierLocationInfoDto ctsCourierLocationInfoDto, DlvDelivery activeDelivery, LocalDateTime lastMovementDate) {

        Double longitude = ctsCourierLocationInfoDto.getLongitude();
        Double latitude = ctsCourierLocationInfoDto.getLatitude();
        LocalDateTime time = ctsCourierLocationInfoDto.getTime();

        boolean isReentered = ctsLocationControlService.isReentered(time, lastMovementDate);

        if (!isReentered){

            Optional<StrStoreInfoDto> storeInfoDtoOptional = ctsLocationControlService.getClosestStoreToTheLocation(longitude, latitude);

            if (storeInfoDtoOptional.isPresent()){
                StrStoreInfoDto strStoreInfoDto = storeInfoDtoOptional.get();

                Double totalDeliveryDistance = calculateTotalDeliveryDistance(activeDelivery.getId());

                activeDelivery.setDlvDeliveryStatus(EnumDlvDeliveryStatus.COMPLETED);
                activeDelivery.setDistance(totalDeliveryDistance);
                activeDelivery.setStoreName(strStoreInfoDto.getName());

                dlvDeliveryEntityService.save(activeDelivery);
            }

        }
    }

    private Double calculateTotalDeliveryDistance(Long ctsDeliveryId) {

        List<DlvDeliveryMovement> dlvDeliveryMovementList = dlvDeliveryMovementEntityService.findAllByDlvDeliveryIdOrderByMovementDate(ctsDeliveryId);

        double totalDistance = 0.0;
        for (int i = 0; i < dlvDeliveryMovementList.size() -1; i++){

            DlvDeliveryMovement dlvDeliveryMovement1 = dlvDeliveryMovementList.get(i);
            DlvDeliveryMovement dlvDeliveryMovement2 = dlvDeliveryMovementList.get(i+1);

            Double latitude1 = dlvDeliveryMovement1.getLatitude();
            Double longitude1 = dlvDeliveryMovement1.getLongitude();

            Double latitude2 = dlvDeliveryMovement2.getLatitude();
            Double longitude2 = dlvDeliveryMovement2.getLongitude();

            double distance = DlvDistanceCalculatorService.distance(latitude1, latitude2, longitude1, longitude2);

            totalDistance = totalDistance + distance;
        }
        
        return totalDistance;
    }

    private LocalDateTime getLastMovementDate(DlvDelivery activeDelivery) {

        DlvDeliveryMovement lastMovement = dlvDeliveryMovementEntityService.findLastDeliveryMovementByDeliveryId(activeDelivery.getId());

        LocalDateTime lastMovementDate = lastMovement.getMovementDate();
        return lastMovementDate;
    }
}
