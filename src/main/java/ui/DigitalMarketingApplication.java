/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import model.Advertisement.Advertisement;
import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.MarketModel.Market;
import model.MarketModel.MarketChannelAssignment;
import model.ProductManagement.SolutionOffer;
import model.MarketModel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author kal bugrara
 */
public class DigitalMarketingApplication {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    // TODO - Digital Marketing
    /*
     * ## Part 1 - Populating the model
     * 
     * 1. Your company sells products to three different markets and uses four
     * channels. You can create your own names of the company, markets and channels.
     */

    // Initialize the company and the markets
    System.out.println("--------**** ADMIN CONSOLE ****--------");

    Business business = ConfigureABusiness.createABusinessAndLoadALotOfData(
        "Amazon", 50, 30, 30,
        100,
        10);
    System.out.println("Business: " + business.getName());
    Scanner scanner = new Scanner(System.in);
    String welcomeMessage = "Welcome to the system! Please enter a number to retrieve the relevant report" +
        "\nFor your business " + business.getName() + ", the following reports are available: " +
        "\n1. Market profitability report" +
        "\n2. Channel profitability report" +
        "\n3. Product performance report by market" +
        "\n4. Advertising Efficiency report";
    System.out.print(welcomeMessage);
    int intValue = scanner.nextInt();
    if (intValue == 1) {
        ConfigureABusiness.printMarketReport(business);
      } else if (intValue == 2) {
        ConfigureABusiness.printChannelReport(business);
      } else if (intValue == 3) {
        ConfigureABusiness.printProductPerformanceByMarket(business);
      } else if (intValue == 4) {
        ConfigureABusiness.printAdvertisingEfficiency(business);
      } else {
        System.out.println("Invalid input");
        scanner.close();
      }
    ArrayList<SolutionOffer> solutionOffers = business.getSolutionOfferCatalog().getSolutionOffers();

    System.out.println("--------**** USER CONSOLE ****--------");

    HashMap<String, Integer> numSolutionOffersPerMarketChannel = new HashMap<>();
    for (SolutionOffer so : solutionOffers) {
      MarketChannelAssignment mca = so.getMarketChannelAssignment();
      String idString = mca.getIdString();
      numSolutionOffersPerMarketChannel.put(idString, numSolutionOffersPerMarketChannel.getOrDefault(idString, 0) + 1);
    }
    System.out.println("Below is the number of solution offers available for each Market-Channel combination:");
    for (String idString : numSolutionOffersPerMarketChannel.keySet()) {
      System.out.println(idString + ": " + numSolutionOffersPerMarketChannel.get(idString) + " solution offers");
    }

  
    System.out.println("Select a Market:");
    System.out.println("1. Consumer\n2. B2B\n3. International");
    System.out.print("Your choice: ");
    int marketChoice = scanner.nextInt();
    scanner.nextLine(); // Consume the newline

    System.out.println("Select a Channel:");
    System.out.println("1. Search Engine\n2. Social Media\n3. Email\n4. Content");
    System.out.print("Your choice: ");
    int channelChoice = scanner.nextInt();
    scanner.nextLine(); // Consume the newline

    ArrayList<Market> markets = business.getMarketcatalog().getMarkets();
    ArrayList<Channel> channels = business.getChannelcatalog().getChannels();
    Market market = switch (marketChoice) {
      case 1 -> markets.get(0);
      case 2 -> markets.get(1);
      case 3 -> markets.get(2);
      default -> null;
    };
    if (market == null) {
      System.out.println("Invalid Market Choice");
      scanner.close();
      return;
    }

    Channel channel = switch (channelChoice) {
        case 1 -> channels.get(0);
        case 2 -> channels.get(1);
        case 3 -> channels.get(2);
        case 4 -> channels.get(3);
        default -> null;
    };
    if (channel == null) {
      System.out.println("Invalid Channel Choice");
      scanner.close();
      return;
    }
    System.out.println("\nHello! You have chosen " + market + " in " + channel + ".\n");
    MarketChannelAssignment mca = new MarketChannelAssignment(market, channel);
    HashMap<String, Advertisement> advertisements = business.getAdvertisementcatalog().getAllAdvertisements();
    Advertisement ad = advertisements.get(mca.getIdString());
    System.out.println("Advertisement:" + ad.getName() + "\n");

