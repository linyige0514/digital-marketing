/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.HashMap;
import java.util.Set;

import com.github.javafaker.Faker;

import model.Advertisement.Advertisement;
import model.Advertisement.AdvertisementCatalog;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.MarketModel.Channel;
import model.MarketModel.ChannelCatalog;
import model.MarketModel.ChannelSummary;
import model.MarketModel.Market;
import model.MarketModel.MarketCatalog;
import model.MarketModel.MarketChannelAssignment;
import model.MarketModel.MarketSummary;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.Personnel.Person;
import model.Personnel.PersonDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.SolutionOffer;
import model.ProductManagement.SolutionOfferCatalog;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;

/**
 *
 * @author kal bugrara
 */
public class ConfigureABusiness {

  static int upperPriceLimit = 50;
  static int lowerPriceLimit = 10;
  static int range = 5;
  static int productMaxQuantity = 5;
  static int solutionOfferCount = 10;
  static int solutionOfferMinProducts = 2;
  static int solutionOfferMaxProducts = 10;
  static float solutionOfferMinDiscount = 0.6f;
  static float solutionOfferMaxDiscount = 0.9f;
  static int advertisementMinCost = 1000;
  static int advertisementMaxCost = 5000;

  public static Business createABusinessAndLoadALotOfData(String name, int supplierCount, int productCount,
      int customerCount, int orderCount, int itemCount) {
    Business business = new Business(name);

    // Add Markets
    loadMarkets(business);

    // Add Channels
    loadChannels(business);

    // Configure advertisements
    loadAdvertisements(business, advertisementMinCost, advertisementMaxCost);

    // Add Suppliers +
    loadSuppliers(business, supplierCount);

    // Add Products +
    loadProducts(business, productCount);

    // Add Customers
    loadCustomers(business, customerCount);

    // Add Solution Offers
    loadSolutionOffers(business, solutionOfferCount, solutionOfferMinProducts, solutionOfferMaxProducts,
        solutionOfferMinDiscount, solutionOfferMaxDiscount);

    // Add Order
    loadOrders(business, orderCount, itemCount);

    // printMarketReport(business);
    // printChannelReport(business);
    // printProductPerformanceByMarket(business);
    // printAdvertisingEfficiency(business);

    return business;
  }

  public static void loadSuppliers(Business b, int supplierCount) {
    Faker faker = new Faker();

    SupplierDirectory supplierDirectory = b.getSupplierDirectory();
    for (int index = 1; index <= supplierCount; index++) {
      supplierDirectory.newSupplier(faker.company().name());
    }
  }

  public static void loadMarkets(Business b) {
    MarketCatalog marketCatalog = b.getMarketcatalog();
    Market consumerMarket = new Market("consumer");
    Market b2bMarket = new Market("b2b");
    Market internationalMarket = new Market("international");
    marketCatalog.addMarket(consumerMarket);
    marketCatalog.addMarket(b2bMarket);
    marketCatalog.addMarket(internationalMarket);
  }

  public static void loadAdvertisements(Business b, int minCost, int maxCost) {
    AdvertisementCatalog advertisementCatalog = b.getAdvertisementcatalog();
    // Find all combinations of markets and channels
    MarketCatalog marketCatalog = b.getMarketcatalog();
    ChannelCatalog channelCatalog = b.getChannelcatalog();

    for (int i = 0; i < marketCatalog.getMarkets().size(); i++) {
      for (int j = 0; j < channelCatalog.getChannels().size(); j++) {
        Market market = marketCatalog.getMarkets().get(i);
        Channel channel = channelCatalog.getChannels().get(j);
        MarketChannelAssignment msa = new MarketChannelAssignment(market, channel);
        Advertisement advertisement = new Advertisement("Market_" + market.getName() + "_Channel" + channel.getName(),
            msa, getRandom(minCost, maxCost));
        advertisementCatalog.addAdvertisement(advertisement, msa);
      }
    }
  }

  public static void loadChannels(Business b) {
    ChannelCatalog channelCatalog = b.getChannelcatalog();
    Channel searchEngineMarketingChannel = new Channel("searchEngineMarketing");
    Channel socialMediaMarketingChannel = new Channel("socialMediaMarketing");
    Channel emailMarketingChannel = new Channel("emailMarketing");
    Channel contentMarketingChannel = new Channel("contentMarketing");
    channelCatalog.addChannel(searchEngineMarketingChannel);
    channelCatalog.addChannel(socialMediaMarketingChannel);
    channelCatalog.addChannel(emailMarketingChannel);
    channelCatalog.addChannel(contentMarketingChannel);
  }

