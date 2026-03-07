package dao;

import model.Product;

import java.util.ArrayList;

public interface IProductDao {
    ArrayList<Product> showAllProducts();
    void addProduct(Product product);
}
