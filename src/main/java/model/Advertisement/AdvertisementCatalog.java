package model.Advertisement;

import java.util.HashMap;

import model.MarketModel.MarketChannelAssignment;
import model.MarketModel.Channel;
import model.MarketModel.Market;

public class AdvertisementCatalog {
    HashMap<String, Advertisement> advertisementMap;

    public AdvertisementCatalog() {
        advertisementMap = new HashMap<String, Advertisement>();
    }
    public HashMap<String, Advertisement> getAllAdvertisements() {
        return advertisementMap;
    }

    public void addAdvertisement(Advertisement ad, MarketChannelAssignment mca) {
        advertisementMap.put(mca.getIdString(), ad);
    }

    public Advertisement getAdvertisement(MarketChannelAssignment mca) {
        return advertisementMap.get(mca.getIdString());
    }

    public int getTotalCost() {
        int sum = 0;
        for (Advertisement ad : advertisementMap.values()) {
            sum = sum + ad.getCost();
        }
        return sum;
    }

    public int getCostByChannel(Channel channel) {
        int sum = 0;
        for (Advertisement ad : advertisementMap.values()) {
            if (ad.getChannel().equals(channel)) {
                sum = sum + ad.getCost();
            }
        }
        return sum;
    }

    public int getCostByMarket(Market market) {
        int sum = 0;
        for (Advertisement ad : advertisementMap.values()) {
            if (ad.getMarket().equals(market)) {
                sum = sum + ad.getCost();
            }
        }
        return sum;
    }
}
