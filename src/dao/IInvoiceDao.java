package dao;

import model.Invoice;

import java.util.ArrayList;

public interface IInvoiceDao {
    ArrayList<Invoice> displayAllInvoices();
    void addInvoice(Invoice invoice);
}
