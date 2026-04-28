package app;

import java.util.List;

public class PricingEngine {

    // Constants 
    private static final double TAX_RATE = 0.08;

    private static final String CUSTOMER_REGULAR = "REGULAR";
    private static final String CUSTOMER_VIP     = "VIP";

    private static final String PROMO_SAVE10 = "SAVE10";
    private static final String PROMO_SAVE20 = "SAVE20";

    private static final double DISCOUNT_VIP_BASE          = 0.15;
    private static final double DISCOUNT_SAVE10            = 0.10;
    private static final double DISCOUNT_SAVE20            = 0.20;
    private static final double DISCOUNT_VIP_WITH_SAVE10   = 0.25;
    private static final double DISCOUNT_VIP_WITH_SAVE20   = 0.35;

    public double calculate(List<Double> prices, List<Integer> quantities,
                            String customerType, String promoCode) {

        double total = 0;
        for (int i = 0; i < prices.size(); i++) {
            total += prices.get(i) * quantities.get(i);
        }

        double discount = 0;

        if (customerType.equals(CUSTOMER_REGULAR)) {
            if (promoCode != null) {
                if (promoCode.equals(PROMO_SAVE10)) {
                    discount = DISCOUNT_SAVE10;
                } else if (promoCode.equals(PROMO_SAVE20)) {
                    discount = DISCOUNT_SAVE20;
                }
            }
        } else if (customerType.equals(CUSTOMER_VIP)) {
            discount = DISCOUNT_VIP_BASE;
            if (promoCode != null) {
                if (promoCode.equals(PROMO_SAVE20)) {
                    discount = DISCOUNT_VIP_WITH_SAVE20;
                } else if (promoCode.equals(PROMO_SAVE10)) {
                    discount = DISCOUNT_VIP_WITH_SAVE10;
                }
            }
        }

        double discountAmount = total * discount;
        double subtotal       = total - discountAmount;
        double taxAmount      = subtotal * TAX_RATE;
        double finalPrice     = subtotal + taxAmount;

        System.out.println("--- Invoice ---");
        System.out.println("Subtotal: "  + total);
        System.out.println("Discount: "  + discountAmount);
        System.out.println("Tax: "       + taxAmount);
        System.out.println("Final: "     + finalPrice);

        return finalPrice;
    }
}
