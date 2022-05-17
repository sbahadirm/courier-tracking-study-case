package com.bahadirmemis.couriertrackingstudycase.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CtsCourierLocationInfoDto {

    @NotNull
    @PastOrPresent
    private LocalDateTime time;

    @NotNull
    @Positive
    private Long courierId;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
