package presentation;

import model.Invoice;
import service.IInvoiceService;
import service.impl.InvoiceServiceimpl;

import java.util.ArrayList;
import java.util.Scanner;

import static presentation.AdminView.showMainMenu;

public class InvoiceView {
    private static final IInvoiceService invoiceService = new InvoiceServiceimpl();

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
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    displayAllInvoices(sc);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    showMainMenu(sc);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại");
                    break;
            }
        }
    }

    public static void displayAllInvoices(Scanner sc){
        System.out.println("""
                ========== Danh sách hóa đơn ==========
                """);
        ArrayList<Invoice> invoiceList = invoiceService.displayAllInvoices();
        if (invoiceList.isEmpty()) {
            System.out.println("Không có hóa đơn nào trong hệ thống");
        } else {
            for (Invoice invoice : invoiceList) {
                System.out.println(invoice);
            }
        }
    }
}
