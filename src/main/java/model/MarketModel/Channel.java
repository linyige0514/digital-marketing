/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class Channel {
  String name;

  ArrayList<MarketChannelAssignment> marketChannelCombinations;

  public Channel() {
    marketChannelCombinations = new ArrayList<MarketChannelAssignment>();
  }

}
