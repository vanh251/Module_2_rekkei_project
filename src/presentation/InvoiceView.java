package presentation;

import model.Customer;
import model.Invoice;
import service.ICustomerService;
import service.IInvoiceService;
import service.impl.CustomerServiceImpl;
import service.impl.InvoiceServiceimpl;
import utils.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static presentation.AdminView.showMainMenu;

public class InvoiceView {
    private static final IInvoiceService invoiceService = new InvoiceServiceimpl();
    private static final ICustomerService customerService = new CustomerServiceImpl();

    public static void showInvoiceMenu(Scanner sc){
        while (true){
            System.out.println("""
                    ========== QUẢN LÝ HÓA ĐƠN ==========
                    1. Hiển thị danh sách hóa đơn
                    2. Thêm mới hóa đơn
                    3. Tìm kiếm hoá đơn
                    4. Quay lại menu chính
                    ===================================
                    """);
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 4);
            switch (choice){
                case 1:
                    displayAllInvoices(sc);
                    break;
                case 2:
                    addInvoice(sc);
                    break;
                case 3:
                    showSearchInvoiceMenu(sc);
                    break;
                case 4:
                    showMainMenu(sc);
                    break;
            }
        }
    }

    public static void displayAllInvoices(Scanner sc){
        System.out.println("""
                ========== Danh sách hóa đơn ==========
                """);
        List<Invoice> invoiceList = invoiceService.displayAllInvoices();
        if (invoiceList.isEmpty()) {
            System.out.println("Không có hóa đơn nào trong hệ thống");
        } else {
            System.out.println("+-----+----------+------------------------------+------------+");
            System.out.printf("|%-5s|%-10s|%-30s|%-12s|\n", "ID", "Cust ID", "Created At", "Total (đ)");
            System.out.println("+-----+----------+------------------------------+------------+");
            for (Invoice invoice : invoiceList) {
                System.out.println(invoice);
            }
            System.out.println("+-----+----------+------------------------------+------------+");
        }
    }

    public static void addInvoice(Scanner sc) {
        System.out.println("""
                ========== Thêm mới hóa đơn ==========
                """);
        int customerId = InputValidator.getPositiveInt(sc, "Nhập ID khách hàng: ");
        BigDecimal totalAmount = InputValidator.getPositiveBigDecimal(sc, "Nhập tổng tiền: ");
        
        try {
            invoiceService.addInvoice(new Invoice(customerId, totalAmount));
            System.out.println("Thêm hóa đơn thành công");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public static void showSearchInvoiceMenu(Scanner sc){
        while (true){
            System.out.println("""
                ========== Tìm kiếm hoá đơn ==========
                1.Tìm theo tên khách hàng
                2.Tìm theo ngày/tháng/năm
                3. Quay lại menu hoá đơn
                """);
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 3);
            switch (choice){
                case 1:
                    searchInvoiceByCustomerName(sc);
                    break;
                case 2:
                    searchInvoiceByDate(sc);
                    break;
                case 3:
                    showInvoiceMenu(sc);
                    break;
            }
        }
    }

    public static void searchInvoiceByCustomerName(Scanner sc){
        System.out.println("""
                ========== Tìm kiếm hóa đơn theo tên khách hàng ==========
                """);
        String name = InputValidator.getValidString(sc, "Nhập tên khách hàng: ", 2, 50);
        List<Customer> customerList = customerService.findCustomersByName(name);
        if (customerList.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng nào với tên: " + name);
            return;
        }
        List<Invoice> invoiceList = new ArrayList<>();
        for(Customer customer : customerList){
            invoiceList.addAll(invoiceService.findInvoiceByCustomerId(customer.getId()));
        }
        if (invoiceList.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn nào cho khách hàng có tên: " + name);
        } else {
            System.out.println("+-----+----------+------------------------------+------------+");
            System.out.printf("|%-5s|%-10s|%-30s|%-12s|\n", "ID", "Cust ID", "Created At", "Total (đ)");
            System.out.println("+-----+----------+------------------------------+------------+");
            for (Invoice invoice : invoiceList) {
                System.out.println(invoice);
            }
            System.out.println("+-----+----------+------------------------------+------------+");
        }
    }

    public static void searchInvoiceByDate(Scanner sc){
        System.out.println("""
                ========== Tìm kiếm hóa đơn theo ngày/tháng/năm ==========
                """);
        LocalDate date = InputValidator.getValidDate(sc, "Nhập ngày (dd/MM/yyyy): ");
        List<Invoice> invoiceList = invoiceService.findInvoiceByDate(date);
        if (invoiceList.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn nào cho ngày: " + date);
        } else {
            System.out.println("+-----+----------+------------------------------+------------+");
            System.out.printf("|%-5s|%-10s|%-30s|%-12s|\n", "ID", "Cust ID", "Created At", "Total (đ)");
            System.out.println("+-----+----------+------------------------------+------------+");
            for (Invoice invoice : invoiceList) {
                System.out.println(invoice);
            }
            System.out.println("+-----+----------+------------------------------+------------+");
        }
    }
}
