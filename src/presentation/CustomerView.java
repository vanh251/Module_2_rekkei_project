package presentation;

import model.Customer;
import service.ICustomerService;
import service.impl.CustomerServiceImpl;

import java.util.ArrayList;
import java.util.Scanner;

import static presentation.AdminView.showMainMenu;

public class CustomerView {
    private static final ICustomerService customerService = new CustomerServiceImpl();

    public static void showCustomerMenu(Scanner sc) {
        while (true){
            System.out.println("""
                ========== Quản lý khách hàng ==========
                1. Hiển thị danh sách khách hàng
                2. Thêm khách hàng mới
                3. Cập nhật thông tin khách hàng
                4. Xoá khách hàng theo ID
                5. Quay lại menu chính
                ========================================
                """);
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    displayAllCustomers(sc);
                    break;
                case 2:
                    addCustomer(sc);
                    break;
                case 3:
                    updateCustomer(sc);
                    break;
                case 4:
                    deleteCustomer(sc);
                    break;
                case 5:
                    showMainMenu(sc);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại");
                    break;
            }
        }
    }

    public static void displayAllCustomers(Scanner sc) {
        System.out.println("""
                ========== Danh sách khách hàng ==========
                """);
        ArrayList<Customer> customerList = customerService.displayAllCustomers();
        if (customerList.isEmpty()) {
            System.out.println("Không có khách hàng nào trong hệ thống");
        } else {
            for (Customer customer : customerList) {
                System.out.println(customer);
            }
        }
    }

    public static void addCustomer(Scanner sc){
        System.out.println("""
                ========== Thêm khách hàng mới ==========
                """);
        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine();
        System.out.print("Nhập số điện thoại khách hàng: ");
        String phone = sc.nextLine();
        System.out.print("Nhập email khách hàng: ");
        String email = sc.nextLine();
        System.out.print("Nhập địa chỉ khách hàng: ");
        String address = sc.nextLine();
        Customer customer = new Customer(name, phone, email, address);
        customerService.addCustomer(customer);
        System.out.println("Thêm khách hàng thành công");
    }

    public static void updateCustomer(Scanner sc){
        System.out.println("""
                ========== Cập nhật thông tin khách hàng ==========
                """);
        System.out.print("Nhập ID khách hàng cần cập nhật: ");
        int id = Integer.parseInt(sc.nextLine());
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng với ID: " + id);
            return;
        }
        System.out.println("Thông tin khách hàng hiện tại có ID " + id);
        System.out.println(customer);
        System.out.print("Nhập tên khách hàng mới (bỏ trống nếu không muốn thay đổi): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) {
            customer.setName(name);
        }
        System.out.print("Nhập số điện thoại khách hàng mới (bỏ trống nếu không muốn thay đổi): ");
        String phone = sc.nextLine();
        if (!phone.isEmpty()) {
            customer.setPhone(phone);
        }
        System.out.print("Nhập email khách hàng mới (bỏ trống nếu không muốn thay đổi): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) {
            customer.setEmail(email);
        }
        System.out.print("Nhập địa chỉ khách hàng mới (bỏ trống nếu không muốn thay đổi): ");
        String address = sc.nextLine();
        if (!address.isEmpty()) {
            customer.setAddress(address);
        }
        customerService.updateCustomer(customer);
        System.out.println("Cập nhật thông tin khách hàng thành công");
    }

    public static void deleteCustomer(Scanner sc){
        System.out.println("""
                ========== Xoá khách hàng theo ID ==========
                """);
        System.out.print("Nhập ID khách hàng cần xoá: ");
        int id = Integer.parseInt(sc.nextLine());
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng với ID: " + id);
            return;
        }
        System.out.println("Thông tin khách hàng có ID " + id);
        System.out.println(customer);
        System.out.print("Bạn có chắc chắn muốn xoá khách hàng này? (y/n): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            customerService.deleteCustomer(id);
            System.out.println("Xoá khách hàng thành công");
        } else {
            System.out.println("Đã hủy xoá khách hàng");
        }
    }
}
