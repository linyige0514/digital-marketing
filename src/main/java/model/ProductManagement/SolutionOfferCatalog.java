/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author kal bugrara
 */
public class SolutionOfferCatalog {
    ArrayList<SolutionOffer> solutionoffers;

    public SolutionOfferCatalog() {
        solutionoffers = new ArrayList<SolutionOffer>();
    }
    public ArrayList<SolutionOffer> getSolutionOffers() {
        return solutionoffers;
    }

    public void addSolutionOffer(SolutionOffer so) {
        solutionoffers.add(so);
    }

    public SolutionOffer pickRandomSolutionOffer() {
        if (solutionoffers.size() == 0)
            return null;
        Random r = new Random();
        int randomIndex = r.nextInt(solutionoffers.size());
        return solutionoffers.get(randomIndex);
    }

}