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
public class Market {
  ArrayList<SolutionOffer> so;
  ArrayList<MarketChannelAssignment> channels;
  ArrayList<String> characteristics;
  ArrayList<Market> submarkets;
  int size;

  public Market(String s) {
    characteristics = new ArrayList<String>();
    characteristics.add(s);
    so = new ArrayList<SolutionOffer>();
    channels = new ArrayList<MarketChannelAssignment>();
  }

  public void addSolutionOffer(SolutionOffer s) {
    so.add(s);
  }

  public void addChannel(MarketChannelAssignment mca) {
    channels.add(mca);
  }

  public String getName(){
    return characteristics.get(0);
  }

}
