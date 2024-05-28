package com.hardeymorlah.Logistics.Model.DTOs;

import lombok.Getter;

@Getter
public enum Status {
    SHIPPED("Sipped"),
    ORDER_PLACED("Order placed"),
    AVAILABLE_IN_STOCK("Available"),
    OUT_OF_STOCK("Out of Stock"),
    CONFIRMED("Confirmed"),
    PICKED_UP("Picked u["),
    RETURNED_TO_SENDER("Returned to Sender"),
    READY_FOR_PICKUP("Ready for Pickup");

    private final String description;

    Status(String description){
        this.description = description;
    }


}
