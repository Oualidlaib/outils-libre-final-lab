package app;

public class RegularDiscountStrategy implements DiscountStrategy {

    private static final String PROMO_SAVE10 = "SAVE10";
    private static final String PROMO_SAVE20 = "SAVE20";

    private static final double DISCOUNT_SAVE10 = 0.10;
    private static final double DISCOUNT_SAVE20 = 0.20;

    @Override
    public double apply(double total, String promoCode) {
        if (PROMO_SAVE10.equals(promoCode)) return total * DISCOUNT_SAVE10;
        if (PROMO_SAVE20.equals(promoCode)) return total * DISCOUNT_SAVE20;
        return 0;
    }
}