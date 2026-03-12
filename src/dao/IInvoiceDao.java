package dao;

import model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceDao {
    List<Invoice> displayAllInvoices();
    void addInvoice(Invoice invoice);
    List<Invoice> findInvoicesByCustomerId(int customerId);
    List<Invoice> findInvoicesByDate(LocalDate date);
}
