package service.impl;

import dao.IProductDao;
import dao.impl.ProductDaoImpl;
import model.Product;
import service.IProductService;

import java.util.ArrayList;

public class ProductServiceImpl implements IProductService {
    private static final IProductDao productDao = new ProductDaoImpl();
    @Override
    public ArrayList<Product> showAllProducts() {
        ArrayList<Product> productList = productDao.showAllProducts();
        if (productList.isEmpty()){
            return null;
        } else {
            return productList;
        }
    }

    @Override
    public void addProduct(Product product) {
        if (product == null){
            throw  new IllegalArgumentException("Sản phẩm không được null");
        }else {
            productDao.addProduct(product);
        }
    }

    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public Product findProductById(int id) {
        return productDao.findProductById(id);
    }

    @Override
    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }
}
