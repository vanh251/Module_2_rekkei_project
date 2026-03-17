package dao.impl;

import dao.IInvoiceItemDao;
import model.InvoiceItem;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemDaoImpl implements IInvoiceItemDao {

    @Override
    public void addInvoiceItem(InvoiceItem item) {
        String sql = "insert into invoice_details(invoice_id, product_id, quantity, unit_price) values (?, ?, ?, ?)";
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setInt(1, item.getInvoiceId());
            pre.setInt(2, item.getProductId());
            pre.setInt(3, item.getQuantity());
            pre.setBigDecimal(4, item.getUnitPrice());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm chi tiết hóa đơn: " + e.getMessage());
        }
    }

    @Override
    public List<InvoiceItem> findItemsByInvoiceId(int invoiceId) {
        String sql = """
                select d.id, d.invoice_id, d.product_id, p.name, d.quantity, d.unit_price
                from invoice_details d
                join product p ON d.product_id = p.id
                where d.invoice_id = ?
                """;
        List<InvoiceItem> items = new ArrayList<>();
        try (
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ) {
            pre.setInt(1, invoiceId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                items.add(new InvoiceItem(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getBigDecimal(6)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn chi tiết hóa đơn: " + e.getMessage());
        }
        return items;
    }
}
