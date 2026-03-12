package presentation;

import service.IInvoiceService;
import service.impl.InvoiceServiceimpl;
import utils.InputValidator;

import java.time.LocalDate;
import java.util.Scanner;

import static presentation.AdminView.showMainMenu;

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
                    showMainMenu(sc);
                    break;
            }
        }
    }

    public static void showRevenueByDay(Scanner sc){
        System.out.println("""
                ========== Doanh thu theo ngày ==========
                """);
        LocalDate date = InputValidator.getValidDate(sc, "Nhập ngày (dd/MM/yyyy): ");
        Double revenue = invoiceService.getRevenueByDate(date);
        if (revenue == null) {
            System.out.println("Không có doanh thu cho ngày " + date);
        } else {
            System.out.println("+----------------------+------------------+");
            System.out.printf("| %-20s | %16s |\n", "Date", "Revenue");
            System.out.println("+----------------------+------------------+");
            System.out.printf("| %-20s | %16.2f |\n", date, revenue);
            System.out.println("+----------------------+------------------+");
        }
    }

    public static void showRevenueByMonth(Scanner sc){
        System.out.println("""
                ========== Doanh thu theo tháng ==========
                """);
        int month = InputValidator.getIntInRange(sc, "Nhập tháng (1-12): ", 1, 12);
        int year = InputValidator.getIntInRange(sc, "Nhập năm: ", 1900, LocalDate.now().getYear());
        Double revenue = invoiceService.getRevenueByMonth(month, year);
        if (revenue == null) {
            System.out.println("Không có doanh thu cho tháng " + month + "/" + year);
        } else {
            System.out.println("+----------------------+------------------+");
            System.out.printf("| %-20s | %16s |\n", "Month/Year", "Revenue");
            System.out.println("+----------------------+------------------+");
            System.out.printf("| %-20s | %16.2f |\n", month + "/" + year, revenue);
            System.out.println("+----------------------+------------------+");
        }
    }

    public static void showRevenueByYear(Scanner sc){
        System.out.println("""
                ========== Doanh thu theo năm ==========
                """);
        int year = InputValidator.getIntInRange(sc, "Nhập năm: ", 1900, LocalDate.now().getYear());
        Double revenue = invoiceService.getRevenueByYear(year);
        if (revenue == null) {
            System.out.println("Không có doanh thu cho năm " + year);
        } else {
            System.out.println("+----------------------+------------------+");
            System.out.printf("| %-20s | %16s |\n", "Year", "Revenue");
            System.out.println("+----------------------+------------------+");
            System.out.printf("| %-20s | %16.2f |\n", year, revenue);
            System.out.println("+----------------------+------------------+");
        }
    }
}
