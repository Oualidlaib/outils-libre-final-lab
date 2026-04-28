package app;

public class InvoicePrinter {
    public void print(Invoice invoice) {
        System.out.println("--- Invoice ---");
        System.out.println("Subtotal: " + invoice.total);
        System.out.println("Discount: " + invoice.discountAmount);
        System.out.println("Tax: "      + invoice.taxAmount);
        System.out.println("Final: "    + invoice.finalPrice);
    }
}