package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private int id;
    private int customerId;
    private String customerName;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private List<InvoiceItem> items = new ArrayList<>();

    public Invoice() {
    }

    public Invoice(int id, int customerId, String customerName, LocalDateTime createdAt, BigDecimal totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }

    public Invoice(int customerId, BigDecimal totalAmount) {
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        String custDisplay = (customerName != null && !customerName.isEmpty()) ? customerName : ("ID:" + customerId);
        String dateStr = (createdAt != null)
                ? createdAt.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                : "";
        return String.format("|%-5d|%-22s|%-22s|%16.2f |", id, custDisplay, dateStr, totalAmount);
    }
}
