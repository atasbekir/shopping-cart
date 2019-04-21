package com.bekiratas.shoppingcart.model;

import com.bekiratas.shoppingcart.enumeration.DiscountType;

import java.io.Serializable;
import java.math.BigDecimal;

public class Campaign implements Serializable {

    private Category category;
    private BigDecimal discountAmount;
    private Integer minimumQuantity;
    private DiscountType discountType;

    public Campaign() {
    }

    public Campaign(Category category, BigDecimal discountAmount, Integer minimumQuantity, DiscountType discountType) {
        this.category = category;
        this.discountAmount = discountAmount;
        this.minimumQuantity = minimumQuantity;
        this.discountType = discountType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "category=" + category +
                ", discountAmount=" + discountAmount +
                ", minimumQuantity=" + minimumQuantity +
                ", discountType=" + discountType +
                '}';
    }
}
