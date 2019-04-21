package com.bekiratas.shoppingcart.model;

import com.bekiratas.shoppingcart.enumeration.DiscountType;

import java.io.Serializable;
import java.math.BigDecimal;

public class Coupon implements Serializable {
    private BigDecimal minimumAmount;
    private BigDecimal discountAmount;
    private DiscountType discountType;

    public Coupon() {
    }

    public Coupon(BigDecimal minimumAmount, BigDecimal discountAmount, DiscountType discountType) {
        this.minimumAmount = minimumAmount;
        this.discountAmount = discountAmount;
        this.discountType = discountType;
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(BigDecimal minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "minimumAmount=" + minimumAmount +
                ", discountAmount=" + discountAmount +
                ", discountType=" + discountType +
                '}';
    }
}