  public static void loadSolutionOffers(
      Business b, int solutionOfferCount, int minProducts, int maxProducts,
      float minDiscount, float maxDiscount) {
    SolutionOfferCatalog solutionOfferCatalog = b.getSolutionOfferCatalog();
    SupplierDirectory supplierDirectory = b.getSupplierDirectory();
    MarketCatalog marketCatalog = b.getMarketcatalog();
    ChannelCatalog channelCatalog = b.getChannelcatalog();

    for (int index = 1; index <= solutionOfferCount; index++) {
      int numProducts = getRandom(minProducts, maxProducts);
      Market randomMarket = marketCatalog.pickRandomMarket();
      Channel randomChannel = channelCatalog.pickRandomChannel();
      MarketChannelAssignment randomAssignment = new MarketChannelAssignment(randomMarket, randomChannel);
      SolutionOffer solutionOffer = new SolutionOffer("Offer #" + index, randomAssignment);
      randomMarket.addSolutionOffer(solutionOffer);
      randomMarket.addChannel(randomAssignment);

      // Randomly pick products and add to the solution offer
      int randomSupplierIndex = getRandom(1, supplierDirectory.getSupplierList().size());
      Supplier randomSupplier = supplierDirectory.getSupplierList().get(randomSupplierIndex);
      ProductCatalog productCatalog = randomSupplier.getProductCatalog();
      int totalPrice = 0;
      for (int productIndex = 1; productIndex <= numProducts; productIndex++) {
        Product randomProduct = productCatalog.pickRandomProduct();
        int randomPrice = getRandom(randomProduct.getFloorPrice(), randomProduct.getCeilingPrice());
        float discount = getRandomFloat(minDiscount, maxDiscount);
        int discountedPrice = (int) (Math.round(randomPrice * discount));
        solutionOffer.addProduct(randomProduct, discountedPrice);
        totalPrice += discountedPrice;
      }
      solutionOffer.setPrice(totalPrice);
      solutionOfferCatalog.addSolutionOffer(solutionOffer);
    }
  }

  public static void printChannelReport(Business b) {
    ArrayList<Channel> channels = b.getChannelcatalog().getChannels();
    ArrayList<String> headerElements = new ArrayList<String>();
    HashMap<Channel, ChannelSummary> channelToSummary = b.getChannelReports();

    headerElements.add("Channel ID");
    for (String field : new String[] { "Revenue", "Cost", "Profit" }) {
      headerElements.add(field);
    }
    System.out.println("=== Channel Profitability Performance ===");
    for (int i = 0; i < headerElements.size(); i++) {
      if (i == 0) {
        System.out.printf("%-50s", headerElements.get(i));
      } else {
        System.out.printf("%-20s", headerElements.get(i));
      }
    }
    System.out.println();
    for (Channel channel : channels) {
      ArrayList<String> lineElements = new ArrayList<String>();
      lineElements.add(channel.getName());
      lineElements.add(Integer.toString(channelToSummary.get(channel).getRevenue()));
      lineElements.add(Integer.toString(channelToSummary.get(channel).getAdvertisementCost()));
      lineElements.add(Integer.toString(channelToSummary.get(channel).getProfit()));
      for (int i = 0; i < lineElements.size(); i++) {
        if (i == 0) {
          System.out.printf("%-50s", lineElements.get(i));
        } else {
          System.out.printf("%-20s", lineElements.get(i));
        }
      }
      System.out.println();
    }
  }

  public static HashMap<SolutionOffer, Integer> getSolutionOfferAdvertisingBudget(Business b) {
    // Get the solution offers
    ArrayList<SolutionOffer> solutionOffers = b.getSolutionOfferCatalog().getSolutionOffers();
    // Count the number of solution offers per market-channel assignment
    HashMap<String, Integer> mcaToCount = new HashMap<String, Integer>();
    for (SolutionOffer so : solutionOffers) {
      MarketChannelAssignment mca = so.getMarketChannelAssignment();
      if (mcaToCount.containsKey(mca.getIdString())) {
        mcaToCount.put(mca.getIdString(), mcaToCount.get(mca.getIdString()) + 1);
      } else {
        mcaToCount.put(mca.getIdString(), 1);
      }
    }
    // Compute the advertising budget per solution offer
    HashMap<SolutionOffer, Integer> soToBudget = new HashMap<SolutionOffer, Integer>();
    for (SolutionOffer so : solutionOffers) {
      MarketChannelAssignment mca = so.getMarketChannelAssignment();
      int totalBudget = b.getAdvertisementcatalog().getAllAdvertisements().get(mca.getIdString()).getCost();
      int budgetPerOffer = totalBudget / mcaToCount.get(mca.getIdString());
      soToBudget.put(so, budgetPerOffer);
    }
    return soToBudget;
  }

