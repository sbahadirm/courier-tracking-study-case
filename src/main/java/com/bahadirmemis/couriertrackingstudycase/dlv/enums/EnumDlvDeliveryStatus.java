package com.bahadirmemis.couriertrackingstudycase.dlv.enums;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public enum EnumDlvDeliveryStatus {

    ACTIVE("Active"),
    COMPLETED("Completed");

    private String status;

    EnumDlvDeliveryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
