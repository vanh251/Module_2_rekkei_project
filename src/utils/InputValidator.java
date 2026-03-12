package utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class InputValidator {
    
    public static int getIntInput(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
            }
        }
    }
    
    public static int getPositiveInt(Scanner sc, String prompt) {
        while (true) {
            int value = getIntInput(sc, prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Lỗi: Giá trị phải lớn hơn 0!");
        }
    }
    
    public static int getIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int value = getIntInput(sc, prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Lỗi: Giá trị phải từ " + min + " đến " + max + "!");
        }
    }
    
    public static BigDecimal getPositiveBigDecimal(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                BigDecimal value = sc.nextBigDecimal();
                sc.nextLine();
                if (value.compareTo(BigDecimal.ZERO) > 0) {
                    return value;
                }
                System.out.println("Lỗi: Giá trị phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số hợp lệ!");
                sc.nextLine();
            }
        }
    }
    
    public static BigDecimal getBigDecimalInRange(Scanner sc, String prompt, BigDecimal min, BigDecimal max) {
        while (true) {
            try {
                System.out.print(prompt);
                BigDecimal value = sc.nextBigDecimal();
                sc.nextLine();
                if (value.compareTo(min) >= 0 && value.compareTo(max) <= 0) {
                    return value;
                }
                System.out.println("Lỗi: Giá trị phải từ " + min + " đến " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số hợp lệ!");
                sc.nextLine();
            }
        }
    }
    
    public static String getValidString(Scanner sc, String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Lỗi: Không được để trống!");
                continue;
            }
            
            if (input.length() < minLength || input.length() > maxLength) {
                System.out.println("Lỗi: Độ dài phải từ " + minLength + " đến " + maxLength + " ký tự!");
                continue;
            }
            
            return input;
        }
    }
    
    public static String getValidEmail(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String email = sc.nextLine().trim();
            
            if (email.isEmpty()) {
                System.out.println("Lỗi: Email không được để trống!");
                continue;
            }
            
            if (email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$")) {
                return email;
            }
            System.out.println("Lỗi: Email không hợp lệ!");
        }
    }
    
    public static String getValidPhone(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String phone = sc.nextLine().trim();
            
            if (phone.isEmpty()) {
                System.out.println("Lỗi: Số điện thoại không được để trống!");
                continue;
            }
            
            if (phone.matches("^\\d{10,11}$")) {
                return phone;
            }
            System.out.println("Lỗi: Số điện thoại phải là 10-11 chữ số!");
        }
    }
    
    public static String getNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Lỗi: Không được để trống!");
        }
    }

    public static LocalDate getValidDate(Scanner sc, String prompt) {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = sc.nextLine().trim();

                if (dateStr.isEmpty()) {
                    System.out.println("Lỗi: Ngày không được để trống!");
                    continue;
                }

                LocalDate date = LocalDate.parse(dateStr, formatter);
                return date;
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Lỗi: Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng: dd/MM/yyyy");
            }
        }
    }
}
