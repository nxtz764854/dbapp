package ui;

import model.Player;
import model.Animal;
import model.Crop;
import model.Item;
import model.Inventory;
import model.NPC;
import model.Relation;

import service.PlayerService;
import service.AnimalService;
import service.CropService;
import service.ItemService;
import service.InventoryService;
import service.NPCService;
import service.RelationService;
import service.GameService;

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

        if (npc.isGivinggifttoday()) {
            System.out.println(npc.getNpcname() + " has already received a gift today.");
            return;
        }

        System.out.print("Enter item name to gift: ");
        String itemname = scanner.nextLine();
        Item item = itemService.getItemByName(itemname);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        boolean removed = inventoryService.removeItemFromInventory(playerID, item.getItemID(), 1);
        if (!removed) {
            System.out.println("You don’t have that item.");
            return;
        }

        relationService.incrementHearts(playerID, npc.getNpcID());
        npcService.updateGivingGiftToday(npc.getNpcID(), true);

        System.out.println("You gifted " + itemname + " to " + npcname + ". They seem happy!");
    }

    private void harvest(int playerID) {
        System.out.println("\nHarvesting crops...");
        cropService.markCropsHarvested(playerID); // Assumes this harvests and gives produce

        System.out.println("Harvesting animals...");
        animalService.markAnimalsHarvested(playerID); // Assumes this resets ready flag and adds produce

        System.out.println("All harvests completed!");
    }
}