    ArrayList<SolutionOffer> availableSolutionOffers = new ArrayList<>();
    for (SolutionOffer so : solutionOffers) {
      if (so.getMarketChannelAssignment().getChannel() == mca.getChannel() && so.getMarketChannelAssignment().getMarket() == mca.getMarket()) {
        availableSolutionOffers.add(so);
      }
    }
    if (availableSolutionOffers.isEmpty()) {
      System.out.println("Sorry! No solution offers available for this market and channel.");
      scanner.close();
      return;
    } else {
      System.out.println("Available Solution Offers:");
      for (int i = 0; i < availableSolutionOffers.size(); i++) {
        System.out.println((i + 1) + ". " + availableSolutionOffers.get(i).getName() + " - $" + availableSolutionOffers.get(i).getPrice());
      }
    }
    
    List<SolutionOffer> cart = new ArrayList<>();
    while (true) {
      System.out.print("Enter the number of the solution to add to cart (or 0 to finish): ");
      int choice = scanner.nextInt();
      if (choice == 0) {
          break;
      }

      if (choice > 0 && choice <= availableSolutionOffers.size()) {
          SolutionOffer selectedSolution = availableSolutionOffers.get(choice - 1);
          cart.add(selectedSolution);
          System.out.println(selectedSolution.getName() + " has been added to your cart.");
      } else {
          System.out.println("Invalid choice. Please try again.");
      }
    }
      // Step 5: Complete Purchase
      if (!cart.isEmpty()) {
        System.out.println("\nYour Shopping Cart:");
        double total = 0;
        for (SolutionOffer item : cart) {
            System.out.println("- " + item + " ($" + item.getPrice() + ")");
            total += item.getPrice();
        }
        System.out.printf("Total: $%.2f\n", total);

        System.out.print("Do you want to complete the purchase? (yes/no): ");
        String confirmation = scanner.next();

        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println("\nThank you for your purchase!");
            System.out.println("Order Confirmation: Your total is $" + total + ".");
        } else {
            System.out.println("\nPurchase cancelled.");
        }
    } else {
        System.out.println("\nYour cart is empty. No purchase was made.");
    }

    scanner.close();


    /*
     * 2. You sell more than thirty different products, pricing them differently for
     * different markets. You are also using different channels to reach your
     * customers.
     * 
     * 3. Every quarter your advertising agency gives you a breakdown of advertising
     * costs, for each market and by each channel. Link to advertising expense
     * table:
     * (https://northeastern-my.sharepoint.com/:i:/g/personal/
     * a_lelashvili_northeastern_edu/Ea1mOtQvG9pGqrSyVmxA_e4BFZx-LZCVa-nu4XLYW3h5Uw?
     * e=0YozCW)
     * 
     * 4. Autogenerate sales orders and randomly pick the following:
     * - Solution Offer
     * - Market
     * - Channel
     * - Price
     * 
     * ## Part 2 – Build reports
     * 
     * 1. Create Market profitability report. This report should show how same
     * product sold under different solution offers performed in different Markets.
     * You should show total sales revenues as well as advertising costs and profit.
     * 
     * 2. Create Channel profitability report. This report should show which channel
     * was easier to sell, required less advertising budget and still sold well.
     * 
     * 3. Advertising Efficiency. This report should do a breakdown of the
     * advertising budget for the solution offers sold in each market sold through
     * each channel. Create a way to allocate Advertising costs and show the results
     * in a table.
     * 
     * 4. Implement a user interaction where the user (with Admin role) can choose
     * from list of reports and see the report.
     * 
     * ## Part 3 - User Interaction
     * 
     * 1. Implement a user interaction through the terminal.
     * 2. The user can choose options from menus and pick its own choices.
     * 3. Implement the following features:
     * - User can identify (or pick) its profile with Market and Channel choices.
     * - User will see an advertisement based on the choices above.
     * - User can see list of available solution offers and prices and can choose
     * one or more to add to a shopping cart.
     * - User can complete the purchase and get order confirmation.
     * 
     * 4. Add any additional features you think will improve user experience
     */
  }
}
