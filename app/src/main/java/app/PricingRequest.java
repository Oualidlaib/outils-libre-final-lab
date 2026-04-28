package app;

import java.util.List;

public class PricingRequest {
    public final List<Double>  prices;
    public final List<Integer> quantities;
    public final String        customerType;
    public final String        promoCode;

    public PricingRequest(List<Double> prices, List<Integer> quantities,
                          String customerType, String promoCode) {
        this.prices       = prices;
        this.quantities   = quantities;
        this.customerType = customerType;
        this.promoCode    = promoCode;
    }
}