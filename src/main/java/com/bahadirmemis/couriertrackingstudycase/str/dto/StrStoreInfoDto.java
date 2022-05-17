package com.bahadirmemis.couriertrackingstudycase.str.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrStoreInfoDto {

    private String name;

    @JsonAlias("lat")
    private Double latitude;

    @JsonAlias("lng")
    private Double longitude;
}
