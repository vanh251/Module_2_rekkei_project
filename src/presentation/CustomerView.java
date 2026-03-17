package presentation;

import model.Customer;
import service.ICustomerService;
import service.impl.CustomerServiceImpl;
import utils.InputValidator;

import java.util.List;
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
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 5);
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
                    return;
            }
        }
    }

    public static void displayAllCustomers(Scanner sc) {
        System.out.println("""
                ========== Danh sách khách hàng ==========
                """);
        List<Customer> customerList = customerService.displayAllCustomers();
        if (customerList.isEmpty()) {
            System.out.println("Không có khách hàng nào trong hệ thống");
        } else {
            System.out.println("+-----+----------------------+-----------------+-----------------------------+----------------------------------+");
            System.out.printf("|%-5s|%-22s|%-17s|%-29s|%-34s|\n", "ID", "Tên", "Điện thoại", "Email", "Địa chỉ");
            System.out.println("+-----+----------------------+-----------------+-----------------------------+----------------------------------+");
            for (Customer customer : customerList) {
                System.out.println(customer);
            }
            System.out.println("+-----+----------------------+-----------------+-----------------------------+----------------------------------+");
        }
    }

    public static void addCustomer(Scanner sc){
        System.out.println("""
                ========== Thêm khách hàng mới ==========
                """);
        String name = InputValidator.getValidString(sc, "Nhập tên khách hàng: ", 2, 50);
        String phone = InputValidator.getValidPhone(sc, "Nhập số điện thoại khách hàng: ");
        String email = InputValidator.getValidEmail(sc, "Nhập email khách hàng: ");
        String address = InputValidator.getValidString(sc, "Nhập địa chỉ khách hàng: ", 5, 100);
        
        Customer customer = new Customer(name, phone, email, address);
        customerService.addCustomer(customer);
        System.out.println("Thêm khách hàng thành công");
    }

    public static void updateCustomer(Scanner sc){
        System.out.println("""
                ========== Cập nhật thông tin khách hàng ==========
                """);
        displayAllCustomers(sc);
        int id = InputValidator.getPositiveInt(sc, "Nhập ID khách hàng cần cập nhật: ");
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng với ID: " + id);
            return;
        }
        System.out.println("Thông tin khách hàng hiện tại có ID " + id);
        System.out.println(customer);
        System.out.print("Nhập tên khách hàng mới (bỏ trống nếu không muốn thay đổi): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) {
            customer.setName(name);
        }
        System.out.print("Nhập số điện thoại khách hàng mới (bỏ trống nếu không muốn thay đổi): ");
        String phone = sc.nextLine().trim();
        if (!phone.isEmpty()) {
            if (phone.matches("^\\d{10,11}$")) {
                customer.setPhone(phone);
            } else {
                System.out.println("Số điện thoại không hợp lệ, bỏ qua cập nhật số điện thoại.");
            }
        }
        System.out.print("Nhập email khách hàng mới (bỏ trống nếu không muốn thay đổi): ");
        String email = sc.nextLine().trim();
        if (!email.isEmpty()) {
            if (email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$")) {
                customer.setEmail(email);
            } else {
                System.out.println("Email không hợp lệ, bỏ qua cập nhật email.");
            }
        }
        System.out.print("Nhập địa chỉ khách hàng mới (bỏ trống nếu không muốn thay đổi): ");
        String address = sc.nextLine().trim();
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
        displayAllCustomers(sc);
        int id = InputValidator.getPositiveInt(sc, "Nhập ID khách hàng cần xoá: ");
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
            if (customerService.deleteCustomer(id)){
                System.out.println("Xoá khách hàng thành công");
            } else {
                System.out.println("Không thể xoá khách hàng này vì có liên quan đến hoá đơn, vui lòng xoá hoá đơn liên quan trước");
            }
        } else {
            System.out.println("Đã hủy xoá khách hàng");
        }
    }
}
