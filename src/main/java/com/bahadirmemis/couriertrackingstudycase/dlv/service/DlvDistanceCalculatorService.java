package com.bahadirmemis.couriertrackingstudycase.dlv.service;

import org.springframework.stereotype.Service;

/**
 *
 * referenced by:
 * https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
 *
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
public class DlvDistanceCalculatorService {

    public static final Double MIN_DISTANCE_FROM_STORE = 0.1;
    public static final Long MIN_SECOND_FOR_NEW_ENTRY = 60L;

    private static final int R = 6371; // Radius of the earth

    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        return distanceWithHeight(lat1, lat2, lon1, lon2, 0.00, 0.00);
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distanceWithHeight(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
