package presentation;

import model.Customer;
import model.Invoice;
import model.InvoiceItem;
import model.Product;
import service.ICustomerService;
import service.IInvoiceItemService;
import service.IInvoiceService;
import service.IProductService;
import service.impl.CustomerServiceImpl;
import service.impl.InvoiceItemServiceImpl;
import service.impl.InvoiceServiceimpl;
import service.impl.ProductServiceImpl;
import utils.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static presentation.AdminView.showMainMenu;

public class InvoiceView {
    private static final IInvoiceService invoiceService = new InvoiceServiceimpl();
    private static final IInvoiceItemService invoiceItemService = new InvoiceItemServiceImpl();
    private static final ICustomerService customerService = new CustomerServiceImpl();
    private static final IProductService productService = new ProductServiceImpl();

    private static final String INVOICE_SEP = "+------+------------------------+----------------------+--------------------+";
    private static final String INVOICE_HDR = String.format("| %-5s| %-23s| %-21s| %-19s|",
            "ID", "Khách hàng", "Ngày tạo", "Tổng tiền (đ)");

    private static final String DETAIL_SEP  = "+------+------------------------------+---------+------------------+------------------+";
    private static final String DETAIL_HDR  = String.format("| %-5s| %-29s| %-8s| %-17s| %-17s|",
            "ID SP", "Tên sản phẩm", "Số lượng", "Đơn giá (đ)", "Thành tiền (đ)");
// menu hóa đơn chính
    public static void showInvoiceMenu(Scanner sc) {
        while (true) {
            System.out.println("""
                    ========== QUẢN LÝ HÓA ĐƠN ==========
                    1. Hiển thị danh sách hóa đơn
                    2. Thêm mới hóa đơn
                    3. Tìm kiếm hoá đơn
                    4. Quay lại menu chính
                    =======================================""");
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 4);
            switch (choice) {
                case 1:
                    displayAllInvoices();
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

    public static void displayAllInvoices() {
        System.out.println("========== Danh sách hóa đơn ==========");
        List<Invoice> invoiceList = invoiceService.displayAllInvoices();
        if (invoiceList.isEmpty()) {
            System.out.println("Không có hóa đơn nào trong hệ thống.\n");
        } else {
            printInvoiceTable(invoiceList);
        }
    }

    public static void addInvoice(Scanner sc) {
        System.out.println("========== Thêm mới hóa đơn ==========");

        // 1. Chọn khách hàng
        CustomerView.displayAllCustomers(sc);
        int customerId = InputValidator.getPositiveInt(sc, "Nhập ID khách hàng: ");
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng với ID: " + customerId + "\n");
            return;
        }
        System.out.println("Khách hàng: " + customer.getName());

        // 2. Hiển thị danh sách sản phẩm
        ProductView.displayAllProducts();

        // 3. Chọn sản phẩm và số lượng
        List<InvoiceItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        System.out.println("--- Nhập sản phẩm (nhập ID = 0 để kết thúc) ---");
        while (true) {
            int productId = InputValidator.getPositiveIntOrZero(sc, "Nhập ID sản phẩm (0 để xong): ");
            if (productId == 0) break;

            Product product = productService.findProductById(productId);
            if (product == null) {
                System.out.println("Không tìm thấy sản phẩm với ID: " + productId + ", thử lại.");
                continue;
            }

            int quantity = InputValidator.getPositiveInt(sc, "Số lượng [" + product.getName() + "]: ");
            if (quantity > product.getStock()) {
                System.out.println("Tồn kho chỉ còn " + product.getStock() + ", vui lòng nhập lại.");
                continue;
            }

            InvoiceItem item = new InvoiceItem(0, productId, product.getName(), quantity, product.getPrice());
            items.add(item);
            totalAmount = totalAmount.add(item.getSubtotal());
            System.out.printf("Đã thêm: %s x%d = %,.2f đ%n", product.getName(), quantity, item.getSubtotal());
        }

        if (items.isEmpty()) {
            System.out.println("Chưa chọn sản phẩm nào, hủy thêm hóa đơn.\n");
            return;
        }

        // 4. Xác nhận
        System.out.printf("%nTổng cộng: %,.2f đ%n", totalAmount);
        System.out.print("Xác nhận tạo hóa đơn? (y/n): ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Đã hủy.\n");
            return;
        }

        // 5. Lưu vào DB
        Invoice invoice = new Invoice(customerId, totalAmount);
        int invoiceId = invoiceService.addInvoiceReturnId(invoice);
        if (invoiceId == -1) {
            System.out.println("Lỗi khi tạo hóa đơn.\n");
            return;
        }

        for (InvoiceItem item : items) {
            item.setInvoiceId(invoiceId);
            invoiceItemService.addInvoiceItem(item);
            // Cập nhật tồn kho sản phẩm
            Product p = productService.findProductById(item.getProductId());
            if (p != null) {
                p.setStock(p.getStock() - item.getQuantity());
                productService.updateProduct(p);
            }
        }

        System.out.println("Thêm hóa đơn #" + invoiceId + " thành công!\n");
    }

