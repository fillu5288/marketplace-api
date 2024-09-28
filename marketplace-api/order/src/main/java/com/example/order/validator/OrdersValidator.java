package com.example.order.validator;

import com.example.order.pojo.Orders;
import com.example.order.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@SuppressWarnings("NullableProblems")
@Component
public class OrdersValidator implements Validator {
    private final OrdersService ordersService;

    @Autowired
    public OrdersValidator(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Orders.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Orders orders = (Orders) target;

        if (ordersService.findOneId(orders.getId()).isPresent())
            errors.rejectValue("name", "", "Orders is present");
    }
}
