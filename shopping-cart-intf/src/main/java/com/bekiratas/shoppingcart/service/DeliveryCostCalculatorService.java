package com.bekiratas.shoppingcart.service;

import com.bekiratas.shoppingcart.model.ShoppingCart;

import java.math.BigDecimal;

public interface DeliveryCostCalculatorService extends Service {
    BigDecimal calculateFor(ShoppingCart cart);
}
