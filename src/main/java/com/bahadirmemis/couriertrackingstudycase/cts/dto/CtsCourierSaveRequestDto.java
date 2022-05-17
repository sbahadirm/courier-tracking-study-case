package com.bahadirmemis.couriertrackingstudycase.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CtsCourierSaveRequestDto {

    @NotNull
    private String name;
}