  public static void printAdvertisingEfficiency(Business b) {
    HashMap<SolutionOffer, Integer> soToBudget = getSolutionOfferAdvertisingBudget(b);
    HashMap<SolutionOffer, Integer> soToRevenue = new HashMap<SolutionOffer, Integer>();
    ArrayList<SolutionOffer> solutionOffers = b.getSolutionOfferCatalog().getSolutionOffers();
    ArrayList<Order> orders = b.getMasterOrderList().getOrderList();
    for (Order order : orders) {
      for (OrderItem oi: order.getOrderItems()) {
        SolutionOffer so = oi.getSelectedSolutionOffer();
        int revenue = soToRevenue.getOrDefault(so, 0);
        revenue += oi.getActualPrice() * oi.getQuantity();
        soToRevenue.put(so, revenue);
      }
    }
    System.out.println("=== Advertising Efficiency ===");
    ArrayList<String> headerElements = new ArrayList<String>();
    headerElements.add("Solution Offer");
    headerElements.add("Market");
    headerElements.add("Channel");
    headerElements.add("Revenue");
    headerElements.add("Budget");
    headerElements.add("Efficiency");
    for (String field: headerElements) {
      System.out.printf("%-30s", field);
    }
    System.out.println();

    for (SolutionOffer so: solutionOffers) {
      ArrayList<String> lineElements = new ArrayList<String>();
      lineElements.add(so.getName());
      lineElements.add(so.getMarketChannelAssignment().getMarket().getName());
      lineElements.add(so.getMarketChannelAssignment().getChannel().getName());
      int revenue = soToRevenue.get(so);
      int budget = soToBudget.get(so);
      float efficiency = (float) revenue / budget;
      lineElements.add(Integer.toString(revenue));
      lineElements.add(Integer.toString(budget));
      lineElements.add(Float.toString(efficiency));
      for (String field: lineElements) {
        System.out.printf("%-30s", field);
      }
      System.out.println();
    }
  }

  public static void printMarketReport(Business b) {
    ArrayList<Market> markets = b.getMarketcatalog().getMarkets();
    ArrayList<String> headerElements = new ArrayList<String>();
    HashMap<Market, MarketSummary> marketToSummary = b.getMarketReports();
    System.out.println("=== Market Profitability Performance ===");
    headerElements.add("Market ID");
    for (String field : new String[] { "Revenue", "Cost", "Profit" }) {
      headerElements.add(field);
    }
    for (int i = 0; i < headerElements.size(); i++) {
      if (i == 0) {
        System.out.printf("%-50s", headerElements.get(i));
      } else {
        System.out.printf("%-20s", headerElements.get(i));
      }
    }
    System.out.println();
    for (Market market : markets) {
      ArrayList<String> lineElements = new ArrayList<String>();
      lineElements.add(market.getName());
      lineElements.add(Integer.toString(marketToSummary.get(market).getRevenue()));
      lineElements.add(Integer.toString(marketToSummary.get(market).getAdvertisementCost()));
      lineElements.add(Integer.toString(marketToSummary.get(market).getProfit()));
      for (int i = 0; i < lineElements.size(); i++) {
        if (i == 0) {
          System.out.printf("%-50s", lineElements.get(i));
        } else {
          System.out.printf("%-20s", lineElements.get(i));
        }
      }
      System.out.println();
    }
  }

  public static void printProductPerformanceByMarket(Business b) {
    ArrayList<SolutionOffer> solutionOffers = b.getSolutionOfferCatalog().getSolutionOffers();
    Set<Product> products = new HashSet<Product>();
    ArrayList<Market> markets = b.getMarketcatalog().getMarkets();
    HashMap<Market, MarketSummary> marketToSummary = b.getMarketReports();
    ArrayList<String> headerElements = new ArrayList<String>();

    headerElements.add("Product ID");
    for (Market market : markets) {
      for (String field : new String[] { "Qty", "Rev" }) {
        headerElements.add(market.getName() + "(" + field + ")");
      }
    }

    // Print the header
    System.out.println("=== Product performance in each market ===");
    System.out.println();
    for (int i = 0; i < headerElements.size(); i++) {
      if (i == 0) {
        System.out.printf("%-50s", headerElements.get(i));
      } else {
        System.out.printf("%-20s", headerElements.get(i));
      }
    }
    System.out.println();
    for (int i = 0; i < 170; i++) {
      System.out.print("-");
    }
    System.out.println();
    // List all products covered in the solution offers
    for (SolutionOffer so : solutionOffers) {
      products.addAll(so.getProducts());
    }

    for (Product product : products) {
      ArrayList<String> lineElements = new ArrayList<String>();
      lineElements.add(product.getName());
      for (Market market : markets) {
        lineElements.add(Integer.toString(marketToSummary.get(market).getProduct2Quantity().getOrDefault(product, 0)));
        lineElements.add(Integer.toString(marketToSummary.get(market).getProduct2Revenue().getOrDefault(product, 0)));
      }
      for (int i = 0; i < lineElements.size(); i++) {
        if (i == 0) {
          System.out.printf("%-50s", lineElements.get(i));
        } else {
          System.out.printf("%-20s", lineElements.get(i));
        }
      }
      System.out.println();
    }
  }