    public static void viewInvoiceDetail(Scanner sc) {
        System.out.println("========== Xem chi tiết hóa đơn ==========");
        int invoiceId = InputValidator.getPositiveInt(sc, "Nhập ID hóa đơn: ");
        printInvoiceDetail(invoiceId);
    }

    public static void printInvoiceDetail(int invoiceId) {
        List<InvoiceItem> items = invoiceItemService.findItemsByInvoiceId(invoiceId);
        if (items.isEmpty()) {
            System.out.println("Không tìm thấy chi tiết cho hóa đơn #" + invoiceId + "\n");
            return;
        }
        System.out.println("--- Chi tiết hóa đơn #" + invoiceId + " ---");
        System.out.println(DETAIL_SEP);
        System.out.println(DETAIL_HDR);
        System.out.println(DETAIL_SEP);
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceItem item : items) {
            System.out.println(item);
            total = total.add(item.getSubtotal());
        }
        System.out.println(DETAIL_SEP);
        System.out.printf("| %-46s| %,16.2f | %-17s|%n", "  TỔNG CỘNG", total, "");
        System.out.println(DETAIL_SEP);
        System.out.println();
    }

    public static void showSearchInvoiceMenu(Scanner sc) {
        while (true) {
            System.out.println("""
                    ========== Tìm kiếm hoá đơn ==========
                    1. Tìm theo tên khách hàng
                    2. Tìm theo ngày/tháng/năm
                    3. Quay lại menu hoá đơn
                    """);
            int choice = InputValidator.getIntInRange(sc, "Nhập lựa chọn: ", 1, 3);
            switch (choice) {
                case 1:
                    searchInvoiceByCustomerName(sc);
                    break;
                case 2:
                    searchInvoiceByDate(sc);
                    break;
                case 3:
                    return;
            }
        }
    }

    public static void searchInvoiceByCustomerName(Scanner sc) {
        System.out.println("========== Tìm kiếm hóa đơn theo tên khách hàng ==========");
        String name = InputValidator.getValidString(sc, "Nhập tên khách hàng: ", 2, 50);
        List<Customer> customerList = customerService.findCustomersByName(name);
        if (customerList.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng nào với tên: " + name + "\n");
            return;
        }
        List<Invoice> invoiceList = new ArrayList<>();
        for (Customer customer : customerList) {
            invoiceList.addAll(invoiceService.findInvoiceByCustomerId(customer.getId()));
        }
        if (invoiceList.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn nào cho khách hàng: " + name + "\n");
        } else {
            printInvoiceTable(invoiceList);
            askViewDetail(sc, invoiceList);
        }
    }

    public static void searchInvoiceByDate(Scanner sc) {
        System.out.println("========== Tìm kiếm hóa đơn theo ngày/tháng/năm ==========");
        LocalDate date = InputValidator.getValidDate(sc, "Nhập ngày (dd/MM/yyyy): ");
        List<Invoice> invoiceList = invoiceService.findInvoiceByDate(date);
        if (invoiceList.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn nào cho ngày: " + date + "\n");
        } else {
            printInvoiceTable(invoiceList);
            askViewDetail(sc, invoiceList);
        }
    }

    //Helper method
    private static void printInvoiceTable(List<Invoice> invoiceList) {
        System.out.println(INVOICE_SEP);
        System.out.println(INVOICE_HDR);
        System.out.println(INVOICE_SEP);
        for (Invoice invoice : invoiceList) {
            String custDisplay = (invoice.getCustomerName() != null && !invoice.getCustomerName().isEmpty())
                    ? invoice.getCustomerName() : ("ID:" + invoice.getCustomerId());
            String dateStr = (invoice.getCreatedAt() != null)
                    ? invoice.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    : "";
            System.out.printf("| %-5d| %-23s| %-21s| %,19.2f|%n",
                    invoice.getId(), custDisplay, dateStr, invoice.getTotalAmount());
        }
        System.out.println(INVOICE_SEP);
        System.out.println();
    }

    private static void askViewDetail(Scanner sc, List<Invoice> invoiceList) {
        System.out.print("Xem chi tiết hóa đơn nào? (nhập ID, 0 để bỏ qua): ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            if (id != 0) {
                boolean found = invoiceList.stream().anyMatch(inv -> inv.getId() == id);
                if (found) printInvoiceDetail(id);
                else System.out.println("ID không có trong danh sách trên.\n");
            }
        } catch (NumberFormatException e) {
            // bỏ qua
        }
    }
}
