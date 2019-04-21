package com.bekiratas.shoppingcart.service;

import com.bekiratas.shoppingcart.model.ShoppingCart;
import com.bekiratas.shoppingcart.service.DeliveryCostCalculatorService;

import java.math.BigDecimal;

public class DeliveryCostCalculatorServiceImpl extends AbstractService implements DeliveryCostCalculatorService {
    public static final BigDecimal DEFAULT_FIXED_COST = new BigDecimal(2.99);

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;
    private BigDecimal fixedCost;

    public DeliveryCostCalculatorServiceImpl() {
    }

    public DeliveryCostCalculatorServiceImpl(BigDecimal costPerDelivery, BigDecimal costPerProduct, BigDecimal fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    @Override
    public BigDecimal calculateFor(ShoppingCart cart) {
        return costPerDelivery.multiply(new BigDecimal(cart.getNumberOfDeliveries())).add(costPerProduct.multiply(new BigDecimal(cart.getNumberOfProducts()))).add(fixedCost);
    }

    public BigDecimal getCostPerDelivery() {
        return costPerDelivery;
    }

    public void setCostPerDelivery(BigDecimal costPerDelivery) {
        this.costPerDelivery = costPerDelivery;
    }

    public BigDecimal getCostPerProduct() {
        return costPerProduct;
    }

    public void setCostPerProduct(BigDecimal costPerProduct) {
        this.costPerProduct = costPerProduct;
    }

    public BigDecimal getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(BigDecimal fixedCost) {
        this.fixedCost = fixedCost;
    }


}
