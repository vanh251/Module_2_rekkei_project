import presentation.AdminView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdminView.showStartMenu(sc);
        sc.close();
    }
}