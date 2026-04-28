package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class PricingEngineTest {

    private final PricingEngine engine = new PricingEngine();
    private final List<Double> standardPrice = Arrays.asList(100.0);
    private final List<Integer> standardQty = Arrays.asList(1);
    
    private PricingRequest request(List<Double> prices, List<Integer> quantities,
                                   String customerType, String promoCode) {
        return new PricingRequest(prices, quantities, customerType, promoCode);
    }


    @Test
    void testTotalSummationWithMultipleItems() {
        // Item 1: 50.0 * 2 = 100.0
        // Item 2: 25.0 * 2 = 50.0
        // Total Sum = 150.0
        // Regular + No Discount = 0% discount ($0).
        // Subtotal = 150. Tax 8% ($12). Total = 162.0
        List<Double>  prices     = Arrays.asList(50.0, 25.0);
        List<Integer> quantities = Arrays.asList(2, 2);

        double result = engine.calculate(request(prices, quantities, "REGULAR", null));

        assertEquals(162.0, result, 0.001);
    }

    @Test
    void testRegularCustomerWithNoDiscount() {
        // (100 * 1) = 100. Regular + No Discount = 0% discount ($0).
        // Subtotal = 100. Tax 8% ($8). Total = 108.0
        double result = engine.calculate(request(standardPrice, standardQty, "REGULAR", null));
        assertEquals(108.0, result, 0.001);
    }

    @Test
    void testRegularCustomerWithSAVE10() {
        // (100 * 1) = 100. Regular + SAVE10 = 10% discount ($10).
        // Subtotal = 90. Tax 8% ($7.2). Total = 97.2
        double result = engine.calculate(request(standardPrice, standardQty, "REGULAR", "SAVE10"));
        assertEquals(97.2, result, 0.001);
    }

    @Test
    void testRegularCustomerWithSAVE20() {
        // (100 * 1) = 100. Regular + SAVE20 = 20% discount ($20).
        // Subtotal = 80. Tax 8% ($6.4). Total = 86.4
        double result = engine.calculate(request(standardPrice, standardQty, "REGULAR", "SAVE20"));
        assertEquals(86.4, result, 0.001);
    }

    @Test
    void testVIPCustomerWithNoDiscount() {
        // (100 * 1) = 100. VIP + No Promo = 15% discount ($15).
        // Subtotal = 85. Tax 8% ($6.8). Total = 91.8
        double result = engine.calculate(request(standardPrice, standardQty, "VIP", null));
        assertEquals(91.8, result, 0.001);
    }

    @Test
    void testVIPCustomerWithSAVE10() {
        // (100 * 1) = 100. VIP (15%) + SAVE10 (10%) = 25% discount ($25).
        // Subtotal = 75. Tax 8% ($6). Total = 81.0
        double result = engine.calculate(request(standardPrice, standardQty, "VIP", "SAVE10"));
        assertEquals(81.0, result, 0.001);
    }

    @Test
    void testVIPCustomerWithSAVE20() {
        // (100 * 1) = 100. VIP (15%) + SAVE20 (20%) = 35% discount ($35).
        // Subtotal = 65. Tax 8% ($5.2). Total = 70.2
        double result = engine.calculate(request(standardPrice, standardQty, "VIP", "SAVE20"));
        assertEquals(70.2, result, 0.001);
    }

    @Test
    void testEmptyProductList() {
        // Total = 0. Regular + No Promo = 0% discount.
        // Subtotal = 0. Tax 8% ($0). Total = 0.0
        List<Double>  prices     = Arrays.asList();
        List<Integer> quantities = Arrays.asList();

        double result = engine.calculate(request(prices, quantities, "REGULAR", null));

        assertEquals(0.0, result, 0.001);
    }

    @Test
    void testNullPromoCode() {
        // (100 * 1) = 100. Regular + null = 0% discount ($0).
        // Subtotal = 100. Tax 8% ($8). Total = 108.0
        double result = engine.calculate(request(standardPrice, standardQty, "REGULAR", null));
        assertEquals(108.0, result, 0.001);
    }
}
