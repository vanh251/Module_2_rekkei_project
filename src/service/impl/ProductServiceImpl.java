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
}
