package service;

import model.Product;

import java.util.ArrayList;

public interface IProductService {
    ArrayList<Product> showAllProducts();
    void addProduct(Product product);
}
