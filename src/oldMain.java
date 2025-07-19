import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            if (conn == null) {
                System.out.println("Failed to connect to database.");
                return;
            }

            System.out.print("Enter your username: ");
            String inputName = scanner.nextLine().trim();

            int playerId;
            String selectQuery = "SELECT playerID, wallet, current_day, current_season, current_year FROM players WHERE playername = ?";
            PreparedStatement checkStmt = conn.prepareStatement(selectQuery);
            checkStmt.setString(1, inputName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                playerId = rs.getInt("playerID");
                int wallet = rs.getInt("wallet");
                int day = rs.getInt("current_day");
                String season = rs.getString("current_season");
                int year = rs.getInt("current_year");
                System.out.println("Welcome back, " + inputName + "!");
                System.out.println("Your Player ID: " + playerId + ", Wallet: " + wallet);
                System.out.println("Current Date: Day " + day + " of " + season + ", Year " + year);
            } else {
                String insertQuery = "INSERT INTO players (playername, wallet, current_day, current_season, current_year) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setString(1, inputName);
                insertStmt.setInt(2, 150); //wallet
                insertStmt.setInt(3, 1); // Start at day 1
                insertStmt.setString(4, "Spring"); // Start at Spring
                insertStmt.setInt(5, 1); // Start at year 1
                insertStmt.executeUpdate();

                ResultSet keys = insertStmt.getGeneratedKeys();
                if (keys.next()) {
                    playerId = keys.getInt(1);
                    createFarm(conn, playerId);
                    System.out.println("New player created!");
                    System.out.println("Your Player ID: " + playerId + ", Username: " + inputName + ", Wallet: 150");
                    System.out.println("Current Date: Day 1 of Spring, Year 1");
                } else {
                    System.out.println("Failed to create player.");
                    return;
                }
            }

            // Show menu options
            boolean running = true;
            while (running) {
               System.out.println("\nWhat would you like to do?");
    System.out.println("1. Farm");
    System.out.println("2. Shop");
    System.out.println("3. Inventory");
    System.out.println("4. Relationships");
    System.out.println("5. Rest (Advance to Next Day)");
    System.out.println("6. Sign out");

    System.out.print("Enter your choice: ");
    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    switch (choice) {
        case 1:
            showFarm(conn, playerId, scanner);
            break;
        case 2:
            shop(conn, playerId, scanner);
            break;
        case 3:
            showInventory(conn, playerId);
            break;
        case 4:
            System.out.println("Viewing relationships... (not yet implemented)");
            break;
        case 5:
            advanceDay(conn, playerId);
            break;
        case 6:
           System.out.println("Signed out. Goodbye!");
            System.exit(0);
            break;
        default:
            System.out.println("Invalid choice.");
    }
}
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void advanceDay(Connection conn, int playerID) {
    try {
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT current_day, current_season, current_year FROM players WHERE playerID = ?"
        );
        stmt.setInt(1, playerID);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int day = rs.getInt("current_day");
            String season = rs.getString("current_season");
            int year = rs.getInt("current_year");

            day++;

            if (day > 28) {
                day = 1;
                switch (season) {
                    case "Spring": season = "Summer"; break;
                    case "Summer": season = "Fall"; break;
                    case "Fall": season = "Winter"; break;
                    case "Winter":
                        season = "Spring";
                        year++;
                        break;
                }
            }

            PreparedStatement updateStmt = conn.prepareStatement(
                "UPDATE players SET current_day = ?, current_season = ?, current_year = ? WHERE playerID = ?"
            );
            updateStmt.setInt(1, day);
            updateStmt.setString(2, season);
            updateStmt.setInt(3, year);
            updateStmt.setInt(4, playerID);
            updateStmt.executeUpdate();

            

            System.out.println("You rested. It is now Day " + day + " of " + season + ", Year " + year + ".");
            updateCropGrowth(conn, playerID);
            updateAnimalGrowth(conn, playerID); 
        }

    } catch (SQLException e) {
        System.out.println("Error advancing day: " + e.getMessage());
    }

}


    public static void showInventory(Connection conn, int playerId) {
    String query = "SELECT i.itemname, inv.quantity " +
                   "FROM inventories inv " +
                   "JOIN items i ON inv.itemID = i.itemID " +
                   "WHERE inv.playerID = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, playerId);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Your Inventory:");
        boolean hasItems = false;
        while (rs.next()) {

            String item = rs.getString("itemname");
            int qty = rs.getInt("quantity");
            if(qty!=0){
                System.out.println("- " + item + ": " + qty);
            hasItems = true;
            }
            
        }
        if (!hasItems) {
            System.out.println("(empty)");
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving inventory: " + e.getMessage());
    }
}


   public static void showFarm(Connection conn, int playerId, Scanner scanner) {
    try {
        // Display planted crops
        String cropQuery = "SELECT c.cropID, i.itemname, c.growth_time, c.readytoharvest FROM crops c JOIN items i ON c.itemID = i.itemID WHERE c.playerID = ?";
        PreparedStatement cropStmt = conn.prepareStatement(cropQuery);
        cropStmt.setInt(1, playerId);
        ResultSet cropRs = cropStmt.executeQuery();
        System.out.println("Your planted crops:");
        boolean hasCrops = false;
        while (cropRs.next()) {
            int cropID = cropRs.getInt("cropID");
            String cropName = cropRs.getString("itemname");
            int growthTime = cropRs.getInt("growth_time");
            boolean ready = cropRs.getBoolean("readytoharvest");
            System.out.println("- " + cropName + " (CropID: " + cropID + "), Days left: " + growthTime + (ready ? " [Ready to harvest]" : ""));
            hasCrops = true;
        }
        if (!hasCrops) System.out.println("(No crops planted)");

        // Display animals
        String animalQuery = "SELECT a.animalID, i.itemname, a.age, a.readytoharvest FROM animals a JOIN items i ON a.itemID = i.itemID WHERE a.playerID = ?";
        PreparedStatement animalStmt = conn.prepareStatement(animalQuery);
        animalStmt.setInt(1, playerId);
        ResultSet animalRs = animalStmt.executeQuery();
        System.out.println("Your animals:");
        boolean hasAnimals = false;
        while (animalRs.next()) {
            int animalID = animalRs.getInt("animalID");
            String animalname = animalRs.getString("itemname");
            int age = animalRs.getInt("age");
            boolean ready = animalRs.getBoolean("readytoharvest");
            System.out.println("- " + animalname + " (AnimalID: " + animalID + "), Age: " + age + (ready ? " [Ready to harvest]" : ""));
            hasAnimals = true;
        }
        if (!hasAnimals) System.out.println("(No animals)");

        // Farm menu
        System.out.println("\nFarm Options:");
        System.out.println("[1] Plant seeds");
        System.out.println("[2] Harvest crops");
        System.out.println("[3] Place animals");
        System.out.println("[4] Tend to animals");
        System.out.println("[0] Return to main menu");
        System.out.print("Enter your choice: ");
        String farmChoice = scanner.nextLine().trim();

        switch (farmChoice) {
            case "1":
                // Show seeds in inventory
                String seedQuery = "SELECT i.itemID, i.itemname, inv.quantity, i.specialvalue FROM inventories inv JOIN items i ON inv.itemID = i.itemID WHERE inv.playerID = ? AND i.itemtype = 'crop'";
                PreparedStatement seedStmt = conn.prepareStatement(seedQuery);
                seedStmt.setInt(1, playerId);
                ResultSet seedRs = seedStmt.executeQuery();
                System.out.println("Seeds in your inventory:");
                boolean hasSeeds = false;
                while (seedRs.next()) {
                    int itemID = seedRs.getInt("itemID");
                    String itemName = seedRs.getString("itemname");
                    int qty = seedRs.getInt("quantity");
                    int growthDays = seedRs.getInt("specialvalue"); // Use 'value' as growth days for simplicity
                    if(qty !=0){
                        System.out.println("- " + itemName + " (ItemID: " + itemID + "), Qty: " + qty + ", Days to grow: " + growthDays);
                        hasSeeds = true;
                    }
                }
                if (!hasSeeds) {
                    System.out.println("(No seeds in inventory)");
                    break;
                }
                System.out.print("Enter ItemID of the seed to plant: ");
                String itemIdStr = scanner.nextLine().trim();
                try {
                    int itemID = Integer.parseInt(itemIdStr);
                    plantSeed(conn, playerId, itemID);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ItemID.");
                }
                break;
            case "2":
                // Harvest crops
                System.out.print("Enter CropID to harvest: ");
                String cropIdStr = scanner.nextLine().trim();
                try {
                    int cropID = Integer.parseInt(cropIdStr);
                    harvestCrop(conn, playerId, cropID);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid CropID.");
                }
                break;
            case "3":
                // Show animals in inventory
                String animalInvQuery = "SELECT i.itemID, i.itemname, inv.quantity, i.specialvalue FROM inventories inv JOIN items i ON inv.itemID = i.itemID WHERE inv.playerID = ? AND i.itemtype = 'animal'";
                PreparedStatement animalInvStmt = conn.prepareStatement(animalInvQuery);
                animalInvStmt.setInt(1, playerId);
                ResultSet animalInvRs = animalInvStmt.executeQuery();
                System.out.println("Animals in your inventory:");
                boolean hasanimals = false;
                while (animalInvRs.next()) {
                    int itemID = animalInvRs.getInt("itemID");
                    String itemName = animalInvRs.getString("itemname");
                    int qty = animalInvRs.getInt("quantity");
                    int growthDays = animalInvRs.getInt("specialvalue"); // Use 'value' as growth days for simplicity
                    if(qty !=0){
                        System.out.println("- " + itemName + " (ItemID: " + itemID + "), Qty: " + qty + ", Maturing days: " + growthDays);
                        hasanimals = true;
                    }
                }
                if (!hasanimals) {
                    System.out.println("(No animals in inventory)");
                    break;
                }
                System.out.print("Enter ItemID of the animal to place: ");
                String itemIdString = scanner.nextLine().trim();
                try {
                    int itemID = Integer.parseInt(itemIdString);
                    placeAnimal(conn, playerId, itemID);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ItemID.");
                }
                break;
            case "4":
                // Tend to Animals
                System.out.print("Enter AnimalID to tend: ");
                String animalIdStr = scanner.nextLine().trim();
                try {
                    int animalID = Integer.parseInt(animalIdStr);
                    tendAnimal(conn, playerId, animalID);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid CropID.");
                }
                break;
            case "0":
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice.");
        }

    } catch (SQLException e) {
        System.out.println("Error showing farm: " + e.getMessage());
    }
}


