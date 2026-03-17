package model;

import java.math.BigDecimal;

public class InvoiceItem {
    private int id;
    private int invoiceId;
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;

    public InvoiceItem() {}

    public InvoiceItem(int invoiceId, int productId, String productName, int quantity, BigDecimal unitPrice) {
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public InvoiceItem(int id, int invoiceId, int productId, String productName, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public String toString() {
        return String.format("| %-5d| %-29s| %8d| %16.2f | %16.2f |",
                productId, productName, quantity, unitPrice, getSubtotal());
    }
}
