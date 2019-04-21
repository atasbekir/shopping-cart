package com.bekiratas.shoppingcart;

import com.bekiratas.shoppingcart.model.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;

public class ShoppingCartTests {

    private ShoppingCart shoppingCart;
    private Category categoryFood;
    private Category categoryTech;
    private Product productApple;
    private Product productBanana;
    private Product productComputer;

    @Before
    public void setUp() {
        shoppingCart = new ShoppingCart();
        categoryFood = new Category("Food");
        categoryFood = new Category("Food");
        categoryTech = new Category("Technology");
        productApple = new Product("apple", new BigDecimal(7), categoryFood);
        productBanana = new Product("strawberry", new BigDecimal(10), categoryFood);
        productComputer = new Product("Samsung Tablet", new BigDecimal(2500), categoryTech);
        shoppingCart.addItem(productApple, 6);
        shoppingCart.addItem(productBanana, 2);
        shoppingCart.addItem(productComputer, 1);

    }

    @Test
    public void testAddItem() {
        Product productIphone = new Product("iPhone", new BigDecimal(8000), categoryTech);
        shoppingCart.addItem(productIphone, 1);

        Map<Product, Integer> itemsOfCart = shoppingCart.getItems();
        assertTrue(itemsOfCart.containsKey(productBanana));
        assertTrue(itemsOfCart.containsKey(productIphone));

        assertEquals(new Integer(6), itemsOfCart.get(productApple));
        assertEquals(new Integer(1), itemsOfCart.get(productIphone));
    }

    @Test
    public void testAddAmountAfterAddItem() {
        testAddItem();
        Product productIphone = new Product("iPhone", new BigDecimal(5000), categoryTech);
        shoppingCart.addItem(productIphone, 1);
        assertEquals(4, shoppingCart.getItems().size());
    }

    @Test
    public void removeItem() {
        testAddItem();
        shoppingCart.removeItem(new Product("iPhone"));
    }

}
