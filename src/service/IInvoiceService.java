package service;

import model.Invoice;

import java.util.ArrayList;

public interface IInvoiceService {
    ArrayList<Invoice> displayAllInvoices();
    void addInvoice(Invoice invoice);
}
