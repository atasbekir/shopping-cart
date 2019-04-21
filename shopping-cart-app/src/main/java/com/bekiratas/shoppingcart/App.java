package com.bekiratas.shoppingcart;

import com.bekiratas.shoppingcart.service.DeliveryCostCalculatorService;
import com.bekiratas.shoppingcart.service.DeliveryCostCalculatorServiceImpl;
import com.bekiratas.shoppingcart.enumeration.DiscountType;
import com.bekiratas.shoppingcart.model.*;

import java.math.BigDecimal;

public class App {

    public static void main(String[] args) {

        ShoppingCart cart = new ShoppingCart();

        Category categoryFood = new Category("Food");
        Category categoryTech = new Category("Technology");
        Category categoryHome = new Category("Home");
        Product apple = new Product("Apple", new BigDecimal(5), categoryFood);
        Product almond = new Product("Almond", new BigDecimal(7), categoryFood);

        Product table = new Product("Sofa", new BigDecimal(1500), categoryHome);

        Product computer = new Product("iPhone", new BigDecimal(8000), categoryTech);

        cart.addItem(computer, 3);
        cart.addItem(apple, 7);
        cart.addItem(almond, 2);
        cart.addItem(table, 5);

        Campaign campaign1 = new Campaign(categoryFood, new BigDecimal(20), 5, DiscountType.RATE);
        Campaign campaign2 = new Campaign(categoryTech, new BigDecimal(500), 1, DiscountType.AMOUNT);
        cart.applyDiscounts(campaign1, campaign2);

        Coupon coupon = new Coupon(new BigDecimal(100), new BigDecimal(10), DiscountType.RATE);
        cart.applyCoupon(coupon);

        cart.applyCoupon(coupon);
        DeliveryCostCalculatorService calculator = new DeliveryCostCalculatorServiceImpl(new BigDecimal(2), new BigDecimal(2), DeliveryCostCalculatorServiceImpl.DEFAULT_FIXED_COST);
        cart.setDeliveryCost(calculator.calculateFor(cart));

        cart.print();
    }

}
