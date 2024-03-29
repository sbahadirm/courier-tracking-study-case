package com.bahadirmemis.couriertrackingstudycase.gen.exceptions;

import com.bahadirmemis.couriertrackingstudycase.gen.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends GenBusinessException{

    public ItemNotFoundException(BaseErrorMessage message) {
        super(message);
    }
}
