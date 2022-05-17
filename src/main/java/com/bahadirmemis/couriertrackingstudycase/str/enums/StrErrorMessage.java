package com.bahadirmemis.couriertrackingstudycase.str.enums;

import com.bahadirmemis.couriertrackingstudycase.gen.enums.BaseErrorMessage;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public enum StrErrorMessage implements BaseErrorMessage {

    COULD_NOT_READ_DATA("Could not read data!");

    private String message;

    StrErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
