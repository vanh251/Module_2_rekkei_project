package dao;

import model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IProductDao {
    List<Product> showAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    Product findProductById(int id);
    void deleteProduct(int id);
    List<Product> findProductByBrand(String brand);
    List<Product> findProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findProductByStockAvailability(String name, int stock);
}
