package dao.impl;

import dao.IInvoiceDao;
import model.Invoice;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDaoImpl implements IInvoiceDao {
    @Override
    public ArrayList<Invoice> displayAllInvoices() {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from invoice");
                ) {
            ArrayList<Invoice> invoiceList = new ArrayList<>();
            ResultSet rs = pre.executeQuery();
            while (rs.next()){
                invoiceList.add(new Invoice(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getTimestamp(3).toLocalDateTime(),
                        rs.getBigDecimal(4)
                ));
            }
            return invoiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addInvoice(Invoice invoice) {

    }
}
