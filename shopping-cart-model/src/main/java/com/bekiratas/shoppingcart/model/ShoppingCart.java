package com.bekiratas.shoppingcart.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCart implements Serializable {

    private Map<Product, Integer> items;
    private Map<Category, BigDecimal> validCategoryDiscounts;
    private Coupon validCoupon;
    private BigDecimal deliveryCost;

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Product, Integer> items) {
        this.items = items;
    }

    public Map<Category, BigDecimal> getValidCategoryDiscounts() {
        return validCategoryDiscounts;
    }

    public void setValidCategoryDiscounts(Map<Category, BigDecimal> validCategoryDiscounts) {
        this.validCategoryDiscounts = validCategoryDiscounts;
    }

    public Coupon getValidCoupon() {
        return validCoupon;
    }

    public void setValidCoupon(Coupon validCoupon) {
        this.validCoupon = validCoupon;
    }

    public ShoppingCart() {
        items = new HashMap<>();
        validCategoryDiscounts = new HashMap<>();
    }

    public Integer getNumberOfDeliveries() {
        return (int) items.entrySet().stream().map(x -> x.getKey().getCategory()).distinct().count();
    }

    public Integer getNumberOfProducts() {
        return (int) items.entrySet().stream().map(x -> x.getKey()).distinct().count();
    }

    private BigDecimal getTotalAmountAfterCategoryDiscounts() {
        return getTotalAmount().subtract(getCampaignDiscount());
    }

    public BigDecimal getTotalAmount() {
        return items
                .entrySet()
                .stream()
                .map(x -> x.getKey().getAmount().multiply(new BigDecimal(x.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalAmountAfterDiscounts() {
        return getTotalAmountAfterCategoryDiscounts().subtract(getCouponDiscount());

    }

    public BigDecimal getCouponDiscount() {
        if (validCoupon == null) {
            return BigDecimal.ZERO;
        }
        switch (validCoupon.getDiscountType()) {
            case RATE:
                return getTotalAmountAfterCategoryDiscounts().multiply(validCoupon.getDiscountAmount()).divide(new BigDecimal(100), RoundingMode.HALF_UP);
            case AMOUNT:
                return validCoupon.getDiscountAmount();
            default:
                return BigDecimal.ZERO;
        }
    }

    public BigDecimal getCampaignDiscount() {
        return validCategoryDiscounts.entrySet().stream().map(x -> x.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }


    private BigDecimal calculateDiscount(Campaign campaign) {
        List<Map.Entry<Product, Integer>> filteredItems = items.entrySet().stream().filter(x -> x.getKey().getCategory().equals(campaign.getCategory())).collect(Collectors.toList());
        Integer quantity = filteredItems
                .stream()
                .map(x -> x.getValue())
                .reduce(0, Integer::sum);
        if (campaign.getMinimumQuantity() > quantity) {
            return BigDecimal.ZERO;
        }

        switch (campaign.getDiscountType()) {
            case AMOUNT:
                return campaign.getDiscountAmount();
            case RATE:
                return filteredItems
                        .stream()
                        .map(x -> x.getKey().getAmount().multiply(new BigDecimal(x.getValue())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .multiply(new BigDecimal(20))
                        .divide(new BigDecimal(100), RoundingMode.HALF_UP);
            default:
                return BigDecimal.ZERO;
        }

    }

    public void applyDiscounts(Campaign... campaigns) {
        validCategoryDiscounts.clear();
        if (campaigns == null) {
            return;
        }
        Map<Category, BigDecimal> maximumDiscounts = new HashMap<>();
        for (Campaign campaign : campaigns) {
            BigDecimal discount = calculateDiscount(campaign);
            if (discount.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            if (maximumDiscounts.containsKey(campaign.getCategory())) {
                if (maximumDiscounts.get(campaign.getCategory()).compareTo(discount) <= 0) {
                    maximumDiscounts.remove(maximumDiscounts.get(campaign.getCategory()));
                    maximumDiscounts.put(campaign.getCategory(), discount);
                }
                continue;
            }
            maximumDiscounts.put(campaign.getCategory(), discount);

        }
        validCategoryDiscounts.putAll(maximumDiscounts);
    }


    public void addItem(Product item, Integer quantity) {
        items.put(item, items.containsKey(item) ? (items.get(item) + quantity) : quantity);
    }

    public void removeItem(Product item) {
        if (!items.containsKey(item)) {
            throw new RuntimeException(item.getName() + " adlı ürün bulunmamaktadır.");
        }
        items.remove(items.get(item));
    }


    public void applyCoupon(Coupon coupon) {
        if (coupon == null) {
            return;
        }
        if (coupon.getMinimumAmount().compareTo(getTotalAmountAfterDiscounts()) > 0) {
            return;
        }
        validCoupon = coupon;
    }

    public void print() {

        String delimiter = String.join("", Collections.nCopies(125, "-"));

        StringBuilder builder = new StringBuilder();
        builder.append(delimiter + "\n");
        Map<Category, List<Map.Entry<Product, Integer>>> groupedByCategory = items.entrySet().stream().collect(Collectors.groupingBy(x -> x.getKey().getCategory()));
        builder.append(String.format("%-20s %-20s %-20s %-20s %-20s %-20s\n", "CategoryName", "ProductName", "Quantity", "Unit Price", "Total Price", "Total Discount"));
        builder.append(delimiter + "\n");

        for (Map.Entry<Category, List<Map.Entry<Product, Integer>>> entry : groupedByCategory.entrySet()) {
            builder.append(String.format("%-105s", entry.getKey().getName()));
            BigDecimal discount = validCategoryDiscounts.entrySet().stream().filter(x -> x.getKey().equals(entry.getKey())).map(x -> x.getValue()).findFirst().orElse(null);
            builder.append(discount != null ? String.format("%-20s\n", (discount + " TL")) : "\n");
            for (Map.Entry<Product, Integer> item : entry.getValue()) {
                builder.append(String.format("%20s ", ""));
                builder.append(String.format("%-20s ", item.getKey().getName()));
                builder.append(String.format("%-20s ", item.getValue().toString()));
                builder.append(String.format("%-20s ", item.getKey().getAmount().toString() + " TL"));
                builder.append(String.format("%-20s\n", item.getKey().getAmount().multiply(new BigDecimal(item.getValue())).toString() + " TL"));

            }
            builder.append(delimiter + "\n");

        }

        builder.append(String.format("%125s\n", "Coupon Discount"));
        builder.append(String.format("%125s\n", Optional.of(getCouponDiscount()).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP) + " TL"));
        builder.append(String.format("%125s\n", "Total Amount"));
        builder.append(String.format("%125s\n", Optional.of(getTotalAmount()).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP) + " TL"));
        builder.append(String.format("%125s\n", "Delivery Cost"));
        builder.append(String.format("%125s\n", Optional.of(getDeliveryCost()).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP) + " TL"));
        builder.append(String.format("%125s\n", "Total Amount with Delivery"));
        builder.append(String.format("%125s\n", Optional.of(getTotalAmount()).orElse(BigDecimal.ZERO).add(getDeliveryCost()).setScale(2, RoundingMode.HALF_UP) + " TL"));


        System.out.println(builder.toString());

    }
}
