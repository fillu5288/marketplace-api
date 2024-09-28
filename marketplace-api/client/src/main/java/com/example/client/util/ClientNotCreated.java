package com.example.client.util;

public class ClientNotCreated extends RuntimeException{
    public ClientNotCreated(String msg) {
        super(msg);
    }
}
