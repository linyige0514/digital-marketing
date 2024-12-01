/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;

import model.MarketModel.MarketChannelAssignment;
import model.OrderManagement.OrderItem;

/**
 *
 * @author kal bugrara
 */
public class SolutionOffer {
    ArrayList<Product> products;
    int targetPrice; // floor, ceiling, and target ideas
    ArrayList<OrderItem> orderItems;

    MarketChannelAssignment marketChannelComb;

    public SolutionOffer(MarketChannelAssignment m, int price, Product p) {
        targetPrice = price;
        marketChannelComb = m;
        m.addSolutionOffer(this);
        products = new ArrayList<Product>();
        products.add(p);
        orderItems = new ArrayList<OrderItem>();
    }

    public void addProduct(Product p) {
        products.add(p);
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
        return products.get(0).getName() + marketChannelComb.getName();
    }

    public int getSalesVolume() {
        int total = 0;
        for (OrderItem oi : orderItems) {
            total += oi.getOrderItemTotal();
        }
        return total;
    }

}
