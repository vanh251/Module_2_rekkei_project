package presentation;

import model.Admin;
import service.IAdminService;
import service.impl.AdminServiceImpl;

import java.util.Scanner;

public class AdminView {
    private static final IAdminService adminService = new AdminServiceImpl();
    private static Admin adminLogin = null;
    public static void showMenuLogin(Scanner sc){
        System.out.println("""
                ========== Đăng nhập quản trị ==========
                """);
        System.out.print("Tài khoản: ");
        String username = sc.nextLine();
        System.out.print("Mật khẩu: ");
        String password = sc.nextLine();
        System.out.println("========================================");
        Admin admin = adminService.login(username, password);
        if(admin!=null){
            System.out.println("Đăng nhập thành công");
            adminLogin = admin;
        }else{
            System.out.println("Thông tin đăng nhập không đúng, vui lòng thử lại");
            showMenuLogin(sc);
        }
    }

}
