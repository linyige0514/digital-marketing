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
public class ChannelCatalog {

    ArrayList<Channel> channels;

    public ChannelCatalog() {
        channels = new ArrayList<Channel>();
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    public Channel pickRandomChannel() {
        if (channels.size() == 0)
            return null;
        Random r = new Random();
        int randomIndex = r.nextInt(channels.size());
        return channels.get(randomIndex);
    }

}