public static void createFarm(Connection conn, int playerId) {
    try {
        // Check if the player already has a farm
        String checkQuery = "SELECT playerID FROM farms WHERE playerID = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setInt(1, playerId);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            System.out.println("Farm already exists for player ID: " + playerId);
            return;
        }

        // Insert new farm row
        String insertFarm = "INSERT INTO farms (playerID, cropID, animalID) VALUES (?, NULL, NULL)";
        PreparedStatement stmt = conn.prepareStatement(insertFarm);
        stmt.setInt(1, playerId);
        stmt.executeUpdate();

        System.out.println("Farm created for player ID: " + playerId);
    } catch (SQLException e) {
        System.out.println("Error creating farm: " + e.getMessage());
    }
}

public static void plantSeed(Connection conn, int playerId, int itemID) {
    try {
        String checkQuery = "SELECT inv.quantity, i.itemname, i.specialvalue FROM inventories inv JOIN items i ON inv.itemID = i.itemID WHERE inv.playerID = ? AND inv.itemID = ? AND i.itemtype = 'crop'";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setInt(1, playerId);
        checkStmt.setInt(2, itemID);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next() && rs.getInt("quantity") > 0) {
            int growthDays = rs.getInt("specialvalue");
            String cropName = rs.getString("itemname");

            int produceId = getProduceId(itemID);
            if (produceId == -1) {
                System.out.println("No grown product mapping for this seed.");
                return;
            }

            // Insert crop
            String insertCrop = "INSERT INTO crops (playerID, itemID, cropname, growth_time, produceID, readytoharvest) VALUES (?, ?, ?, ?, ?, FALSE)";
            PreparedStatement insertStmt = conn.prepareStatement(insertCrop, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, playerId);
            insertStmt.setInt(2, itemID);
            insertStmt.setString(3, cropName);
            insertStmt.setInt(4, growthDays);
            insertStmt.setInt(5, produceId);
            insertStmt.executeUpdate();

            // Get cropID
            ResultSet keys = insertStmt.getGeneratedKeys();
            if (keys.next()) {
                int cropId = keys.getInt(1);

                // Update player's farm
                String updateFarm = "UPDATE farms SET cropID = ? WHERE playerID = ?";
                PreparedStatement updateFarmStmt = conn.prepareStatement(updateFarm);
                updateFarmStmt.setInt(1, cropId);
                updateFarmStmt.setInt(2, playerId);
                updateFarmStmt.executeUpdate();
            }

            // Reduce seed count
            String updateInv = "UPDATE inventories SET quantity = quantity - 1 WHERE playerID = ? AND itemID = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateInv);
            updateStmt.setInt(1, playerId);
            updateStmt.setInt(2, itemID);
            updateStmt.executeUpdate();

            System.out.println("Planted " + cropName + " in your farm!");
        } else {
            System.out.println("You don't have that seed.");
        }
    } catch (SQLException e) {
        System.out.println("Error planting seed: " + e.getMessage());
    }
}

