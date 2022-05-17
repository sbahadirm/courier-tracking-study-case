package com.bahadirmemis.couriertrackingstudycase.cts.controller;

import com.bahadirmemis.couriertrackingstudycase.cts.dto.CtsCourierLocationInfoDto;
import com.bahadirmemis.couriertrackingstudycase.cts.dto.CtsCourierSaveRequestDto;
import com.bahadirmemis.couriertrackingstudycase.cts.entity.CtsCourier;
import com.bahadirmemis.couriertrackingstudycase.cts.service.CtsCourierTrackingService;
import com.bahadirmemis.couriertrackingstudycase.gen.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/couriers")
public class CtsCourierTrackingController {

    private final CtsCourierTrackingService ctsCourierTrackingService;

    @PostMapping
    public ResponseEntity<RestResponse> saveCourier(
            @Valid @RequestBody CtsCourierSaveRequestDto ctsCourierSaveRequestDto){

        CtsCourier ctsCourier = ctsCourierTrackingService.saveCourier(ctsCourierSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(ctsCourier.getId()));
    }

    @PostMapping("/positions")
    public ResponseEntity<RestResponse> saveCourierPosition(
            @Valid @RequestBody CtsCourierLocationInfoDto ctsCourierLocationInfoDto){

        ctsCourierTrackingService.saveCourierPosition(ctsCourierLocationInfoDto);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @GetMapping("/total-distance/{courierId}")
    public ResponseEntity<RestResponse> getTotalDistanceOfCourier(@PathVariable Long courierId){

        Double totalDistance = ctsCourierTrackingService.getTotalDistanceOfCourier(courierId);

        return ResponseEntity.ok(RestResponse.of(totalDistance));

    }
}
