package service;

import model.Product;

import java.util.ArrayList;

public interface IProductService {
    ArrayList<Product> showAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    Product findProductById(int id);
    void deleteProduct(int id);
}
