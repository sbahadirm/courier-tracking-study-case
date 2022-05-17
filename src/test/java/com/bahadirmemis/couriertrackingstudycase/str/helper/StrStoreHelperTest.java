package com.bahadirmemis.couriertrackingstudycase.str.helper;

import com.bahadirmemis.couriertrackingstudycase.gen.exceptions.GenBusinessException;
import com.bahadirmemis.couriertrackingstudycase.str.dto.StrStoreInfoDto;
import com.bahadirmemis.couriertrackingstudycase.str.enums.StrErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class StrStoreHelperTest {

    @Test
    void shouldConvertToStrStoreInfoDto() throws JsonProcessingException {

        String json = getStoreInfoAsJSON();

        List<StrStoreInfoDto> strStoreInfoDtoList = StrStoreHelper.convertToStrStoreInfoDto(json);

        assertEquals(5L, strStoreInfoDtoList.size());

    }

    @Test
    void shouldGetStrStoreInfoDtoList() {

        List<StrStoreInfoDto> strStoreInfoDtoList = StrStoreHelper.getStrStoreInfoDtoList("/static/stores.json");

        assertEquals(5, strStoreInfoDtoList.size());
    }

    @Test
    void shouldNotGetStrStoreInfoDtoList() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> StrStoreHelper.getStrStoreInfoDtoList(""));

        assertEquals(StrErrorMessage.COULD_NOT_READ_DATA, genBusinessException.getBaseErrorMessage());
    }

    @Test
    void convertToStrStoreInfoDto() throws JsonProcessingException {

        String storeInfoAsJSON = getStoreInfoAsJSON();


        List<StrStoreInfoDto> strStoreInfoDtoList = StrStoreHelper.convertToStrStoreInfoDto(storeInfoAsJSON);

        assertEquals(5, strStoreInfoDtoList.size());
    }

    private String getStoreInfoAsJSON() {
        String json = "[\n" +
                "  {\n" +
                "    \"name\": \"Ataşehir MMM Migros\",\n" +
                "    \"lat\": 40.9923307,\n" +
                "    \"lng\": 29.1244229\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Novada MMM Migros\",\n" +
                "    \"lat\": 40.986106,\n" +
                "    \"lng\": 29.1161293\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Beylikdüzü 5M Migros\",\n" +
                "    \"lat\": 41.0066851,\n" +
                "    \"lng\": 28.6552262\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Ortaköy MMM Migros\",\n" +
                "    \"lat\": 41.055783,\n" +
                "    \"lng\": 29.0210292\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Caddebostan MMM Migros\",\n" +
                "    \"lat\": 40.9632463,\n" +
                "    \"lng\": 29.0630908\n" +
                "  }\n" +
                "]";
        return json;
    }
}