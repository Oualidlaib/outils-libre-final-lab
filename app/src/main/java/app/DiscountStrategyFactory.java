package app;

import java.util.Map;

public class DiscountStrategyFactory {

    private static final Map<String, DiscountStrategy> STRATEGIES = Map.of(
        "REGULAR", new RegularDiscountStrategy(),
        "VIP",     new VipDiscountStrategy()
    );

    public static DiscountStrategy resolve(String customerType) {
        return STRATEGIES.getOrDefault(customerType, (total, promo) -> 0);
    }
}