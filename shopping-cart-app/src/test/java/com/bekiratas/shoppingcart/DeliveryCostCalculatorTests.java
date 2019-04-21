package com.bekiratas.shoppingcart;

import com.bekiratas.shoppingcart.service.DeliveryCostCalculatorService;
import com.bekiratas.shoppingcart.service.DeliveryCostCalculatorServiceImpl;
import com.bekiratas.shoppingcart.model.Category;
import com.bekiratas.shoppingcart.model.Product;
import com.bekiratas.shoppingcart.model.ShoppingCart;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class DeliveryCostCalculatorTests {

    private DeliveryCostCalculatorService deliveryCostCalculator;
    private ShoppingCart shoppingCart;

    @Before
    public void setup() {
        shoppingCart = new ShoppingCart();
        deliveryCostCalculator = new DeliveryCostCalculatorServiceImpl(new BigDecimal(1), new BigDecimal(1), DeliveryCostCalculatorServiceImpl.DEFAULT_FIXED_COST);
        Category categoryFood = new Category("Food");
        Category categoryTech = new Category("Technology");
        Product apple = new Product("Apple", new BigDecimal(10), categoryFood);
        Product almond = new Product("Almond", new BigDecimal(15), categoryFood);
        Product computer = new Product("iPhone", new BigDecimal(8000), categoryTech);
        shoppingCart.addItem(apple, 6);
        shoppingCart.addItem(almond, 5);
        shoppingCart.addItem(computer, 3);
    }

    @Test
    public void testCalculateFor() {
        assertTrue(new BigDecimal(7.99).compareTo(deliveryCostCalculator.calculateFor(shoppingCart)) == 0);
    }
}
