/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;

import java.util.ArrayList;
import model.ProductManagement.SolutionOffer;

/**
 *
 * @author kal bugrara
 */
public class MarketChannelAssignment {
  Market market;
  Channel channel;
  ArrayList<SolutionOffer> bundles;

  int advertisingBudget;

  public MarketChannelAssignment(Market m, Channel c) {
    bundles = new ArrayList<SolutionOffer>();
    market = m;
    channel = c;
  }

  public void addSolutionOffer(SolutionOffer so) {
    bundles.add(so);
  }

  public Market getMarket() {
    return market;
  }

  public Channel getChannel() {
    return channel;
  }

  public int getAdvertisingBudget() {
    return advertisingBudget;
  }

  public void setAdvertisingBudget(int advertisingBudget) {
    this.advertisingBudget = advertisingBudget;
  }

  public int getSalesVolume() {
    int total = 0;
    for (SolutionOffer so : bundles) {
      total += so.getSalesVolume();
    }
    return total;
  }

  public int getAdvertisingBudgetShare(SolutionOffer so) {
    // for each solution offer, its share in budget would be = sales * budget /
    // total sales
    if (!bundles.contains(so))
      return 0;
    return so.getSalesVolume() * advertisingBudget / this.getSalesVolume();
  }

  public String getName() {
    // TODO Implement name for market - channel combinations
    return "some market, some channel";
  }

}