public static void placeAnimal(Connection conn, int playerId, int itemID) {
    try {
        String checkQuery = "SELECT inv.quantity, i.itemname, i.specialvalue FROM inventories inv JOIN items i ON inv.itemID = i.itemID WHERE inv.playerID = ? AND inv.itemID = ? AND i.itemtype = 'animal'";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setInt(1, playerId);
        checkStmt.setInt(2, itemID);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next() && rs.getInt("quantity") > 0) {
            int produceDays = rs.getInt("specialvalue");
            String animalName = rs.getString("itemname");

            int produceId = getProduceId(itemID);
            if (produceId == -1) {
                System.out.println("No grown product mapping for this animal.");
                return;
            }

            // Insert animal
            String insertAnimal = "INSERT INTO animals (playerID, itemID, animalname, age, producedays, produceID, readytoharvest) VALUES (?, ?, ?, ?, ?, ?, FALSE)";
            PreparedStatement insertStmt = conn.prepareStatement(insertAnimal, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, playerId);
            insertStmt.setInt(2, itemID);
            insertStmt.setString(3, animalName);
            insertStmt.setInt(4, 0);
            insertStmt.setInt(5, produceDays);
            insertStmt.setInt(6, produceId);
            insertStmt.executeUpdate();

            ResultSet keys = insertStmt.getGeneratedKeys();
            if (keys.next()) {
                int animalId = keys.getInt(1);

                // Update player's farm
                String updateFarm = "UPDATE farms SET animalID = ? WHERE playerID = ?";
                PreparedStatement updateFarmStmt = conn.prepareStatement(updateFarm);
                updateFarmStmt.setInt(1, animalId);
                updateFarmStmt.setInt(2, playerId);
                updateFarmStmt.executeUpdate();
            }

            // Reduce quantity
            String updateInv = "UPDATE inventories SET quantity = quantity - 1 WHERE playerID = ? AND itemID = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateInv);
            updateStmt.setInt(1, playerId);
            updateStmt.setInt(2, itemID);
            updateStmt.executeUpdate();

            System.out.println("Placed " + animalName + " in your farm!");
        } else {
            System.out.println("You don't have that animal.");
        }
    } catch (SQLException e) {
        System.out.println("Error placing animal: " + e.getMessage());
    }
}


    public static void harvestCrop(Connection conn, int playerId, int cropID) {
    try {
        // Check if crop is ready
        String checkQuery = "SELECT itemID, cropname, readytoharvest FROM crops WHERE cropID = ? AND playerID = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setInt(1, cropID);
        checkStmt.setInt(2, playerId);
        ResultSet rs = checkStmt.executeQuery();
        if (rs.next() && rs.getBoolean("readytoharvest")) {
            int itemID = rs.getInt("itemID");
            // Remove crop from crop table
            String deleteCrop = "DELETE FROM crops WHERE cropID = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteCrop);
            deleteStmt.setInt(1, cropID);
            deleteStmt.executeUpdate();
            // Add to inventory (or update quantity)
            int ProduceId = getProduceId(itemID);
            if (ProduceId == -1) {
                System.out.println("No grown product mapping for this seed.");
                return;
            }
            // Add grown product to inventory
            String upsertInv = "INSERT INTO inventories (playerID, itemID, quantity) VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE quantity = quantity + 1";
            PreparedStatement upsertStmt = conn.prepareStatement(upsertInv);
            upsertStmt.setInt(1, playerId);
            upsertStmt.setInt(2, ProduceId);
            upsertStmt.executeUpdate();
            System.out.println("Harvested successfully!");
        } else {
            System.out.println("That crops is not ready to harvest.");
        }
    } catch (SQLException e) {
        System.out.println("Error harvesting crop: " + e.getMessage());
    }
}

