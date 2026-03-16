package service;

import model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> displayAllInvoices();
    void addInvoice(Invoice invoice);
    int addInvoiceReturnId(Invoice invoice);
    List<Invoice> findInvoiceByCustomerId(int customerId);
    List<Invoice> findInvoiceByDate(LocalDate date);
    Double getRevenueByDate(LocalDate date);
    Double getRevenueByMonth(int month, int year);
    Double getRevenueByYear(int year);
}
