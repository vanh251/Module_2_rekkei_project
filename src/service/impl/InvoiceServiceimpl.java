package service.impl;

import dao.IInvoiceDao;
import dao.impl.InvoiceDaoImpl;
import model.Invoice;
import service.IInvoiceService;

import java.time.LocalDate;
import java.util.List;

public class InvoiceServiceimpl implements IInvoiceService {
    private final IInvoiceDao invoiceDao = new InvoiceDaoImpl();

    @Override
    public List<Invoice> displayAllInvoices() {
        return invoiceDao.displayAllInvoices();
    }

    @Override
    public void addInvoice(Invoice invoice) {
        invoiceDao.addInvoice(invoice);
    }

    @Override
    public List<Invoice> findInvoiceByCustomerId(int customerId) {
        return invoiceDao.findInvoicesByCustomerId(customerId);
    }

    @Override
    public List<Invoice> findInvoiceByDate(LocalDate date) {
        return invoiceDao.findInvoicesByDate(date);
    }
}
