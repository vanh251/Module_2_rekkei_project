package presentation;

import model.Admin;
import service.IAdminService;
import service.impl.AdminServiceImpl;
import utils.InputValidator;

import java.util.Scanner;

import static presentation.CustomerView.showCustomerMenu;
import static presentation.InvoiceView.showInvoiceMenu;
import static presentation.ProductView.showPhoneManagementMenu;

public class AdminView {
    private static final IAdminService adminService = new AdminServiceImpl();
    private static Admin adminLogin = null;

    public static void showStartMenu(Scanner sc){
        while (true){
            System.out.println("""
                    ========== Hệ thống quản lý cửa hàng ==========
                    1. Đăng nhập Admin
                    2. Thoát
                    ===============================================
                    """);
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 2);
            switch (choice){
                case 1:
                    AdminView.showMenuLogin(sc);
                    break;
                case 2:
                    System.out.println("Thoát chương trình");
                    System.exit(0);
            }
        }
    }

    public static void showMenuLogin(Scanner sc){
        System.out.println("""
                ========== Đăng nhập quản trị ==========
                """);
        String username = InputValidator.getNonEmptyString(sc, "Tài khoản: ");
        String password = InputValidator.getNonEmptyString(sc, "Mật khẩu: ");
        System.out.println("========================================");
        Admin admin = adminService.login(username, password);
        if(admin!=null){
            System.out.println("Đăng nhập thành công");
            adminLogin = admin;
            showMainMenu(sc);
        }else{
            System.out.println("Thông tin đăng nhập không đúng, vui lòng thử lại");
            showMenuLogin(sc);
        }
    }

    public static void showMainMenu(Scanner sc){
        while (true){
            System.out.println("""
                ========== Menu chính =========
                1. Quản lý sản phẩm điện thoại
                2. Quản lý khách hàng
                3. Quản lý hoá đơn
                4. Thống kê doanh thu
                5. Đăng xuất
                ===============================
                """);
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 5);
            switch (choice){
                case 1:
                    showPhoneManagementMenu(sc);
                    break;
                case 2:
                    showCustomerMenu(sc);
                    break;
                case 3:
                    showInvoiceMenu(sc);
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Đăng xuất thành công");
                    showStartMenu(sc);
                    break;
            }
        }
    }

}
