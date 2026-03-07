package dao.impl;

import dao.IProductDao;
import model.Product;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDaoImpl implements IProductDao {
    @Override
    public ArrayList<Product> showAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from product");
                ) {
            ResultSet rs = pre.executeQuery();
            while (rs.next()){
                productList.add(new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBigDecimal(4),
                        rs.getInt(5)
                ));
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addProduct(Product product) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("insert into product(name, brand, price, stock) values (?,?,?,?)");
                ) {
            pre.setString(1,product.getName());
            pre.setString(2,product.getBrand());
            pre.setBigDecimal(3,product.getPrice());
            pre.setInt(4,product.getStock());
            pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
