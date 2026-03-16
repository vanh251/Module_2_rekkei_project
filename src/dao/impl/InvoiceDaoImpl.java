package dao.impl;

import dao.IInvoiceDao;
import model.Invoice;
import utils.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDaoImpl implements IInvoiceDao {

    private Invoice mapRow(ResultSet rs) throws SQLException {
        return new Invoice(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getString("customer_name"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getBigDecimal("total_amount")
        );
    }

    @Override
    public List<Invoice> displayAllInvoices() {
        String sql = """
                SELECT i.id, i.customer_id, c.name AS customer_name, i.created_at, i.total_amount
                FROM invoice i
                LEFT JOIN customer c ON i.customer_id = c.id
                ORDER BY i.id
                """;
        List<Invoice> invoiceList = new ArrayList<>();
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery()
        ) {
            while (rs.next()) {
                invoiceList.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn hóa đơn: " + e.getMessage());
        }
        return invoiceList;
    }

    @Override
    public void addInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoice(customer_id, total_amount) VALUES(?, ?)";
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setInt(1, invoice.getCustomerId());
            pre.setBigDecimal(2, invoice.getTotalAmount());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    @Override
    public int addInvoiceReturnId(Invoice invoice) {
        String sql = "INSERT INTO invoice(customer_id, total_amount) VALUES(?, ?) RETURNING id";
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setInt(1, invoice.getCustomerId());
            pre.setBigDecimal(2, invoice.getTotalAmount());
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public List<Invoice> findInvoicesByCustomerId(int customerId) {
        String sql = """
                SELECT i.id, i.customer_id, c.name AS customer_name, i.created_at, i.total_amount
                FROM invoice i
                LEFT JOIN customer c ON i.customer_id = c.id
                WHERE i.customer_id = ?
                ORDER BY i.id
                """;
        List<Invoice> invoiceList = new ArrayList<>();
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setInt(1, customerId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                invoiceList.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn hóa đơn theo ID khách hàng: " + e.getMessage());
        }
        return invoiceList;
    }

    @Override
    public List<Invoice> findInvoicesByDate(LocalDate date) {
        String sql = """
                SELECT i.id, i.customer_id, c.name AS customer_name, i.created_at, i.total_amount
                FROM invoice i
                LEFT JOIN customer c ON i.customer_id = c.id
                WHERE date(i.created_at) = ?
                ORDER BY i.id
                """;
        List<Invoice> invoiceList = new ArrayList<>();
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setDate(1, Date.valueOf(date));
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                invoiceList.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn hóa đơn theo ngày: " + e.getMessage());
        }
        return invoiceList;
    }

    @Override
    public Double getRevenueByDate(LocalDate date) {
        String sql = "SELECT sum(total_amount) FROM invoice WHERE date(created_at) = ?";
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setDate(1, Date.valueOf(date));
            ResultSet rs = pre.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println("Lỗi khi tính doanh thu theo ngày: " + e.getMessage());
        }
        return 0.0;
    }

    @Override
    public Double getRevenueByMonth(int month, int year) {
        String sql = "SELECT sum(total_amount) FROM invoice WHERE extract(month from created_at) = ? AND extract(year from created_at) = ?";
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setInt(1, month);
            pre.setInt(2, year);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println("Lỗi khi tính doanh thu theo tháng: " + e.getMessage());
        }
        return 0.0;
    }

    @Override
    public Double getRevenueByYear(int year) {
        String sql = "SELECT sum(total_amount) FROM invoice WHERE extract(year from created_at) = ?";
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setInt(1, year);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println("Lỗi khi tính doanh thu theo năm: " + e.getMessage());
        }
        return 0.0;
    }
}
