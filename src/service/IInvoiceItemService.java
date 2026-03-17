package service;

import model.InvoiceItem;

import java.util.List;

public interface IInvoiceItemService {
    void addInvoiceItem(InvoiceItem item);
    List<InvoiceItem> findItemsByInvoiceId(int invoiceId);
}
