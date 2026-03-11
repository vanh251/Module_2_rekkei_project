package presentation;

import model.Product;
import service.IProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import static presentation.AdminView.showMainMenu;

public class ProductView {
    private static final IProductService productService = new service.impl.ProductServiceImpl();

    public static void showPhoneManagementMenu(Scanner sc){
        while(true){
            System.out.println("""
                    ========== Quản lý sản phẩm ==========
                    1. Hiển thị danh sách sản phẩm
                    2. Thêm mới sản phẩm
                    3. Cập nhật thông tin sản phẩm
                    4. Xoá sản phẩm theo ID
                    5. Tìm kiếm theo Brand
                    6. Tìm kiếm theo khoảng giá
                    7. Tìm kiếm theo tồn kho
                    8. Quay lại menu chính
                    ======================================
                    """);
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    showAllProducts(sc);
                    break;
                case 2:
                    addNewProduct(sc);
                    break;
                case 3:
                    updateProduct(sc);
                    break;
                case 4:
                    deleteProduct(sc);
                    break;
                case 5:
                    findProductByBrand(sc);
                    break;
                case 6:
                    findProductByPriceRange(sc);
                    break;
                case 7:
                    findProductByStockAvailability(sc);
                    break;
                case 8:
                    showMainMenu(sc);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại");
                    break;
            }
        }
    }

    public static void showAllProducts(Scanner sc){
        System.out.println("""
                ========== Danh sách sản phẩm ==========
                """);
        ArrayList<Product> products = productService.showAllProducts();
        if (products.isEmpty()){
            System.out.println("Không có sản phẩm nào trong cửa hàng");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
        System.out.println("========================================");
    }

    public static void addNewProduct(Scanner sc){
        System.out.println("""
                ========== Thêm mới sản phẩm ==========
                """);
        System.out.print("Tên sản phẩm: ");
        String name = sc.nextLine();
        System.out.print("Thương hiệu: ");
        String brand = sc.nextLine();
        System.out.print("Giá: ");
        BigDecimal price = sc.nextBigDecimal();
        sc.nextLine();
        System.out.print("Số lượng tồn kho: ");
        int stock = Integer.parseInt(sc.nextLine());
        Product product = new Product(name, brand, price, stock);
        productService.addProduct(product);
        System.out.println("Thêm sản phẩm thành công");
    }

    public static void updateProduct(Scanner sc){
        System.out.println("""
                ========== Cập nhật thông tin sản phẩm ==========
                """);
        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
        int id = Integer.parseInt(sc.nextLine());
        Product product = productService.findProductById(id);
        if(product == null){
            System.out.println("Không tìm thấy sản phẩm có ID: "+ id);
        }else {
            System.out.println("Thông tin sản phẩm có ID "+ id);
            System.out.println(product);
            System.out.println("Nhập thông tin mới cho sản phẩm (để trống nếu không muốn cập nhật trường đó)");
            System.out.print("Tên sản phẩm: ");
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                product.setName(name);
            }
            System.out.print("Thương hiệu: ");
            String brand = sc.nextLine();
            if (!brand.isEmpty()) {
                product.setBrand(brand);
            }
            System.out.print("Giá: ");
            String priceInput = sc.nextLine();
            if (!priceInput.isEmpty()) {
                BigDecimal price = new BigDecimal(priceInput);
                product.setPrice(price);
            }
            System.out.print("Số lượng tồn kho: ");
            String stockInput = sc.nextLine();
            if (!stockInput.isEmpty()) {
                int stock = Integer.parseInt(stockInput);
                product.setStock(stock);
            }
            productService.updateProduct(product);
            System.out.println("Cập nhật sản phẩm thành công");
        }
    }

    public static void deleteProduct(Scanner sc){
        System.out.println("""
                ========== Xoá sản phẩm ==========
                """);
        System.out.print("Nhập ID sản phẩm cần xoá: ");
        int id = Integer.parseInt(sc.nextLine());
        Product product = productService.findProductById(id);
        if(product == null){
            System.out.println("Không tìm thấy sản phẩm có ID: "+ id);
        }else {
            System.out.println("Thông tin sản phẩm có ID "+ id);
            System.out.println(product);
            System.out.print("Bạn có chắc muốn xoá sản phẩm này? (Y/N): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("Y")){
                productService.deleteProduct(id);
                System.out.println("Xoá sản phẩm thành công");
            } else {
                System.out.println("Đã huỷ xoá sản phẩm");
            }
        }
    }

    public static void findProductByBrand(Scanner sc){
        System.out.println("""
                ========== Tìm kiếm sản phẩm theo thương hiệu ==========
                """);
        System.out.print("Nhập thương hiệu cần tìm: ");
        String brand = sc.nextLine();
        ArrayList<Product> products = productService.findProductByBrand(brand);
        if (products.isEmpty()){
            System.out.println("Không tìm thấy sản phẩm nào có thương hiệu: "+ brand);
        } else {
            System.out.println("Danh sách sản phẩm có thương hiệu "+ brand +":");
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    public static void findProductByPriceRange(Scanner sc){
        System.out.println("""
                ========== Tìm kiếm sản phẩm theo khoảng giá ==========
                """);
        System.out.print("Nhập giá tối thiểu: ");
        BigDecimal minPrice = sc.nextBigDecimal();
        sc.nextLine();
        System.out.print("Nhập giá tối đa: ");
        BigDecimal maxPrice = sc.nextBigDecimal();
        sc.nextLine();
        ArrayList<Product> products = productService.findProductByPriceRange(minPrice, maxPrice);
        if (products.isEmpty()){
            System.out.println("Không tìm thấy sản phẩm nào trong khoảng giá: "+ minPrice + " - " + maxPrice);
        } else {
            System.out.println("Danh sách sản phẩm trong khoảng giá "+ minPrice + " - " + maxPrice +":");
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    public static void findProductByStockAvailability(Scanner sc){
        System.out.println("""
                ========== Tìm kiếm sản phẩm theo tồn kho ==========
                """);
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String name = sc.nextLine();
        System.out.print("Nhập số lượng tồn kho tối thiểu: ");
        int stock = Integer.parseInt(sc.nextLine());
        ArrayList<Product> products = productService.findProductByStockAvailability(name, stock);
        if (products.isEmpty()){
            System.out.println("Không tìm thấy sản phẩm nào có tên "+ name +" và tồn kho >= "+ stock);
        } else {
            System.out.println("Danh sách sản phẩm có tên "+ name +" và tồn kho >= "+ stock +":");
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }
}
