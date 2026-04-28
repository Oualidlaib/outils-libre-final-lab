package app;

public class Invoice {
    public final double total;
    public final double discountAmount;
    public final double taxAmount;
    public final double finalPrice;

    public Invoice(double total, double discountAmount,
        double taxAmount, double finalPrice) {
        this.total     = total;
        this.discountAmount = discountAmount;
        this.taxAmount      = taxAmount;
        this.finalPrice     = finalPrice;
    }
}