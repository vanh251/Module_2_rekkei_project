import presentation.AdminView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("""
                    ========== Hệ thống quản lý cửa hàng ==========
                    1. Đăng nhập Admin
                    2. Thoát
                    ===============================================
                    """);
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    AdminView.showMenuLogin(sc);
                    break;
                case 2:
                    System.out.println("Thoát chương trình");
                    sc.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại");
                    break;
            }
        }
    }
}