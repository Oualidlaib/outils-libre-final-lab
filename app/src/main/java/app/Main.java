package app;

import java.util.ArrayList;
import java.util.List;

/**
 * CLI entry point for PricingEngine.
 *
 * Usage:
 *   java -cp <classpath> app.Main <prices> <quantities> <customerType> [promoCode]
 *
 * Arguments:
 *   prices       — comma-separated doubles,  e.g. 100.0,50.0
 *   quantities   — comma-separated integers, e.g. 2,1
 *   customerType — REGULAR or VIP
 *   promoCode    — optional; omit or pass "null" for no promo
 *
 * Example:
 *   java -cp app/build/classes/java/main app.Main 100.0 1 VIP SAVE20
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: app.Main <prices> <quantities> <customerType> [promoCode]");
            System.exit(1);
        }

        List<Double>  prices     = parseDoubles(args[0]);
        List<Integer> quantities = parseInts(args[1]);
        String        customerType = args[2];
        String        promoCode    = (args.length >= 4 && !args[3].equalsIgnoreCase("null"))
                                     ? args[3]
                                     : null;

        PricingRequest request = new PricingRequest(prices, quantities, customerType, promoCode);
        new PricingEngine().calculate(request);
    }

    private static List<Double> parseDoubles(String csv) {
    List<Double> list = new ArrayList<>();
    if (csv == null || csv.isBlank()) return list;
    for (String s : csv.split(",")) list.add(Double.parseDouble(s.trim()));
    return list;
    }

    private static List<Integer> parseInts(String csv) {
        List<Integer> list = new ArrayList<>();
        if (csv == null || csv.isBlank()) return list;
        for (String s : csv.split(",")) list.add(Integer.parseInt(s.trim()));
        return list;
    }
}