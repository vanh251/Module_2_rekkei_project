package presentation;

import model.Product;
import service.IProductService;

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
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
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
        if (products == null){
            System.out.println("Không có sản phẩm nào trong cửa hàng");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
        System.out.println("========================================");
    }
}
