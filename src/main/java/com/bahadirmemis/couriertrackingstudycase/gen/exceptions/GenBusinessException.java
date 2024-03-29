package com.bahadirmemis.couriertrackingstudycase.gen.exceptions;

import com.bahadirmemis.couriertrackingstudycase.gen.enums.BaseErrorMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@RequiredArgsConstructor
@Data
public class GenBusinessException extends RuntimeException{

    private final BaseErrorMessage baseErrorMessage;
}
