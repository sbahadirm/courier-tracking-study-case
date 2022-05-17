package com.bahadirmemis.couriertrackingstudycase.cts.service;

import com.bahadirmemis.couriertrackingstudycase.cts.dto.CtsCourierLocationInfoDto;
import com.bahadirmemis.couriertrackingstudycase.cts.entity.CtsCourier;
import com.bahadirmemis.couriertrackingstudycase.cts.service.entityservice.CtsCourierEntityService;
import com.bahadirmemis.couriertrackingstudycase.dlv.entity.DlvDelivery;
import com.bahadirmemis.couriertrackingstudycase.dlv.entity.DlvDeliveryMovement;
import com.bahadirmemis.couriertrackingstudycase.dlv.service.entityservice.DlvDeliveryEntityService;
import com.bahadirmemis.couriertrackingstudycase.dlv.service.entityservice.DlvDeliveryMovementEntityService;
import com.bahadirmemis.couriertrackingstudycase.str.dto.StrStoreInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class CtsCourierTrackingServiceTest {

    @Mock
    private CtsCourierEntityService ctsCourierEntityService;

    @Mock
    private DlvDeliveryEntityService dlvDeliveryEntityService;

    @Mock
    private DlvDeliveryMovementEntityService dlvDeliveryMovementEntityService;

    @Mock
    private CtsLocationControlService ctsLocationControlService;

    @InjectMocks
    private CtsCourierTrackingService ctsCourierTrackingService;

    @Test
    void shouldSaveCourier() {

        CtsCourier ctsCourier = Mockito.mock(CtsCourier.class);
        when(ctsCourier.getId()).thenReturn(1L);
        when(ctsCourier.getName()).thenReturn("Yusuf");

        when(ctsCourierEntityService.save(any())).thenReturn(ctsCourier);

        CtsCourier result = ctsCourierTrackingService.saveCourier(any());

        assertEquals(1L, result.getId());
        assertEquals("Yusuf", result.getName());
    }

    @Test
    void shouldSaveCourierPosition_whenThereIsNoActiveDelivery() {

        CtsCourierLocationInfoDto ctsCourierLocationInfoDto = mock(CtsCourierLocationInfoDto.class);
        when(ctsCourierLocationInfoDto.getCourierId()).thenReturn(1L);

        DlvDelivery dlvDelivery = mock(DlvDelivery.class);
        when(dlvDelivery.getId()).thenReturn(1L);

        DlvDeliveryMovement dlvDeliveryMovement = mock(DlvDeliveryMovement.class);

        when(dlvDeliveryEntityService.findActiveDeliveryByCourierId(any())).thenReturn(null);
        when(dlvDeliveryEntityService.save(any())).thenReturn(dlvDelivery);
        when(dlvDeliveryMovementEntityService.save(any())).thenReturn(dlvDeliveryMovement);

        ctsCourierTrackingService.saveCourierPosition(ctsCourierLocationInfoDto);
    }

    @Test
    void shouldSaveCourierPosition_whenReenteredIsTrue() {

        LocalDateTime lastMovementDate = LocalDateTime.of(2022, 4, 5, 12, 0, 0);

        CtsCourierLocationInfoDto ctsCourierLocationInfoDto = mock(CtsCourierLocationInfoDto.class);
        when(ctsCourierLocationInfoDto.getCourierId()).thenReturn(1L);
        when(ctsCourierLocationInfoDto.getTime()).thenReturn(lastMovementDate);

        DlvDelivery dlvDelivery = mock(DlvDelivery.class);
        when(dlvDelivery.getId()).thenReturn(1L);

        DlvDeliveryMovement dlvDeliveryMovement = mock(DlvDeliveryMovement.class);
        when(dlvDeliveryMovement.getMovementDate()).thenReturn(lastMovementDate);

        when(dlvDeliveryEntityService.findActiveDeliveryByCourierId(any())).thenReturn(dlvDelivery);
        when(dlvDeliveryMovementEntityService.findLastDeliveryMovementByDeliveryId(any())).thenReturn(dlvDeliveryMovement);
        when(ctsLocationControlService.isReentered(any(), any())).thenReturn(true);

        ctsCourierTrackingService.saveCourierPosition(ctsCourierLocationInfoDto);
    }

    @Test
    void shouldSaveCourierPosition_whenReenteredFalseAndNotInRangeOfStores() {

        LocalDateTime lastMovementDate = LocalDateTime.of(2022, 4, 5, 12, 0, 0);
        LocalDateTime time = LocalDateTime.of(2022, 4, 5, 12, 2, 0);

        CtsCourierLocationInfoDto ctsCourierLocationInfoDto = mock(CtsCourierLocationInfoDto.class);
        when(ctsCourierLocationInfoDto.getCourierId()).thenReturn(1L);
        when(ctsCourierLocationInfoDto.getTime()).thenReturn(time);

        DlvDelivery dlvDelivery = mock(DlvDelivery.class);
        when(dlvDelivery.getId()).thenReturn(1L);

        DlvDeliveryMovement dlvDeliveryMovement = mock(DlvDeliveryMovement.class);
        when(dlvDeliveryMovement.getMovementDate()).thenReturn(lastMovementDate);

        when(dlvDeliveryEntityService.findActiveDeliveryByCourierId(any())).thenReturn(dlvDelivery);
        when(dlvDeliveryMovementEntityService.findLastDeliveryMovementByDeliveryId(any())).thenReturn(dlvDeliveryMovement);
        when(ctsLocationControlService.getClosestStoreToTheLocation(anyDouble(), anyDouble())).thenReturn(Optional.empty());

        ctsCourierTrackingService.saveCourierPosition(ctsCourierLocationInfoDto);
    }

    @Test
    void shouldSaveCourierPosition_whenReenteredFalseAndInRangeOfStores() {

        LocalDateTime lastMovementDate = LocalDateTime.of(2022, 4, 5, 12, 0, 0);
        LocalDateTime time = LocalDateTime.of(2022, 4, 5, 12, 2, 0);

        CtsCourierLocationInfoDto ctsCourierLocationInfoDto = mock(CtsCourierLocationInfoDto.class);
        when(ctsCourierLocationInfoDto.getCourierId()).thenReturn(1L);
        when(ctsCourierLocationInfoDto.getTime()).thenReturn(time);
        when(ctsCourierLocationInfoDto.getLatitude()).thenReturn(40.986106);
        when(ctsCourierLocationInfoDto.getLongitude()).thenReturn(29.1161293);

        DlvDelivery dlvDelivery = mock(DlvDelivery.class);
        when(dlvDelivery.getId()).thenReturn(1L);

        DlvDeliveryMovement dlvDeliveryMovement = mock(DlvDeliveryMovement.class);
        when(dlvDeliveryMovement.getMovementDate()).thenReturn(lastMovementDate);

        DlvDeliveryMovement movement1 = mock(DlvDeliveryMovement.class);
        when(movement1.getLatitude()).thenReturn(40.986106);
        when(movement1.getLongitude()).thenReturn(29.1161293);

        DlvDeliveryMovement movement2 = mock(DlvDeliveryMovement.class);
        when(movement2.getLatitude()).thenReturn(41.986106);
        when(movement2.getLongitude()).thenReturn(28.1161293);

        DlvDeliveryMovement movement3 = mock(DlvDeliveryMovement.class);
        when(movement3.getLatitude()).thenReturn(42.986106);
        when(movement3.getLongitude()).thenReturn(27.1161293);

        List<DlvDeliveryMovement> dlvDeliveryMovementList = new ArrayList<>();
        dlvDeliveryMovementList.add(movement1);
        dlvDeliveryMovementList.add(movement2);
        dlvDeliveryMovementList.add(movement3);

        StrStoreInfoDto strStoreInfoDto = mock(StrStoreInfoDto.class);
        Optional<StrStoreInfoDto> storeInfoDtoOptional = Optional.of(strStoreInfoDto);

        when(dlvDeliveryEntityService.findActiveDeliveryByCourierId(any())).thenReturn(dlvDelivery);
        when(dlvDeliveryMovementEntityService.findLastDeliveryMovementByDeliveryId(any())).thenReturn(dlvDeliveryMovement);
        when(dlvDeliveryMovementEntityService.findAllByDlvDeliveryIdOrderByMovementDate(any())).thenReturn(dlvDeliveryMovementList);
        when(dlvDeliveryEntityService.save(any())).thenReturn(null);
        when(ctsLocationControlService.getClosestStoreToTheLocation(anyDouble(), anyDouble())).thenReturn(storeInfoDtoOptional);

        ctsCourierTrackingService.saveCourierPosition(ctsCourierLocationInfoDto);
    }

    @Test
    void shouldGetTotalDistanceOfCourier() {

        DlvDelivery dlvDelivery1 = mock(DlvDelivery.class);
        when(dlvDelivery1.getDistance()).thenReturn(2.0);

        DlvDelivery dlvDelivery2 = mock(DlvDelivery.class);
        when(dlvDelivery2.getDistance()).thenReturn(3.0);

        List<DlvDelivery> dlvDeliveryList = new ArrayList<>();
        dlvDeliveryList.add(dlvDelivery1);
        dlvDeliveryList.add(dlvDelivery2);

        when(dlvDeliveryEntityService.findAllCompletedDeliveryListByCtsCourierId(any())).thenReturn(dlvDeliveryList);

        Double result = ctsCourierTrackingService.getTotalDistanceOfCourier(any());

        assertEquals(5.0, result);
    }

    @Test
    void shouldGetTotalDistanceOfCourier_WhenThereIsNoCompletedDelivery() {

        List<DlvDelivery> dlvDeliveryList = new ArrayList<>();

        when(dlvDeliveryEntityService.findAllCompletedDeliveryListByCtsCourierId(any())).thenReturn(dlvDeliveryList);

        Double result = ctsCourierTrackingService.getTotalDistanceOfCourier(any());

        assertEquals(0.0, result);
    }

    @Test
    void shouldGetTotalDistanceOfCourier_EvenDistanceIsNull() {

        DlvDelivery dlvDelivery1 = mock(DlvDelivery.class);
        when(dlvDelivery1.getDistance()).thenReturn(2.0);

        DlvDelivery dlvDelivery2 = mock(DlvDelivery.class);
        when(dlvDelivery2.getDistance()).thenReturn(null);

        List<DlvDelivery> dlvDeliveryList = new ArrayList<>();
        dlvDeliveryList.add(dlvDelivery1);
        dlvDeliveryList.add(dlvDelivery2);

        when(dlvDeliveryEntityService.findAllCompletedDeliveryListByCtsCourierId(any())).thenReturn(dlvDeliveryList);

        Double result = ctsCourierTrackingService.getTotalDistanceOfCourier(any());

        assertEquals(2.0, result);
    }


}