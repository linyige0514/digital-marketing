/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author kal bugrara
 */
public class MarketCatalog {

    ArrayList<Market> markets;
    // ArrayList<Market> markets;

    public MarketCatalog() {
        markets = new ArrayList<Market>();
    }

    public ArrayList<Market> getMarkets() {
        return markets;
    }

    public void addMarket(Market market) {
        markets.add(market);
    }

    public Market pickRandomMarket() {
        if (markets.size() == 0)
            return null;
        Random r = new Random();
        int randomIndex = r.nextInt(markets.size());
        return markets.get(randomIndex);
    }

}
