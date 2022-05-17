package com.bahadirmemis.couriertrackingstudycase.dlv.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
class DlvDistanceCalculatorServiceTest {

    @Test
    void shouldDistance_whenLocationsAreSame() {
        double lat1 = 40.986106;
        double lat2 = 40.986106;
        double lon1 = 29.1161293;
        double lon2 = 29.1161293;

        double distance = DlvDistanceCalculatorService.distance(lat1, lat2, lon1, lon2);

        assertEquals(0.0, distance);
    }

    @Test
    void shouldDistance_WhenDistanceAreDifferent() {
        double lat1 = 40.986106;
        double lat2 = 40.9923307;
        double lon1 = 29.1161293;
        double lon2 = 29.1244229;

        double distance = DlvDistanceCalculatorService.distance(lat1, lat2, lon1, lon2);

        assertEquals(981.656843521228, distance);
    }

    @Test
    void shouldDistanceWithHeight_whenLocationsAreSame() {

        double lat1 = 40.986106;
        double lat2 = 40.986106;
        double lon1 = 29.1161293;
        double lon2 = 29.1161293;
        double el1 = 0.0;
        double el2 = 0.0;

        double distance = DlvDistanceCalculatorService.distanceWithHeight(lat1, lat2, lon1, lon2, el1, el2);

        assertEquals(0.0, distance);
    }

    @Test
    void shouldDistanceWithHeight_whenElAreDifferent() {

        double lat1 = 40.986106;
        double lat2 = 40.986106;
        double lon1 = 29.1161293;
        double lon2 = 29.1161293;
        double el1 = 0.0;
        double el2 = 2.0;

        double distance = DlvDistanceCalculatorService.distanceWithHeight(lat1, lat2, lon1, lon2, el1, el2);

        assertEquals(2.0, distance);
    }
}