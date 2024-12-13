/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.Advertisement.AdvertisementCatalog;
import model.CustomerManagement.CustomerDirectory;
import model.MarketModel.Channel;
import model.MarketModel.ChannelCatalog;
import model.MarketModel.ChannelSummary;
import model.MarketModel.Market;
import model.MarketModel.MarketCatalog;
import model.MarketModel.MarketSummary;
import model.MarketingManagement.MarketingPersonDirectory;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.Personnel.EmployeeDirectory;
import model.Personnel.PersonDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;
import model.ProductManagement.SolutionOffer;
import model.ProductManagement.SolutionOfferCatalog;
import model.SalesManagement.SalesPersonDirectory;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.UserAccountDirectory;

/**
 *
 * @author kal bugrara
 */
public class Business {

    String name;
    PersonDirectory persondirectory;
    MasterOrderList masterorderlist;
    SupplierDirectory suppliers;
    MarketCatalog marketcatalog;
    ChannelCatalog channelcatalog;
    SolutionOfferCatalog solutionoffercatalog;
    CustomerDirectory customerdirectory;
    EmployeeDirectory employeedirectory;
    SalesPersonDirectory salespersondirectory;
    UserAccountDirectory useraccountdirectory;
    MarketingPersonDirectory marketingpersondirectory;
    AdvertisementCatalog advertisementcatalog;

    public Business(String n) {
        name = n;
        masterorderlist = new MasterOrderList();
        suppliers = new SupplierDirectory(this);
        // solutionoffercatalog = new SolutionOfferCatalog();
        marketcatalog = new MarketCatalog();
        channelcatalog = new ChannelCatalog();
        persondirectory = new PersonDirectory();
        customerdirectory = new CustomerDirectory(this);
        salespersondirectory = new SalesPersonDirectory(this);
        useraccountdirectory = new UserAccountDirectory();
        marketingpersondirectory = new MarketingPersonDirectory(this);
        employeedirectory = new EmployeeDirectory(this);
        advertisementcatalog = new AdvertisementCatalog();
        solutionoffercatalog = new SolutionOfferCatalog();
    }

    public int getSalesVolume() {
        return masterorderlist.getSalesVolume();

    }

    public String getName() {
        return name;
    }

    public void setMarketcatalog(MarketCatalog marketcatalog) {
        this.marketcatalog = marketcatalog;
    }

    public MarketCatalog getMarketcatalog() {
        return marketcatalog;
    }

    public AdvertisementCatalog getAdvertisementcatalog() {
        return advertisementcatalog;
    }

    public void setChannelcatalog(ChannelCatalog channelcatalog) {
        this.channelcatalog = channelcatalog;
    }

    public ChannelCatalog getChannelcatalog() {
        return channelcatalog;
    }

    public void setSolutionOfferCatalog(SolutionOfferCatalog solutionoffercatalog) {
        this.solutionoffercatalog = solutionoffercatalog;
    }

    public SolutionOfferCatalog getSolutionOfferCatalog() {
        return solutionoffercatalog;
    }

    public PersonDirectory getPersonDirectory() {
        return persondirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return useraccountdirectory;
    }

    public MarketingPersonDirectory getMarketingPersonDirectory() {
        return marketingpersondirectory;
    }

    public SupplierDirectory getSupplierDirectory() {
        return suppliers;
    }

    public HashMap<Channel, ChannelSummary> getChannelReports() {
        HashMap<Channel, ChannelSummary> channelToSummary = new HashMap<Channel, ChannelSummary>();
        ArrayList<Channel> channels = getChannelcatalog().getChannels();
        Set<Product> products = new HashSet<Product>();
        ArrayList<SolutionOffer> solutionOffers = getSolutionOfferCatalog().getSolutionOffers();
        AdvertisementCatalog advertisementCatalog = getAdvertisementcatalog();
        for (SolutionOffer so : solutionOffers) {
            products.addAll(so.getProducts());
        }
        for (Channel channel : channels) {
            ChannelSummary channelSummary = new ChannelSummary(channel);
            channelToSummary.put(channel, channelSummary);
            int advertisementCost = advertisementCatalog.getCostByChannel(channel);
            channelSummary.addAdvertisementCost(advertisementCost);
        }

        for (Product product : products) {
            ArrayList<OrderItem> orderItems = product.getOrderItems();
            for (OrderItem oi : orderItems) {
                Order order = oi.getOrder();
                Channel channel = order.getMarketChannelAssignment().getChannel();
                channelToSummary.get(channel).addOrderItem(oi);
            }
        }
        return channelToSummary;
    }

    public HashMap<Market, MarketSummary> getMarketReports() {
        HashMap<Market, MarketSummary> marketToSummary = new HashMap<Market, MarketSummary>();
        ArrayList<Market> markets = getMarketcatalog().getMarkets();
        Set<Product> products = new HashSet<Product>();
        ArrayList<SolutionOffer> solutionOffers = getSolutionOfferCatalog().getSolutionOffers();
        AdvertisementCatalog advertisementCatalog = getAdvertisementcatalog();
        for (SolutionOffer so : solutionOffers) {
            products.addAll(so.getProducts());
        }
        for (Market market : markets) {
            MarketSummary marketSummary = new MarketSummary(market);
            marketToSummary.put(market, marketSummary);
            int advertisementCost = advertisementCatalog.getCostByMarket(market);
            marketSummary.addAdvertisementCost(advertisementCost);
        }

        for (Product product : products) {
            ArrayList<OrderItem> orderItems = product.getOrderItems();
            for (OrderItem oi : orderItems) {
                Order order = oi.getOrder();
                Market market = order.getMarketChannelAssignment().getMarket();
                marketToSummary.get(market).addOrderItem(oi);
            }
        }
        return marketToSummary;
    }

    public ProductsReport getSupplierPerformanceReport(String n) {
        Supplier supplier = suppliers.findSupplier(n);
        if (supplier == null) {
            return null;
        }
        return supplier.prepareProductsReport();

    }

    public ArrayList<ProductSummary> getSupplierProductsAlwaysAboveTarget(String n) {

        ProductsReport productsreport = getSupplierPerformanceReport(n);
        return productsreport.getProductsAlwaysAboveTarget();

    }

    public int getHowManySupplierProductsAlwaysAboveTarget(String n) {
        ProductsReport productsreport = getSupplierPerformanceReport(n); // see above
        int i = productsreport.getProductsAlwaysAboveTarget().size(); // return size of the arraylist
        return i;
    }

    public CustomerDirectory getCustomerDirectory() {
        return customerdirectory;
    }

    public SalesPersonDirectory getSalesPersonDirectory() {
        return salespersondirectory;
    }

    public MasterOrderList getMasterOrderList() {
        return masterorderlist;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeedirectory;
    }

    public void printShortInfo() {
        System.out.println("Checking what's inside the business hierarchy.");
        suppliers.printShortInfo();
        customerdirectory.printShortInfo();
        masterorderlist.printShortInfo();
    }

}
