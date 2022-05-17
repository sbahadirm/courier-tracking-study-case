package com.bahadirmemis.couriertrackingstudycase.str.helper;

import com.bahadirmemis.couriertrackingstudycase.gen.exceptions.GenBusinessException;
import com.bahadirmemis.couriertrackingstudycase.str.dto.StrStoreInfoDto;
import com.bahadirmemis.couriertrackingstudycase.str.enums.StrErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class StrStoreHelper {

    protected static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static List<StrStoreInfoDto> getStrStoreInfoDtoList(String path){

        List<StrStoreInfoDto> strStoreInfoDtoList;
        try {
            String storeInfoAsJSON = getStoreInfoAsJSON(path);
            strStoreInfoDtoList = convertToStrStoreInfoDto(storeInfoAsJSON);
        } catch (IOException e){
            throw new GenBusinessException(StrErrorMessage.COULD_NOT_READ_DATA);
        }

        return strStoreInfoDtoList;
    }

    public static List<StrStoreInfoDto> convertToStrStoreInfoDto(String storeInfoJson) throws JsonProcessingException {

        List<StrStoreInfoDto> storeList = objectMapper.readValue(storeInfoJson, new TypeReference<>() {});

        return storeList;
    }

    private static String getStoreInfoAsJSON(String path) throws IOException {

        InputStream is = StrStoreHelper.class.getResourceAsStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        StringBuilder storeInfoSB = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            storeInfoSB.append(line);
        }

        return storeInfoSB.toString();
    }
}
