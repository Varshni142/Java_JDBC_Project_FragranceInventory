package com.collection.PerfumeShop;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();

    public static void main(String[] args) {
        boolean loggedIn = false;
        boolean isAdmin = false;

        while (true) {
            if (!loggedIn) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                String password = scanner.nextLine().trim();
                
                isAdmin = Login.isAdmin(username, password);
				if (isAdmin) {
				    System.out.println("Admin access granted.");
				} else {
				    System.out.println("User access granted.");
				}
				loggedIn = true;
            }

            if (loggedIn) {
                if (isAdmin) {
                    loggedIn = adminMenu();  
                } else {
                    loggedIn = userMenu();  
                }
            }
        }
    }

    private static boolean adminMenu() {
        while (true) {
            try {
                // Stylish Admin Menu Design
                System.out.println("╔═════════════════════════════════╗");
                System.out.println("║      🌸 Varshni Perfume Store 🌸      ║");
                System.out.println("║          --- Admin Menu ---          ║");
                System.out.println("╚═════════════════════════════════╝");
                System.out.println("   ⟿ [1] Add Perfume");
                System.out.println("   ⟿ [2] Update Perfume");
                System.out.println("   ⟿ [3] Delete Perfume");
                System.out.println("   ⟿ [4] Show Perfumes (with sorting)");
                System.out.println("   ⟿ [5] Search Perfume");
                System.out.println("   ⟿ [6] Logout");
                System.out.println("   ⟿ [7] Exit");
                System.out.println("────────────────────────────────────");
                System.out.print("⟿ Please choose an option (1-7): ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        inventory.addPerfume();
                        break;
                    case "2":
                        inventory.updatePerfume();
                        break;
                    case "3":
                        inventory.deletePerfume();
                        break;
                    case "4":
                        inventory.displayPerfumes();
                        break;
                    case "5":
                        inventory.searchPerfume();
                        break;
                    case "6":
                        System.out.println("Logging out...");
                        return false;
                    case "7":
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("❗ An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static boolean userMenu() {
        while (true) {
            try {
                // Sleek User Menu Design
                System.out.println("╔═════════════════════════════════╗");
                System.out.println("║      🌸 Varshni Perfume Store 🌸      ║");
                System.out.println("║          --- User Menu ---           ║");
                System.out.println("╚═════════════════════════════════╝");
                System.out.println("   ⟿ [1] Show Perfumes");
                System.out.println("   ⟿ [2] Search Perfume");
                System.out.println("   ⟿ [3] Buy Perfume");
                System.out.println("   ⟿ [4] Logout");
                System.out.println("   ⟿ [5] Exit");
                System.out.println("────────────────────────────────────");
                System.out.print("⟿ Please choose an option (1-5): ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        inventory.displayPerfumes();
                        break;
                    case "2":
                        inventory.searchPerfume();
                        break;
                    case "3":
                        inventory.buyPerfume();
                        break;
                    case "4":
                        System.out.println("Logging out...");
                        return false;
                    case "5":
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("❗ An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