  static void loadProducts(Business b, int productCount) {
    SupplierDirectory supplierDirectory = b.getSupplierDirectory();

    for (Supplier supplier : supplierDirectory.getSupplierList()) {

      int randomProductNumber = getRandom(1, productCount);
      ProductCatalog productCatalog = supplier.getProductCatalog();

      for (int index = 1; index <= randomProductNumber; index++) {

        String productName = "Product #" + index + " from " + supplier.getName();
        int randomFloor = getRandom(lowerPriceLimit, lowerPriceLimit + range);
        int randomCeiling = getRandom(upperPriceLimit - range, upperPriceLimit);
        int randomTarget = getRandom(randomFloor, randomCeiling);

        productCatalog.newProduct(productName, randomFloor, randomCeiling, randomTarget);
      }
    }
  }

  static float getRandomFloat(float min, float max) {
    Random rand = new Random();
    return rand.nextFloat() * (max - min) + min;
  }

  static int getRandom(int lower, int upper) {
    Random r = new Random();

    // nextInt(n) will return a number from zero to 'n'. Therefore e.g. if I want
    // numbers from 10 to 15
    // I will have result = 10 + nextInt(5)
    int randomInt = lower + r.nextInt(upper - lower);
    return randomInt;
  }

  static void loadCustomers(Business b, int customerCount) {
    CustomerDirectory customerDirectory = b.getCustomerDirectory();
    PersonDirectory personDirectory = b.getPersonDirectory();

    Faker faker = new Faker();

    for (int index = 1; index <= customerCount; index++) {
      Person newPerson = personDirectory.newPerson(faker.name().fullName());
      customerDirectory.newCustomerProfile(newPerson);
    }
  }

  static void loadOrders(Business b, int orderCount, int itemCount) {

    // reach out to masterOrderList
    MasterOrderList mol = b.getMasterOrderList();

    // pick a random customer (reach to customer directory)
    CustomerDirectory cd = b.getCustomerDirectory();
    // SupplierDirectory sd = b.getSupplierDirectory();

    for (int index = 0; index < orderCount; index++) {

      CustomerProfile randomCustomer = cd.pickRandomCustomer();
      if (randomCustomer == null) {
        System.out.println("Cannot generate orders. No customers in the customer directory.");
        return;
      }

      // create an order for that customer
      // pick the random sulution offer
      SolutionOffer randomSolutionOffer = b.getSolutionOfferCatalog().pickRandomSolutionOffer();
      Integer productCount = randomSolutionOffer.getProducts().size();
      MarketChannelAssignment msa = randomSolutionOffer.getMarketChannelAssignment();
      Order randomOrder = mol.newOrder(randomCustomer, msa);

      // add order items
      // -- pick a supplier first (randomly)
      // -- pick a product (randomly)
      // -- actual price, quantity

      for (int itemIndex = 0; itemIndex < productCount; itemIndex++) {

        // Supplier randomSupplier = sd.pickRandomSupplier();
        // if (randomSupplier == null) {
        // System.out.println("Cannot generate orders. No supplier in the supplier
        // directory.");
        // return;
        // }
        // ProductCatalog pc = randomSupplier.getProductCatalog();
        // Product randomProduct = pc.pickRandomProduct();
        // if (randomProduct == null) {
        // System.out.println("Cannot generate orders. No products in the product
        // catalog.");
        // return;
        // }

        // int randomPrice = getRandom(randomProduct.getFloorPrice(),
        // randomProduct.getCeilingPrice());
        Product product = randomSolutionOffer.getProducts().get(itemIndex);
        int randomQuantity = getRandom(1, productMaxQuantity);
        int price = randomSolutionOffer.getPriceComponents().get(itemIndex);
        randomOrder.newOrderItem(product, randomSolutionOffer, price, randomQuantity);
      }
    }
    // Make sure order items are connected to the order

  }

}
