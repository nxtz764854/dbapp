package ui;

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n==== Stardew DBApp Menu ====");
            System.out.println("1. View Players");
            System.out.println("2. View Items");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    new controllers.PlayerController().viewAll();
                    break;
                case "2":
                    new controllers.ItemController().viewAll();
                    break;
                case "0":
                    System.out.println(Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}