package presentation;

import model.Product;
import service.IProductService;
import utils.InputValidator;

import java.math.BigDecimal;
import java.util.List;
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
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 8);
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
            }
        }
    }

    public static void showAllProducts(Scanner sc){
        System.out.println("""
                ========== Danh sách sản phẩm ==========
                """);
        List<Product> products = productService.showAllProducts();
        if (products.isEmpty()){
            System.out.println("Không có sản phẩm nào trong cửa hàng");
        } else {
            printProductTable(products);
        }
    }

    public static void displayAllProducts() {
        System.out.println("--- Danh sách sản phẩm ---");
        List<Product> products = productService.showAllProducts();
        if (products.isEmpty()){
            System.out.println("Không có sản phẩm nào.");
        } else {
            printProductTable(products);
        }
    }

    // Helper: in bảng sản phẩm
    private static void printProductTable(List<Product> products) {
        System.out.println("+-----+---------------------------+-----------------+----------------+-------+");
        System.out.printf("|%-5s|%-27s|%-17s|%16s|%-7s|\n", "ID", "Tên sản phẩm", "Thương hiệu", "Giá (đ)", "Tồn kho");
        System.out.println("+-----+---------------------------+-----------------+----------------+-------+");
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println("+-----+---------------------------+-----------------+----------------+-------+");
    }

    public static void addNewProduct(Scanner sc){
        System.out.println("""
                ========== Thêm mới sản phẩm ==========
                """);
        String name = InputValidator.getValidString(sc, "Tên sản phẩm: ", 2, 50);
        String brand = InputValidator.getValidString(sc, "Thương hiệu: ", 2, 30);
        BigDecimal price = InputValidator.getPositiveBigDecimal(sc, "Giá: ");
        int stock = InputValidator.getPositiveInt(sc, "Số lượng tồn kho: ");
        
        Product product = new Product(name, brand, price, stock);
        productService.addProduct(product);
        System.out.println("Thêm sản phẩm thành công");
    }

    public static void updateProduct(Scanner sc){
        System.out.println("""
                ========== Cập nhật thông tin sản phẩm ==========
                """);
        showAllProducts(sc);
        int id = InputValidator.getPositiveInt(sc, "Nhập ID sản phẩm cần cập nhật: ");
        Product product = productService.findProductById(id);
        if(product == null){
            System.out.println("Không tìm thấy sản phẩm có ID: "+ id);
        }else {
            System.out.println("Thông tin sản phẩm có ID "+ id);
            System.out.println(product);
            System.out.println("Nhập thông tin mới cho sản phẩm (để trống nếu không muốn cập nhật trường đó)");
            System.out.print("Tên sản phẩm: ");
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) {
                product.setName(name);
            }
            System.out.print("Thương hiệu: ");
            String brand = sc.nextLine().trim();
            if (!brand.isEmpty()) {
                product.setBrand(brand);
            }
            System.out.print("Giá: ");
            String priceInput = sc.nextLine().trim();
            if (!priceInput.isEmpty()) {
                try {
                    BigDecimal price = new BigDecimal(priceInput);
                    product.setPrice(price);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Giá không hợp lệ, bỏ qua cập nhật giá");
                }
            }
            System.out.print("Số lượng tồn kho: ");
            String stockInput = sc.nextLine().trim();
            if (!stockInput.isEmpty()) {
                try {
                    int stock = Integer.parseInt(stockInput);
                    product.setStock(stock);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Số lượng không hợp lệ, bỏ qua cập nhật số lượng");
                }
            }
            productService.updateProduct(product);
            System.out.println("Cập nhật sản phẩm thành công");
        }
    }

    public static void deleteProduct(Scanner sc){
        System.out.println("""
                ========== Xoá sản phẩm ==========
                """);
        showAllProducts(sc);
        int id = InputValidator.getPositiveInt(sc, "Nhập ID sản phẩm cần xoá: ");
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
                System.out.println("Đã hủy xoá sản phẩm");
            }
        }
    }

    public static void findProductByBrand(Scanner sc){
        System.out.println("""
                ========== Tìm kiếm sản phẩm theo thương hiệu ==========
                """);
        String brand = InputValidator.getNonEmptyString(sc, "Nhập thương hiệu cần tìm: ");
        List<Product> products = productService.findProductByBrand(brand);
        if (products.isEmpty()){
            System.out.println("Không tìm thấy sản phẩm nào có thương hiệu: "+ brand);
        } else {
            System.out.println("Danh sách sản phẩm có thương hiệu "+ brand +":");
            printProductTable(products);
        }
    }

    public static void findProductByPriceRange(Scanner sc){
        System.out.println("""
                ========== Tìm kiếm sản phẩm theo khoảng giá ==========
                """);
        BigDecimal minPrice = InputValidator.getPositiveBigDecimal(sc, "Nhập giá tối thiểu: ");
        BigDecimal maxPrice = InputValidator.getPositiveBigDecimal(sc, "Nhập giá tối đa: ");
        List<Product> products = productService.findProductByPriceRange(minPrice, maxPrice);
        if (products.isEmpty()){
            System.out.println("Không tìm thấy sản phẩm nào trong khoảng giá: "+ minPrice + " - " + maxPrice);
        } else {
            System.out.println("Danh sách sản phẩm trong khoảng giá "+ minPrice + " - " + maxPrice +":");
            printProductTable(products);
        }
    }

    public static void findProductByStockAvailability(Scanner sc){
        System.out.println("""
                ========== Tìm kiếm sản phẩm theo tồn kho ==========
                """);
        String name = InputValidator.getNonEmptyString(sc, "Nhập tên sản phẩm cần tìm: ");
        int stock = InputValidator.getPositiveInt(sc, "Nhập số lượng tồn kho tối thiểu: ");
        List<Product> products = productService.findProductByStockAvailability(name, stock);
        if (products.isEmpty()){
            System.out.println("Không tìm thấy sản phẩm nào có tên "+ name +" và tồn kho >= "+ stock);
        } else {
            System.out.println("Danh sách sản phẩm có tên "+ name +" và tồn kho >= "+ stock +":");
            printProductTable(products);
        }
    }
}
