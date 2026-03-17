package service.impl;

import dao.IInvoiceItemDao;
import dao.impl.InvoiceItemDaoImpl;
import model.InvoiceItem;
import service.IInvoiceItemService;

import java.util.List;

public class InvoiceItemServiceImpl implements IInvoiceItemService {
    private final IInvoiceItemDao invoiceItemDao = new InvoiceItemDaoImpl();

    @Override
    public void addInvoiceItem(InvoiceItem item) {
        invoiceItemDao.addInvoiceItem(item);
    }

    @Override
    public List<InvoiceItem> findItemsByInvoiceId(int invoiceId) {
        return invoiceItemDao.findItemsByInvoiceId(invoiceId);
    }
}