public static void tendAnimal(Connection conn, int playerId, int animalID) {
    try {
        // Check if animal is ready
        String checkQuery = "SELECT itemID, animalname, readytoharvest FROM animals WHERE animalID = ? AND playerID = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setInt(1, animalID);
        checkStmt.setInt(2, playerId);
        ResultSet rs = checkStmt.executeQuery();
        if (rs.next() && rs.getBoolean("readytoharvest")) {
            int itemID = rs.getInt("itemID");
            


            // Add to inventory (or update quantity)
            int ProduceId = getProduceId(itemID);
            if (ProduceId == -1) {
                System.out.println("No grown product mapping for this seed.");
                return;
            }
            // Add grown product to inventory
            String upsertInv = "INSERT INTO inventories (playerID, itemID, quantity) VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE quantity = quantity + 1";
            PreparedStatement upsertStmt = conn.prepareStatement(upsertInv);
            upsertStmt.setInt(1, playerId);
            upsertStmt.setInt(2, ProduceId);
            upsertStmt.executeUpdate();

            String resetproduce = "UPDATE animals set readytoharvest = FALSE WHERE playerID = ?";
            PreparedStatement updateStmt = conn.prepareStatement(resetproduce);
            updateStmt.setInt(1, playerId);
            updateStmt.executeUpdate();
           

            System.out.println("Tended successfully!");
        } else {
            System.out.println("That animal is not ready to be tended.");
        }
    } catch (SQLException e) {
        System.out.println("Error tending animal: " + e.getMessage());
    }
}

    public static void updateCropGrowth(Connection conn, int playerId) {
    try {
        // Decrease growth_time by 1 for all crops not ready
        String updateGrowth = "UPDATE crops SET growth_time = growth_time - 1 WHERE playerID = ? AND readytoharvest = FALSE AND growth_time > 0";
        PreparedStatement updateStmt = conn.prepareStatement(updateGrowth);
        updateStmt.setInt(1, playerId);
        updateStmt.executeUpdate();
        // Set readytoharvest = TRUE where growth_time <= 0
        String readyQuery = "UPDATE crops SET readytoharvest = TRUE WHERE playerID = ? AND growth_time <= 0";
        PreparedStatement readyStmt = conn.prepareStatement(readyQuery);
        readyStmt.setInt(1, playerId);
        readyStmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error updating crop growth: " + e.getMessage());
    }
}

