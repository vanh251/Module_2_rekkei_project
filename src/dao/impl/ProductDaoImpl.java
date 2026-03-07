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
}
