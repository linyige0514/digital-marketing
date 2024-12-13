package model.MarketModel;

import java.util.HashMap;

import model.OrderManagement.OrderItem;
import model.ProductManagement.Product;

public class MarketSummary {

    Market market;
    int quantity;
    int revenue;
    int advertisementCost;
    HashMap<Product, Integer> product2Quantity;
    HashMap<Product, Integer> product2Revenue;

    public MarketSummary(Market market) {
        this.market = market;
        quantity = 0;
        revenue = 0;
        advertisementCost = 0;
        product2Quantity = new HashMap<Product, Integer>();
        product2Revenue = new HashMap<Product, Integer>();
    }

    public void addOrderItem(OrderItem oi) {
        quantity = quantity + oi.getQuantity();
        revenue = revenue + oi.getActualPrice() * oi.getQuantity();
        Product product = oi.getSelectedProduct();
        if (product2Quantity.containsKey(product)) {
            product2Quantity.put(product, product2Quantity.get(product) + oi.getQuantity());
            product2Revenue.put(product, product2Revenue.get(product) + oi.getActualPrice() * oi.getQuantity());
        } else {
            product2Quantity.put(product, oi.getQuantity());
            product2Revenue.put(product, oi.getActualPrice() * oi.getQuantity());
        }
    }

    public HashMap<Product, Integer> getProduct2Quantity() {
        return product2Quantity;
    }

    public HashMap<Product, Integer> getProduct2Revenue() {
        return product2Revenue;
    }

    public void addAdvertisementCost(int cost) {
        advertisementCost = advertisementCost + cost;
    }

    public int getProfit() {
        return revenue - advertisementCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getAdvertisementCost() {
        return advertisementCost;
    }

    public void printProductReport() {
        System.out.println("Market Performance Report: ");
        System.out.println("Market: " + market.getName());
        System.out.println("Total Quantity: " + quantity);
        System.out.println("Total Revenue: " + revenue);
        System.out.println("Total Advertisement Cost: " + advertisementCost);
        System.out.println("Total Profit: " + getProfit());
    }
}
