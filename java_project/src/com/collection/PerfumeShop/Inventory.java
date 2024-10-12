package com.collection.PerfumeShop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Inventory {
    private static final Scanner scanner = new Scanner(System.in);

    public void addPerfume() {
        try {
            System.out.print("Enter perfume name: ");
            String name = scanner.nextLine();

            System.out.print("Enter perfume brand: ");
            String brand = scanner.nextLine();

            System.out.print("Enter fragrance type: ");
            String fragranceType = scanner.nextLine();

            int price;
            while (true) {
                try {
                    System.out.print("Enter perfume price: ");
                    price = Integer.parseInt(scanner.nextLine());
                    if (price < 0) {
                        System.out.println("Price cannot be negative. Please enter a valid price.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price. Please enter a numeric value.");
                }
            }

            Integer quantity = null;
            while (true) {
                try {
                    System.out.print("Enter perfume quantity (leave blank for NULL): ");
                    String input = scanner.nextLine();
                    if (input.isEmpty()) {
                        quantity = null;
                        break;
                    }
                    quantity = Integer.parseInt(input);
                    if (quantity < 0) {
                        System.out.println("Quantity cannot be negative. Please enter a valid quantity.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity. Please enter a numeric value or leave blank.");
                }
            }

            Perfume newPerfume = new Perfume(name, brand, fragranceType, price, quantity != null ? quantity : null);

            String insertSQL = "INSERT INTO perfume (name, brand, fragranceType, price, quantity) VALUES (?, ?, ?, ?, ?)";

            try (Connection con = DBUtil.getDBConnection();
                 PreparedStatement pstmt = con.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, newPerfume.getName());
                pstmt.setString(2, newPerfume.getBrand());
                pstmt.setString(3, newPerfume.getFragranceType());
                pstmt.setInt(4, newPerfume.getPrice());

                if (quantity != null) {
                    pstmt.setInt(5, quantity);
                } else {
                    pstmt.setNull(5, java.sql.Types.INTEGER);
                }

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            System.out.println("Perfume added successfully with ID: " + generatedId);
                        } else {
                            System.out.println("Perfume added successfully, but failed to retrieve ID.");
                        }
                    }
                } else {
                    System.out.println("Failed to add perfume.");
                }
            } catch (SQLException e) {
                System.out.println("Error adding perfume: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct values.");
        }
    }

    public void updatePerfume() {
        try {
            System.out.print("Enter perfume ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Perfume perfume = findPerfumeById(id);
            if (perfume == null) {
                System.out.println("Perfume with ID " + id + " not found.");
                return;
            }

            System.out.println("Choose field to update: ");
            System.out.println("1. Name");
            System.out.println("2. Brand");
            System.out.println("3. Fragrance Type");
            System.out.println("4. Price");
            System.out.println("5. Quantity");
            System.out.println("6. Done updating");

            boolean done = false;
            while (!done) {
                System.out.print("Enter your choice (or 6 to finish): ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter new name (or press Enter to keep '" + perfume.getName() + "'): ");
                        String newName = scanner.nextLine();
                        if (!newName.isEmpty()) {
                            perfume.setName(newName);
                        }
                        break;
                    case "2":
                        System.out.print("Enter new brand (or press Enter to keep '" + perfume.getBrand() + "'): ");
                        String newBrand = scanner.nextLine();
                        if (!newBrand.isEmpty()) {
                            perfume.setBrand(newBrand);
                        }
                        break;
                    case "3":
                        System.out.print("Enter new fragrance type (or press Enter to keep '" + perfume.getFragranceType() + "'): ");
                        String newFragranceType = scanner.nextLine();
                        if (!newFragranceType.isEmpty()) {
                            perfume.setFragranceType(newFragranceType);
                        }
                        break;
                    case "4":
                        System.out.print("Enter new price (or press Enter to keep '" + perfume.getPrice() + "'): ");
                        String newPriceStr = scanner.nextLine();
                        if (!newPriceStr.isEmpty()) {
                            try {
                                int newPrice = Integer.parseInt(newPriceStr);
                                perfume.setPrice(newPrice);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid price. Keeping the current price.");
                            }
                        }
                        break;
                    case "5":
                        System.out.print("Enter new quantity (or press Enter to keep '" + perfume.getQuantity() + "'): ");
                        String newQuantityStr = scanner.nextLine();
                        if (!newQuantityStr.isEmpty()) {
                            try {
                                int newQuantity = Integer.parseInt(newQuantityStr);
                                perfume.setQuantity(newQuantity);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid quantity. Keeping the current quantity.");
                            }
                        }
                        break;
                    case "6":
                        done = true; 
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose a valid field.");
                }
            }

            String updateSQL = "UPDATE perfume SET name = ?, brand = ?, fragranceType = ?, price = ?, quantity = ? WHERE id = ?";

            try (Connection con = DBUtil.getDBConnection();
                 PreparedStatement pstmt = con.prepareStatement(updateSQL)) {

                pstmt.setString(1, perfume.getName());
                pstmt.setString(2, perfume.getBrand());
                pstmt.setString(3, perfume.getFragranceType());
                pstmt.setInt(4, perfume.getPrice());
                pstmt.setInt(5, perfume.getQuantity());
                pstmt.setInt(6, id);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Perfume updated successfully.");
                } else {
                    System.out.println("Failed to update perfume.");
                }
            } catch (SQLException e) {
                System.out.println("Error updating perfume: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct values.");
        }
    }

    public void deletePerfume() {
        try {
            System.out.print("Enter perfume ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            Perfume perfume = findPerfumeById(id);
            if (perfume == null) {
                System.out.println("Perfume with ID " + id + " not found.");
                return;
            }

            String deleteSQL = "DELETE FROM perfume WHERE id = ?";

            try (Connection con = DBUtil.getDBConnection();
                 PreparedStatement pstmt = con.prepareStatement(deleteSQL)) {

                pstmt.setInt(1, id);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Perfume deleted successfully.");
                } else {
                    System.out.println("Failed to delete perfume.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting perfume: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct values.");
        }
    }

    public void searchPerfume() {
        try {
            System.out.println("Search by:");
            System.out.println("1. Name");
            System.out.println("2. Brand");
            System.out.println("3. Fragrance Type");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            String searchField = "";
            switch (choice) {
                case "1":
                    searchField = "name";
                    break;
                case "2":
                    searchField = "brand";
                    break;
                case "3":
                    searchField = "fragranceType";
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            System.out.print("Enter the search value: ");
            String searchValue = scanner.nextLine();

            String searchSQL = "SELECT * FROM perfume WHERE " + searchField + " LIKE ?";

            try (Connection con = DBUtil.getDBConnection();
                 PreparedStatement pstmt = con.prepareStatement(searchSQL)) {

                pstmt.setString(1, "%" + searchValue + "%");

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (!rs.isBeforeFirst()) {
                        System.out.println("No perfumes found.");
                        return;
                    }

                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id") +
                                ", Name: " + rs.getString("name") +
                                ", Brand: " + rs.getString("brand") +
                                ", Fragrance Type: " + rs.getString("fragranceType") +
                                ", Price: " + rs.getInt("price") +
                                ", Quantity: " + rs.getInt("quantity"));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error searching perfumes: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct values.");
        }
    }

    public void displayPerfumes() {
        System.out.println("\nAvailable Perfumes:");

        // Prompt the user to choose a field to sort by
        System.out.println("Sort by:");
        System.out.println("1. Name");
        System.out.println("2. Brand");
        System.out.println("3. Price");
        System.out.print("Enter your choice: ");
        String sortChoice = scanner.nextLine();

        // Determine the sort field based on user input
        String sortField = "";
        switch (sortChoice) {
            case "1":
                sortField = "name";
                break;
            case "2":
                sortField = "brand";
                break;
            case "3":
                sortField = "price";
                break;
            default:
                System.out.println("Invalid choice, defaulting to sort by Name.");
                sortField = "name";
        }

        // Add the ORDER BY clause to the SQL query
        String displaySQL = "SELECT * FROM perfume ORDER BY " + sortField;

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement pstmt = con.prepareStatement(displaySQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Brand: " + rs.getString("brand") +
                        ", Fragrance Type: " + rs.getString("fragranceType") +
                        ", Price: " + rs.getInt("price") +
                        ", Quantity: " + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.out.println("Error displaying perfumes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Perfume findPerfumeById(int id) {
        Perfume perfume = null;
        String searchSQL = "SELECT * FROM perfume WHERE id = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement pstmt = con.prepareStatement(searchSQL)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    perfume = new Perfume(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getString("fragranceType"),
                            rs.getInt("price"),
                            rs.getInt("quantity")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding perfume by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return perfume;
    }

    public void buyPerfume() {
        try {
            System.out.print("Enter perfume ID to buy: ");
            int id = Integer.parseInt(scanner.nextLine());

            Perfume perfume = findPerfumeById(id);
            if (perfume == null) {
                System.out.println("Perfume with ID " + id + " not found.");
                return;
            }

            System.out.println("Perfume details:");
            System.out.println("ID: " + perfume.getId());
            System.out.println("Name: " + perfume.getName());
            System.out.println("Brand: " + perfume.getBrand());
            System.out.println("Fragrance Type: " + perfume.getFragranceType());
            System.out.println("Price: " + perfume.getPrice());
            System.out.println("Quantity available: " + perfume.getQuantity());

            System.out.print("Enter quantity to buy: ");
            int quantityToBuy = Integer.parseInt(scanner.nextLine());

            if (quantityToBuy > perfume.getQuantity()) {
                System.out.println("Not enough stock available. Available quantity: " + perfume.getQuantity());
                return;
            }

            String updateSQL = "UPDATE perfume SET quantity = ? WHERE id = ?";
            try (Connection con = DBUtil.getDBConnection();
                 PreparedStatement pstmt = con.prepareStatement(updateSQL)) {

                pstmt.setInt(1, perfume.getQuantity() - quantityToBuy);
                pstmt.setInt(2, id);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Perfume bought successfully.");
                } else {
                    System.out.println("Failed to update perfume quantity.");
                }
            } catch (SQLException e) {
                System.out.println("Error buying perfume: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct values.");
        }
    }
}
