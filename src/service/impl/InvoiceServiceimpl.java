package service.impl;

import dao.IInvoiceDao;
import dao.impl.InvoiceDaoImpl;
import model.Invoice;
import service.IInvoiceService;

import java.util.ArrayList;

public class InvoiceServiceimpl implements IInvoiceService {
    private static final IInvoiceDao invoiceDao = new InvoiceDaoImpl();

    @Override
    public ArrayList<Invoice> displayAllInvoices() {
        return invoiceDao.displayAllInvoices();
    }

    @Override
    public void addInvoice(Invoice invoice) {

    }
}
