package dao.impl;

import dao.IInvoiceDao;
import model.Invoice;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDaoImpl implements IInvoiceDao {
    @Override
    public List<Invoice> displayAllInvoices() {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from invoice");
                ) {
            List<Invoice> invoiceList = new ArrayList<>();
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
            System.out.println("Lỗi khi truy vấn hóa đơn: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void addInvoice(Invoice invoice) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("insert into invoice(customer_id, total_amount) values(?, ?)");
                ) {
            pre.setInt(1, invoice.getCustomerId());
            pre.setBigDecimal(2, invoice.getTotalAmount());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    @Override
    public List<Invoice> findInvoicesByCustomerId(int customerId) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from invoice where customer_id = ?");
                ) {
            pre.setInt(1, customerId);
            List<Invoice> invoiceList = new ArrayList<>();
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
            System.out.println("Lỗi khi truy vấn hóa đơn theo ID khách hàng: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Invoice> findInvoicesByDate(LocalDate date) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from invoice where date(created_at) = ?");
                ) {
            pre.setDate(1, java.sql.Date.valueOf(date));
            List<Invoice> invoiceList = new ArrayList<>();
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
            System.out.println("Lỗi khi truy vấn hóa đơn theo ngày: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
