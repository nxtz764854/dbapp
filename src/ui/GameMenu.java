package ui;

import model.*;
import service.*;

import java.util.Scanner;
import java.util.List;

public class GameMenu {
    private Scanner scanner;
    private GameService gameService;
    private PlayerService playerService;
    private CropService cropService;
    private ItemService itemService;
    private AnimalService animalService;
    private InventoryService inventoryService;
    private NPCService npcService;
    private RelationService relationService;

    public GameMenu(
        GameService gameService,
        PlayerService playerService,
        CropService cropService,
        ItemService itemService,
        AnimalService animalService,
        InventoryService inventoryService,
        NPCService npcService,
        RelationService relationService
    ) {
        this.scanner = new Scanner(System.in);
        this.gameService = gameService;
        this.playerService = playerService;
        this.cropService = cropService;
        this.itemService = itemService;
        this.animalService = animalService;
        this.inventoryService = inventoryService;
        this.npcService = npcService;
        this.relationService = relationService;
    }

    public void startGameLoop(int playerID) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Main Menu ====");
            System.out.println("1. View Farm");
            System.out.println("2. Advance Day");
            System.out.println("3. View Inventory");
            System.out.println("4. Gift an NPC");
            System.out.println("5. Harvest Ready Crops and Animals");
            System.out.println("6. Exit Game");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewFarm(playerID);
                    break;
                case "2":
                    gameService.advanceDay(playerID);
                    System.out.println("A new day has begun!");
                    break;
                case "3":
                    viewInventory(playerID);
                    break;
                case "4":
                    giftNPC(playerID);
                    break;
                case "5":
                    harvest(playerID);
                    break;
                case "6":
                    running = false;
                    System.out.println("Thanks for playing!");
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void viewFarm(int playerID) {
        System.out.println("\n--- Crops ---");
        for (Crop crop : cropService.getCropsByPlayerID(playerID)) {
            System.out.printf("ID: %d | Name: %s | Days Left: %d | Ready: %b\n",
                    crop.getCropID(), crop.getCropname(), crop.getGrowth_time(), crop.isReadytoharvest());
        }

        System.out.println("\n--- Animals ---");
        for (Animal animal : animalService.getAnimalsByPlayerID(playerID)) {
            System.out.printf("ID: %d | Name: %s | ProduceDays: %d | Ready: %b\n",
                    animal.getAnimalID(), animal.getAnimalname(), animal.getProducedays(), animal.isReadytoharvest());
        }
    }

    private void viewInventory(int playerID) {
        System.out.println("\n--- Inventory ---");
        for (Inventory inv : inventoryService.getInventoryByPlayerID(playerID)) {
            Item item = itemService.getItemByID(inv.getItemID());
            System.out.printf("Item: %s | Quantity: %d\n", item.getItemname(), inv.getQuantity());
        }
    }

    private void giftNPC(int playerID) {
        System.out.print("\nEnter NPC name to gift: ");
        String npcname = scanner.nextLine();
        NPC npc = npcService.getNPCByName(npcname);
        if (npc == null) {
            System.out.println("NPC not found.");
            return;
        }

        System.out.print("Enter item name to gift: ");
        String itemname = scanner.nextLine();
        Item item = itemService.getItemByName(itemname);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        boolean success = gameService.giveGift(playerID, npc.getNpcID(), item.getItemID());

        if (success) {
            System.out.println("You gifted " + itemname + " to " + npcname + ". They seem happy!");
        } else {
            System.out.println(npcname + " has already received a gift today or you don't have the item.");
        }
    }


    private void harvest(int playerID) {
        System.out.println("\nHarvesting crops...");
        gameService.harvestCrops(playerID);

        System.out.println("Harvesting animals...");
        gameService.harvestAnimals(playerID);

        System.out.println("All harvests completed!");
    }
}
