package dao.impl;

import dao.IProductDao;
import model.Product;
import utils.ConnectionDB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements IProductDao {
    @Override
    public List<Product> showAllProducts() {
        List<Product> productList = new ArrayList<>();
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
            System.out.println("Lỗi khi truy vấn sản phẩm: " + e.getMessage());
            return new ArrayList<>();
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
            System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public void updateProduct(Product product) {
        try (
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("update product set name = ?, brand = ?, price = ?, stock = ? where id = ?");
                ) {
            pre.setString(1,product.getName());
            pre.setString(2,product.getBrand());
            pre.setBigDecimal(3,product.getPrice());
            pre.setInt(4,product.getStock());
            pre.setInt(5,product.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public Product findProductById(int id) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from product where id = ?");
                ) {
            pre.setInt(1,id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()){
                return new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBigDecimal(4),
                        rs.getInt(5)
                );
            } else {
                return null;
                }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm sản phẩm: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteProduct(int id) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("delete from product where id = ?");
                ) {
            pre.setInt(1,id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi xoá sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public List<Product> findProductByBrand(String brand) {
        List<Product> productList = new ArrayList<>();
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from product where brand ilike ?");
                ) {
            pre.setString(1, "%" + brand + "%");
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
            System.out.println("Lỗi khi tìm sản phẩm theo thương hiệu: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> findProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from product where price between ? and ?");
                ) {
            pre.setBigDecimal(1, minPrice);
            pre.setBigDecimal(2, maxPrice);
            ResultSet rs = pre.executeQuery();
            List<Product> productList = new ArrayList<>();
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
            System.out.println("Lỗi khi tìm sản phẩm theo giá: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> findProductByStockAvailability(String name, int stock) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from product where name ilike ? and stock >= ?");
                ) {
            pre.setString(1, "%" + name + "%");
            pre.setInt(2, stock);
            ResultSet rs = pre.executeQuery();
            List<Product> productList = new ArrayList<>();
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
            System.out.println("Lỗi khi tìm sản phẩm theo tồn kho: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
