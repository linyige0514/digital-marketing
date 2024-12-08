/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.HashMap;

import model.MarketModel.Market;
import model.MarketModel.MarketChannelAssignment;
import model.OrderManagement.OrderItem;

/**
 *
 * @author kal bugrara
 */
public class SolutionOffer {
    String name;
    HashMap<Product, Float> products;
    int targetPrice; // floor, ceiling, and target ideas
    ArrayList<OrderItem> orderItems;

    MarketChannelAssignment marketChannelComb;

    public SolutionOffer(MarketChannelAssignment m, int price, Product p) {
        targetPrice = price;
        marketChannelComb = m;
        m.addSolutionOffer(this);
        products = new HashMap<Product, Float>();
        products.put(p, 1.0f);
        p.addBundle(this);
        orderItems = new ArrayList<OrderItem>();
    }

    public void addProduct(Product p, float priceFract) {
        products.put(p, priceFract);
        p.addBundle(this);
        normalize();
    }

    public void normalize() {
        float total = 0f;
        for (float share : products.values()) {
            total += share;
        }
        for (Product p : products.keySet()) {
            float currentShare = products.get(p);
            products.replace(p, currentShare / total);
        }
    }

    public Market getMarket() {
        return marketChannelComb.getMarket();
    }

    public int getSalesQuantity() {
        int total = 0;
        for (OrderItem oi : orderItems) {
            total += oi.getQuantity();
        }
        return total;
    }

    public void setPrice(int p) {
        targetPrice = p;
    }

    public void addOrderItem(OrderItem oi) {
        orderItems.add(oi);
    }

    public int getTargetPrice() {
        return targetPrice;
    }

    public String getBundleName() {
        if (products.size() == 0) {
            return "No products in the bundle";
        }
        return name;
    }

    public int getSalesVolume() {
        int total = 0;
        for (OrderItem oi : orderItems) {
            total += oi.getOrderItemTotal();
        }
        return total;
    }

    public int getSalesShare(Product p) {
        Float result = products.get(p) == null ? 0 : products.get(p) * getSalesVolume();
        return result.intValue();
    }

    public int getAdsBudgetShare(Product p) {
        Float result = products.get(p) == null ? 0
                : products.get(p) * marketChannelComb.getAdvertisingBudgetShare(this);
        return result.intValue();
    }

}
