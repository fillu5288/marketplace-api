package com.example.order.util;

public class OrdersNotCreated extends RuntimeException{
    public OrdersNotCreated(String msg) {
        super(msg);
    }
}
