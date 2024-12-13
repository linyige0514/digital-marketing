/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;

import model.MarketModel.MarketChannelAssignment;

/**
 *
 * @author kal bugrara
 */
public class SolutionOffer {
    String name;
    ArrayList<Product> products;
    int price;// floor, ceiling, and target ideas
    MarketChannelAssignment marketChannelComb;
    ArrayList<Integer> priceComponents; // the prices attributed to each individual product

    public SolutionOffer(String name, MarketChannelAssignment m) {
        this.name = name;
        marketChannelComb = m;
        products = new ArrayList<Product>();
        priceComponents = new ArrayList<Integer>();
    }

    public String getName() {
        return name;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void addProduct(Product p, int price) {
        products.add(p);
        priceComponents.add(price);
    }

    public void setPrice(int p) {
        price = p;

    }

    public int getPrice() {
        return price;
    }

    public MarketChannelAssignment getMarketChannelAssignment() {
        return marketChannelComb;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Integer> getPriceComponents() {
        return priceComponents;
    }

    public ProductsReport generateProductPerformanceReport(String sortingRule) {
        ProductsReport productsReport = new ProductsReport(sortingRule);

        for (Product p : products) {
            ProductSummary ps = new ProductSummary(p);
            productsReport.addProductSummary(ps);
        }
        return productsReport;
    }
}
