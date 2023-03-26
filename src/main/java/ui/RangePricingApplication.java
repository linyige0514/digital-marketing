/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.MasterOrderReport;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.ProductsReport;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;

/**
 *
 * @author kal bugrara
 */
public class RangePricingApplication {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here


    // 1. Populate the model +

    Business business = ConfigureABusiness.createABusinessAndLoadALotOfData("Xerox", 50, 10, 30, 100, 10);

   // 2. Maybe some interaction with the user (optional)

    
   // 3. Show some analytics (Summerizing, comparing, sorting, grouping data by some criteria)


    //business.printShortInfo();

    SupplierDirectory sd = business.getSupplierDirectory();
    Supplier randomSupplier = sd.pickRandomSupplier();
    ProductCatalog pd = randomSupplier.getProductCatalog();

    
    ProductsReport myFirstReport = pd.generatProductPerformanceReport();
    // myFirstReport.printProductReport();

    MasterOrderList mol = business.getMasterOrderList();
    MasterOrderReport orderReport = mol.generateMasterOrderReport();
    orderReport.printOrderReport();


















  }
}
