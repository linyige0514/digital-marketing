package model.ProductManagement;

import java.util.Comparator;

public class ProductSummaryComparator implements Comparator<ProductSummary> {

    @Override
    public int compare(ProductSummary o1, ProductSummary o2) {
        return (-1) * Integer.compare(o1.getSalesRevenues(), o2.getSalesRevenues());
    }
    
}
