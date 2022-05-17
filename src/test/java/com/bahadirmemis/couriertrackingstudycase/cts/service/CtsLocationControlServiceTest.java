package com.bahadirmemis.couriertrackingstudycase.cts.service;

import com.bahadirmemis.couriertrackingstudycase.str.dto.StrStoreInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class CtsLocationControlServiceTest {

    @InjectMocks
    private CtsLocationControlService ctsLocationControlService;

    @Test
    void shouldIsEnteredIsTrue_WhenTheLocationsAreSame() {

        double latitude = 40.986106;
        double longitude = 29.1161293;

        StrStoreInfoDto strStoreInfoDto = mock(StrStoreInfoDto.class);
        when(strStoreInfoDto.getLatitude()).thenReturn(latitude);
        when(strStoreInfoDto.getLongitude()).thenReturn(longitude);

        boolean result = ctsLocationControlService.isEntered(strStoreInfoDto, longitude, latitude);

        assertTrue(result);
    }

    @Test
    void shouldIsEnteredIsFalse_WhenTheLocationsAreFarFromEachOther() {

        double latitude = 41.986106;
        double longitude = 28.1161293;

        StrStoreInfoDto strStoreInfoDto = mock(StrStoreInfoDto.class);
        when(strStoreInfoDto.getLatitude()).thenReturn(40.986106);
        when(strStoreInfoDto.getLongitude()).thenReturn(29.1161293);

        boolean result = ctsLocationControlService.isEntered(strStoreInfoDto, longitude, latitude);

        assertFalse(result);
    }

    @Test
    void shouldIsReenteredIsTrue_WhenDifferenceIsLessThanOneMinute() {

        LocalDateTime time1 = LocalDateTime.of(2022, 4, 5, 12, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2022, 4, 5, 12, 0, 59);

        boolean result = ctsLocationControlService.isReentered(time1, time2);

        assertTrue(result);

    }

    @Test
    void shouldIsReenteredIsTrue_WhenDifferenceIsOneMinute() {

        LocalDateTime time1 = LocalDateTime.of(2022, 4, 5, 12, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2022, 4, 5, 12, 1, 0);

        boolean result = ctsLocationControlService.isReentered(time1, time2);

        assertTrue(result);
    }

    @Test
    void shouldIsReenteredIsFalse_WhenDifferenceIsMoreThanOneMinute() {

        LocalDateTime time1 = LocalDateTime.of(2022, 4, 5, 12, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2022, 4, 5, 12, 1, 1);

        boolean result = ctsLocationControlService.isReentered(time1, time2);

        assertFalse(result);
    }

    @Test
    void shouldGetClosestStoreToTheLocation() {

        double latitude = 40.986106;
        double longitude = 29.1161293;

        Optional<StrStoreInfoDto> result = ctsLocationControlService.getClosestStoreToTheLocation(longitude, latitude);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldNotGetClosestStoreToTheLocation() {

        double latitude = 99.99;
        double longitude = 99.99;

        Optional<StrStoreInfoDto> result = ctsLocationControlService.getClosestStoreToTheLocation(longitude, latitude);

        assertFalse(result.isPresent());
    }
}