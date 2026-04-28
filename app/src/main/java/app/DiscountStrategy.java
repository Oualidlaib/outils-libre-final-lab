package app;

public interface DiscountStrategy {
    double apply(double total, String promoCode);
}