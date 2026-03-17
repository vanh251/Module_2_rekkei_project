package dao;

import model.InvoiceItem;

import java.util.List;

public interface IInvoiceItemDao {
    void addInvoiceItem(InvoiceItem item);
    List<InvoiceItem> findItemsByInvoiceId(int invoiceId);
}
