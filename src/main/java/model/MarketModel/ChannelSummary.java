package model.MarketModel;


import model.OrderManagement.OrderItem;

public class ChannelSummary {
    
    Channel channel;
    int quantity;
    int revenue;
    int advertisementCost;


    public ChannelSummary(Channel channel) {
        this.channel = channel;
        quantity = 0;
        revenue = 0;
        advertisementCost = 0;
    }

    public void addOrderItem(OrderItem oi) {
        quantity = quantity + oi.getQuantity();
        revenue = revenue + oi.getActualPrice() * oi.getQuantity();
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
        System.out.println("Channel Performance Report: ");
        System.out.println("Channel: " + channel.getName());
        System.out.println("Total Quantity: " + quantity);
        System.out.println("Total Revenue: " + revenue);
        System.out.println("Total Advertisement Cost: " + advertisementCost);
        System.out.println("Total Profit: " + getProfit());
    }
}
