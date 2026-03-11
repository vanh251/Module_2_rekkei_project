package dao;

import model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface IProductDao {
    ArrayList<Product> showAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    Product findProductById(int id);
    void deleteProduct(int id);
    ArrayList<Product> findProductByBrand(String brand);
    ArrayList<Product> findProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    ArrayList<Product> findProductByStockAvailability(String name, int stock);
}
