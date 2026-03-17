package presentation;

import service.IInvoiceService;
import service.impl.InvoiceServiceimpl;
import utils.InputValidator;

import java.time.LocalDate;
import java.util.Scanner;


public class RevenueView {
    private static final IInvoiceService invoiceService = new InvoiceServiceimpl();

    public static void showRevenueMenu(Scanner sc){
        while (true){
            System.out.println("""
                ========== Thống kê doanh thu ==========
                1. Doanh thu theo ngày
                2. Doanh thu theo tháng
                3. Doanh thu theo năm
                4. Quay lại menu chính
                ========================================
                """);
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 4);
            switch (choice){
                case 1:
                    showRevenueByDay(sc);
                    break;
                case 2:
                    showRevenueByMonth(sc);
                    break;
                case 3:
                    showRevenueByYear(sc);
                    break;
                case 4:
                    return;
            }
        }
    }

    public static void showRevenueByDay(Scanner sc){
        System.out.println("""
                ========== Doanh thu theo ngày ==========
                """);
        LocalDate date = InputValidator.getValidDate(sc, "Nhập ngày (dd/MM/yyyy): ");
        double revenue = invoiceService.getRevenueByDate(date);
        System.out.println("+----------------------+------------------+");
        System.out.printf("| %-20s | %16s |\n", "Ngày", "Doanh thu (đ)");
        System.out.println("+----------------------+------------------+");
        System.out.printf("| %-20s | %,16.2f |\n", date, revenue);
        System.out.println("+----------------------+------------------+");
    }

    public static void showRevenueByMonth(Scanner sc){
        System.out.println("""
                ========== Doanh thu theo tháng ==========
                """);
        int month = InputValidator.getIntInRange(sc, "Nhập tháng (1-12): ", 1, 12);
        int year = InputValidator.getIntInRange(sc, "Nhập năm: ", 1900, LocalDate.now().getYear());
        double revenue = invoiceService.getRevenueByMonth(month, year);
        System.out.println("+----------------------+------------------+");
        System.out.printf("| %-20s | %16s |\n", "Tháng/Năm", "Doanh thu (đ)");
        System.out.println("+----------------------+------------------+");
        System.out.printf("| %-20s | %,16.2f |\n", month + "/" + year, revenue);
        System.out.println("+----------------------+------------------+");
    }

    public static void showRevenueByYear(Scanner sc){
        System.out.println("""
                ========== Doanh thu theo năm ==========
                """);
        int year = InputValidator.getIntInRange(sc, "Nhập năm: ", 1900, LocalDate.now().getYear());
        double revenue = invoiceService.getRevenueByYear(year);
        System.out.println("+----------------------+------------------+");
        System.out.printf("| %-20s | %16s |\n", "Năm", "Doanh thu (đ)");
        System.out.println("+----------------------+------------------+");
        System.out.printf("| %-20s | %,16.2f |\n", year, revenue);
        System.out.println("+----------------------+------------------+");
    }
}
