package app;

import java.util.List;

public class PricingEngine {

    
    public double calculate(List<Double> prices, List<Integer> quantities, String customerType, String promoCode) {
        double total = 0;

        for (int i = 0; i < prices.size(); i++) {
            total += prices.get(i) * quantities.get(i);
        }

        double discount = 0;
        
        // Nested IF statements
        if (customerType.equals("REGULAR")) {
            if (promoCode != null) {
                if (promoCode.equals("SAVE10")) {
                    discount = total * 0.1;
                } else if (promoCode.equals("SAVE20")) {
                    discount = total * 0.2;
                }
            }
        } else if (customerType.equals("VIP")) {

            // VIPs get 15% discount automatically
            discount = total * 0.15;
            if (promoCode != null) {
                if (promoCode.equals("SAVE20")) {
                    // VIP 15% + 20% bonus = 35%
                    discount = total * 0.35; 
                } else if (promoCode.equals("SAVE10")) {
                    // VIP 15% + 10% bonus = 25% 
                    discount = total * 0.25; 
                }
            }
        }

        double subtotal = total - discount;
        
        double tax = subtotal * 0.08; 
        
        double finalPrice = subtotal + tax;

        // Printing the invoice
        System.out.println("--- Invoice ---");
        System.out.println("Subtotal: " + total);
        System.out.println("Discount: " + discount);
        System.out.println("Tax: " + tax);
        System.out.println("Final: " + finalPrice);

        return finalPrice;
    }
}
