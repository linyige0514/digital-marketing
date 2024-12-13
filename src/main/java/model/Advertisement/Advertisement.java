package model.Advertisement;

import model.MarketModel.Market;
import model.MarketModel.Channel;
import model.MarketModel.MarketChannelAssignment;

public class Advertisement {
    String name;
    MarketChannelAssignment marketChannelAssignment;
    Integer cost;

    public Advertisement(String name, MarketChannelAssignment marketChannelAssignment, Integer cost) {
        this.name = name;
        this.marketChannelAssignment = marketChannelAssignment;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public MarketChannelAssignment getMarketChannelAssignment() {
        return marketChannelAssignment;
    }

    public Integer getCost() {
        return cost;
    }

    public Market getMarket() {
        return marketChannelAssignment.getMarket();
    }

    public Channel getChannel() {
        return marketChannelAssignment.getChannel();
    }

}
