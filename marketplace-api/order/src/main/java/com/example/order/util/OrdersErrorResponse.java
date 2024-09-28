package com.example.order.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersErrorResponse {
    private String message;
    private long timestamp;

    public OrdersErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
