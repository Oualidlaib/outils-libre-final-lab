// PricingEngine.java
package app;

import java.util.List;

public class PricingEngine {

    private static final double TAX_RATE = 0.08;

    private final InvoicePrinter printer = new InvoicePrinter();

    public double calculate(List<Double> prices, List<Integer> quantities,
                            String customerType, String promoCode) {

        double total     = computeTotal(prices, quantities);

        DiscountStrategy strategy = DiscountStrategyFactory.resolve(customerType);
        double discountAmount     = strategy.apply(total, promoCode);

        double subtotal   = total - discountAmount;
        double taxAmount  = subtotal * TAX_RATE;
        double finalPrice = subtotal + taxAmount;

        printer.print(new Invoice(total, discountAmount, taxAmount, finalPrice));

        return finalPrice;
    }

    private double computeTotal(List<Double> prices, List<Integer> quantities) {
        double total = 0;
        for (int i = 0; i < prices.size(); i++) {
            total += prices.get(i) * quantities.get(i);
        }
        return total;
    }
}