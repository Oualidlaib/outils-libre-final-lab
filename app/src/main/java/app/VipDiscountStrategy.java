// VipDiscountStrategy.java
package app;

public class VipDiscountStrategy implements DiscountStrategy {

    private static final String PROMO_SAVE10 = "SAVE10";
    private static final String PROMO_SAVE20 = "SAVE20";

    private static final double DISCOUNT_VIP_BASE        = 0.15;
    private static final double DISCOUNT_VIP_WITH_SAVE10 = 0.25;
    private static final double DISCOUNT_VIP_WITH_SAVE20 = 0.35;

    @Override
    public double apply(double total, String promoCode) {
        if (PROMO_SAVE20.equals(promoCode)) return total * DISCOUNT_VIP_WITH_SAVE20;
        if (PROMO_SAVE10.equals(promoCode)) return total * DISCOUNT_VIP_WITH_SAVE10;
        return total * DISCOUNT_VIP_BASE;
    }
}