public static void updateAnimalGrowth(Connection conn, int playerId) {
    try {
        // Determine updates for animals that are not producing products
        String updateGrowth = "UPDATE animals SET age = age + 1 WHERE playerID = ? AND readytoharvest = FALSE";
        PreparedStatement updateStmt = conn.prepareStatement(updateGrowth);
        updateStmt.setInt(1, playerId);
        updateStmt.executeUpdate();


        // Set readytoharvest = TRUE where age % producedays ==0
        String readyQuery = "UPDATE animals SET readytoharvest = TRUE WHERE playerID = ? AND age % producedays = 0 AND age > 0";
        PreparedStatement readyStmt = conn.prepareStatement(readyQuery);
        readyStmt.setInt(1, playerId);
        readyStmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error updating animal growth: " + e.getMessage());
    }
}

    public static void shop(Connection conn, int playerId, Scanner scanner) {
    while (true) {
        System.out.println("\nWelcome to the Shop!");
        System.out.println("[1] Buy seeds");
        System.out.println("[2] Buy animals");
        System.out.println("[3] Sell crops");
        System.out.println("[0] Return to main menu");
        System.out.print("Enter your choice: ");
        String shopChoice = scanner.nextLine().trim();

        switch (shopChoice) {
            case "1":
                // --- BUY SEEDS ---
                try {
                    String query = "SELECT itemID, itemname, descript FROM items WHERE itemtype = 'crop' ORDER BY itemID LIMIT 5";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();

                    int[] itemIDs = new int[5];
                    String[] names = new String[5];
                    String[] descripts = new String[5];
                    int[] prices = {20, 40, 50, 60, 150}; // Prices in order
                    int count = 0;

                    System.out.println("Available seeds:");
                    while (rs.next() && count < 5) {
                        itemIDs[count] = rs.getInt("itemID");
                        names[count] = rs.getString("itemname");
                        descripts[count] = rs.getString("descript");
                        System.out.println("[" + (count + 1) + "] " +
                            names[count] +
                            " - " + descripts[count] +
                            " (Price: " + prices[count] + ")");
                        count++;
                    }
                    if (count == 0) {
                        System.out.println("No seeds available.");
                        break;
                    }

                    System.out.print("Enter the number of the seed you want to buy (or 0 to cancel): ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (choice < 1 || choice > count) {
                        if (choice == 0) break;
                        System.out.println("Invalid selection.");
                        break;
                    }

                    int selectedItemID = itemIDs[choice - 1];
                    String selectedName = names[choice - 1];
                    int selectedPrice = prices[choice - 1];

                    System.out.print("How many " + selectedName + " do you want to buy? ");
                    int qty = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (qty <= 0) {
                        System.out.println("Invalid quantity.");
                        break;
                    }

                    int totalPrice = selectedPrice * qty;

                    // Check player wallet
                    String walletQuery = "SELECT wallet FROM players WHERE playerID = ?";
                    PreparedStatement walletStmt = conn.prepareStatement(walletQuery);
                    walletStmt.setInt(1, playerId);
                    ResultSet walletRs = walletStmt.executeQuery();
                    if (walletRs.next()) {
                        int wallet = walletRs.getInt("wallet");
                        if (wallet < totalPrice) {
                            System.out.println("Not enough money! You need " + totalPrice + " but only have " + wallet + ".");
                            break;
                        }
                    } else {
                        System.out.println("Player not found.");
                        break;
                    }

                    // Deduct money
                    String updateWallet = "UPDATE players SET wallet = wallet - ? WHERE playerID = ?";
                    PreparedStatement updateWalletStmt = conn.prepareStatement(updateWallet);
                    updateWalletStmt.setInt(1, totalPrice);
                    updateWalletStmt.setInt(2, playerId);
                    updateWalletStmt.executeUpdate();

                    // Add seeds to inventory
                    String upsertInv = "INSERT INTO inventories (playerID, itemID, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?";
                    PreparedStatement upsertStmt = conn.prepareStatement(upsertInv);
                    upsertStmt.setInt(1, playerId);
                    upsertStmt.setInt(2, selectedItemID);
                    upsertStmt.setInt(3, qty);
                    upsertStmt.setInt(4, qty);
                    upsertStmt.executeUpdate();

                    System.out.println("You bought " + qty + " " + selectedName + (qty > 1 ? "s!" : "!"));
                } catch (SQLException e) {
                    System.out.println("Error in shop: " + e.getMessage());
                }
                break;

            case "2":
            try {
                    String query = "SELECT itemID, itemname, descript FROM items WHERE itemtype = 'animal' ORDER BY itemID LIMIT 5";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();

                    int[] itemIDs = new int[5];
                    String[] names = new String[5];
                    String[] descripts = new String[5];
                    int[] prices = {150, 200, 300, 500, 1000}; // Prices in order
                    int count = 0;

                    System.out.println("Available animals:");
                    while (rs.next() && count < 5) {
                        itemIDs[count] = rs.getInt("itemID");
                        names[count] = rs.getString("itemname");
                        descripts[count] = rs.getString("descript");
                        System.out.println("[" + (count + 1) + "] " +
                            names[count] +
                            " - " + descripts[count] +
                            " (Price: " + prices[count] + ")");
                        count++;
                    }
                    if (count == 0) {
                        System.out.println("No animals available.");
                        break;
                    }

                    System.out.print("Enter the number of the animal you want to buy (or 0 to cancel): ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (choice < 1 || choice > count) {
                        if (choice == 0) break;
                        System.out.println("Invalid selection.");
                        break;
                    }

                    int selectedItemID = itemIDs[choice - 1];
                    String selectedName = names[choice - 1];
                    int selectedPrice = prices[choice - 1];

                    System.out.print("How many " + selectedName + " do you want to buy? ");
                    int qty = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (qty <= 0) {
                        System.out.println("Invalid quantity.");
                        break;
                    }

                    int totalPrice = selectedPrice * qty;

                    // Check player wallet
                    String walletQuery = "SELECT wallet FROM players WHERE playerID = ?";
                    PreparedStatement walletStmt = conn.prepareStatement(walletQuery);
                    walletStmt.setInt(1, playerId);
                    ResultSet walletRs = walletStmt.executeQuery();
                    if (walletRs.next()) {
                        int wallet = walletRs.getInt("wallet");
                        if (wallet < totalPrice) {
                            System.out.println("Not enough money! You need " + totalPrice + " but only have " + wallet + ".");
                            break;
                        }
                    } else {
                        System.out.println("Player not found.");
                        break;
                    }

                    // Deduct money
                    String updateWallet = "UPDATE players SET wallet = wallet - ? WHERE playerID = ?";
                    PreparedStatement updateWalletStmt = conn.prepareStatement(updateWallet);
                    updateWalletStmt.setInt(1, totalPrice);
                    updateWalletStmt.setInt(2, playerId);
                    updateWalletStmt.executeUpdate();

                    // Add animals to inventory
                    String upsertInv = "INSERT INTO inventories (playerID, itemID, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?";
                    PreparedStatement upsertStmt = conn.prepareStatement(upsertInv);
                    upsertStmt.setInt(1, playerId);
                    upsertStmt.setInt(2, selectedItemID);
                    upsertStmt.setInt(3, qty);
                    upsertStmt.setInt(4, qty);
                    upsertStmt.executeUpdate();

                    System.out.println("You bought " + qty + " " + selectedName + (qty > 1 ? "s!" : "!"));
                } catch (SQLException e) {
                    System.out.println("Error in shop: " + e.getMessage());
                }
                break;
            case "3":
                // --- SELL PRODUCTS ---
    try {
        String query = "SELECT i.itemID, i.itemname, i.itemtype, i.specialvalue, i.descript, i.quantity AS stock, inv.quantity " +
                       "FROM inventories inv JOIN items i ON inv.itemID = i.itemID " +
                       "WHERE inv.playerID = ? AND i.itemtype = 'product' AND inv.quantity > 0";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, playerId);
        ResultSet rs = stmt.executeQuery();

        int[] itemIDs = new int[50];
        String[] names = new String[50];
        String[] types = new String[50];
        int[] values = new int[50];
        String[] descripts = new String[50];
        int[] stocks = new int[50];
        int[] quantities = new int[50];
        int count = 0;

        System.out.println("Your products available to sell:");
        while (rs.next() && count < 20) {
            itemIDs[count] = rs.getInt("itemID");
            names[count] = rs.getString("itemname");
            types[count] = rs.getString("itemtype");
            values[count] = rs.getInt("specialvalue");
            descripts[count] = rs.getString("descript");
            stocks[count] = rs.getInt("stock");
            quantities[count] = rs.getInt("quantity");
            System.out.println("[" + (count + 1) + "] " +
                names[count] + " " + descripts[count] +
                " Qty: " + quantities[count] +
                ", Sell Price: " + values[count]);
            count++;
        }
        if (count == 0) {
            System.out.println("You have no products to sell.");
            break;
        }

        System.out.print("Enter the number of the product you want to sell (or 0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice < 1 || choice > count) {
            if (choice == 0) break;
            System.out.println("Invalid selection.");
            break;
        }

        int selectedItemID = itemIDs[choice - 1];
        String selectedName = names[choice - 1];
        int selectedValue = values[choice - 1];
        int maxQty = quantities[choice - 1];

        System.out.print("How many " + selectedName + " do you want to sell? (Max: " + maxQty + "): ");
        int qty = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (qty <= 0 || qty > maxQty) {
            System.out.println("Invalid quantity.");
            break;
        }

        int totalGain = selectedValue * qty;

        // Remove from inventory
        String updateInv = "UPDATE inventories SET quantity = quantity - ? WHERE playerID = ? AND itemID = ?";
        PreparedStatement updateStmt = conn.prepareStatement(updateInv);
        updateStmt.setInt(1, qty);
        updateStmt.setInt(2, playerId);
        updateStmt.setInt(3, selectedItemID);
        updateStmt.executeUpdate();

        // Add money to wallet
        String updateWallet = "UPDATE players SET wallet = wallet + ? WHERE playerID = ?";
        PreparedStatement walletStmt = conn.prepareStatement(updateWallet);
        walletStmt.setInt(1, totalGain);
        walletStmt.setInt(2, playerId);
        walletStmt.executeUpdate();

        System.out.println("You sold " + qty + " " + selectedName + " for " + totalGain + "!");
    } catch (SQLException e) {
        System.out.println("Error selling crops: " + e.getMessage());
    }
    break;
            case "0":
                // Return to main menu
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
}

public static int getProduceId(int seedItemId) {
    // Map seed itemIDs to grown product itemIDs
    switch (seedItemId) {
        case 1: return 6; // Turnip Seed -> Turnip
        case 2: return 7; // Carrot Seed -> Carrot
        case 3: return 8; // Potato Seed -> Potato
        case 4: return 9; // Tomato Seed -> Tomato
        case 5: return 10; // Pumpkin Seed -> Pumpkin
        case 11: return 16; // Chicken -> Egg
        case 12: return 17; // Cow -> Milk
        case 13: return 18; // Pig -> Truffle
        case 14: return 19; // Sheep -> Wool
        case 15: return 20; // Bee -> Honey
        default: return -1;
    }
}
}
