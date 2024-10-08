package com.example.client.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientErrorResponse {
    private String message;
    private long timestamp;

    public ClientErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
