
-- CREATE DATABASE
CREATE DATABASE IF NOT EXISTS farming_game;
USE farming_game;

-- PLAYER TABLE
CREATE TABLE IF NOT EXISTS players (
    playerID INT AUTO_INCREMENT PRIMARY KEY,                                            -- Unique player ID
    playername VARCHAR(50) UNIQUE NOT NULL,                                             -- Name of the player, must be unique
    wallet INT DEFAULT 150,                                                             -- Starting wallet balance
    current_day INT DEFAULT 1,                                                          -- Current day in the game
    current_season ENUM('Spring', 'Summer', 'Fall', 'Winter') DEFAULT 'Spring',         -- Current season in the game
    current_year INT DEFAULT 1                                                          -- Current year in the game
);

-- ITEMS TABLE
CREATE TABLE IF NOT EXISTS items (
    itemID INT PRIMARY KEY AUTO_INCREMENT,                                      -- Unique item ID
    itemname VARCHAR(50) NOT NULL,                                              -- Name of the item
    itemtype ENUM('crop', 'animal', 'tool', 'gift', 'product') NOT NULL,        -- Item Type                                               -- Special value of the item
    descript VARCHAR(255)                                                       -- Description of the item
);

-- INVENTORY TABLE
CREATE TABLE IF NOT EXISTS inventories (
    playerID INT,                                           -- The ID of the player
    itemID INT,                                             -- The ID of the item
    quantity INT DEFAULT 1,                                 -- The quantity of the item, default is 1

    PRIMARY KEY (playerID, itemID),                         -- Composite PK       
    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES items(itemID) ON DELETE CASCADE
);

-- CROP TABLE
CREATE TABLE IF NOT EXISTS crops (
    cropID INT PRIMARY KEY AUTO_INCREMENT,                  -- Unique crop ID
    cropname VARCHAR(50),                                   -- Name of the crop
    playerID INT,                                           -- The player who owns the crop
    itemID INT,                                             -- The item ID associated with the crop
    planted_day INT,                                        -- The day the crop was planted
    growth_time INT,                                        -- The number of days required for the crop to grow
    produceID INT,                                          -- The item ID of the produce after harvesting
    readytoharvest BOOLEAN DEFAULT FALSE,                   -- Indicates if the crop is ready to be harvested

    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE, 
    FOREIGN KEY (itemID) REFERENCES items(itemID),                         
    FOREIGN KEY (produceID) REFERENCES items(itemID)                        
);


-- ANIMALS TABLE
CREATE TABLE IF NOT EXISTS animals (
    animalID INT PRIMARY KEY AUTO_INCREMENT,                -- Unique animal ID
    playerID INT,                                           -- The player who owns the animal
    itemID INT,                                             -- The item ID associated with the animal
    animalname VARCHAR(50),                                 -- Name of the animal
    age INT DEFAULT 0,                                      -- Age of the animal, default is 0
    producedays INT,                                        -- Number of days required to produce
    produceID INT,                                          -- The item ID of the produce
    readytoharvest BOOLEAN DEFAULT FALSE,                   -- Indicates if the animal is ready for harvesting

    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES items(itemID),
    FOREIGN KEY (produceID) REFERENCES items(itemID)
);

-- SHOP INVENTORY TABLE
CREATE TABLE IF NOT EXISTS shop_inventory (
    shopID INT,                                             -- The ID of the shop
    itemID INT,                                             -- The ID of the item
    stock INT DEFAULT 9999,                                 -- The quantity of the item             
    price INT NOT NULL,                                     -- Price for this item at this shop

    PRIMARY KEY (shopID, itemID),
    FOREIGN KEY (shopID) REFERENCES shops(shopID) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);


-- NPCS TABLE
CREATE TABLE IF NOT EXISTS npcs (
    npcID INT PRIMARY KEY AUTO_INCREMENT,                  -- Unique NPC ID
    npcname VARCHAR(50) UNIQUE NOT NULL,                   -- Name of the NPC, must be unique
    givinggifttoday BOOLEAN DEFAULT FALSE                  -- Indicates if the NPC is giving a gift today
);

-- SHOPS TABLE
CREATE TABLE IF NOT EXISTS shops (
    shopID INT PRIMARY KEY AUTO_INCREMENT,                  -- Unique ID per shop
    shopname VARCHAR(50) NOT NULL,                          -- e.g., "General Store", "Animal Vendor"
    owner_npcID INT,                                        -- Optional: link to NPC who owns the shop

    FOREIGN KEY (owner_npcID) REFERENCES npcs(npcID)
);

-- RELATIONS TABLE
CREATE TABLE IF NOT EXISTS relations (
    playerID INT,                                          -- The player who has the relation with the NPC
    npcID INT,                                             -- The NPC with whom the player has a relation
    npchearts INT DEFAULT 0,                               -- The number of hearts the player has with the NPC
    last_gift_day INT,                                     -- Track when last gift was given
    gift_count_this_week INT,                              -- Track number of gifts given this week

    PRIMARY KEY (playerID, npcID),                         -- Composite PK
    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (npcID) REFERENCES npcs(npcID)
);

CREATE TABLE IF NOT EXISTS logs (
    logID INT AUTO_INCREMENT PRIMARY KEY,                   -- Unique log ID
    playerID INT,                                           -- The player who performed the action
    action TEXT,                                            -- The action that was performed (e.g. "gift npc", "collected product")
    season ENUM('Spring', 'Summer', 'Fall', 'Winter'),      -- The current season when the action was performed
    day INT,                                                -- The current day when the action was performed
    year INT,                                               -- The current year when the action was performed
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,           -- Timestamp when the action was performed

    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE
);

-- GIFT TABLE
CREATE TABLE IF NOT EXISTS gift_logs (
    giftID INT AUTO_INCREMENT PRIMARY KEY,                  -- Unique gift ID
    playerID INT,                                           -- The player who gave the gift
    npcID INT,                                              -- The NPC that received the gift
    itemID INT,                                             -- The item that was given as a gift
    season ENUM('Spring', 'Summer', 'Fall', 'Winter'),      -- The current season when the gift was given
    day INT,                                                -- The current day when the gift was given
    year INT,                                               -- The current year when the gift was given
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,           -- Timestamp when the gift was given

    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (npcID) REFERENCES npcs(npcID),
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);

-- HARVEST LOG TABLE
CREATE TABLE IF NOT EXISTS harvest_logs (
    harvestID INT AUTO_INCREMENT PRIMARY KEY,               -- Unique log ID
    playerID INT,                                           -- The player who harvested the crop
    cropID INT,                                             -- The crop that was harvested
    itemID INT,                                             -- The item that was produced by harvesting the crop
    quantity INT DEFAULT 1,                                 -- The quantity of the item that was produced by harvesting the crop
    season ENUM('Spring', 'Summer', 'Fall', 'Winter'),      -- The current season when the crop was harvested
    day INT,                                                -- The current day when the crop was harvested
    year INT,                                               -- The current year when the crop was harvested
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,           -- Timestamp when the crop was harvested

    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (cropID) REFERENCES crops(cropID),
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);


-- PRODUCT LOG TABLE
CREATE TABLE IF NOT EXISTS product_logs (
    productlogID INT AUTO_INCREMENT PRIMARY KEY,            -- Unique log ID
    playerID INT,                                           -- The player who produced the product
    animalID INT,                                           -- The animal that produced the product
    itemID INT,                                             -- The item that was produced
    quantity INT DEFAULT 1,                                 -- The quantity of the item that was produced
    season ENUM('Spring', 'Summer', 'Fall', 'Winter'),      -- The current season when the product was produced
    day INT,                                                -- The current day when the product was produced
    year INT,                                               -- The current year when the product was produced
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,           -- Timestamp when the product was produced


    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (animalID) REFERENCES animals(animalID),
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);

-- TRANSACTION TABLE
CREATE TABLE IF NOT EXISTS transactions (
    transactionID INT AUTO_INCREMENT PRIMARY KEY,           -- Unique transaction ID
    playerID INT,                                           -- The player who made the transaction
    shopID INT,                                             -- The shop where the transaction was made
    transaction_type ENUM('buy', 'sell') NOT NULL,          -- Type of transaction (buy or sell)
    itemID INT,                                             -- The item that was bought or sold
    quantity INT,                                           -- The quantity of the item that was bought or sold
    unit_price INT,                                         -- The price per unit of the item
    total_amount INT,                                       -- The total cost of the transaction
    season ENUM('Spring', 'Summer', 'Fall', 'Winter'),      -- The current season when the transaction was made
    day INT,                                                -- The current day when the transaction was made
    year INT,                                               -- The current year when the transaction was made
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,           -- Timestamp when the transaction was made

    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES items(itemID),
    FOREIGN KEY (shopID) REFERENCES shops(shopID)
);

INSERT INTO items (itemID, itemname, itemtype,  specialvalue, descript, quantity)
VALUES
(1, 'Turnip Seed', 'crop', 1, 'A fast-growing root vegetable. Grows in 1 day.', 9999),
(2, 'Carrot Seed', 'crop', 2, 'A crunchy orange vegetable. Grows in 2 days.', 9999),
(3, 'Potato Seed', 'crop', 5, 'A starchy tuber. Grows in 3 days.', 9999),
(4, 'Tomato Seed', 'crop', 8, 'A juicy red fruit. Grows in 2 days.', 9999),
(5, 'Pumpkin Seed', 'crop', 16, 'A large orange squash. Grows in 5 days.', 9999),

(6, 'Turnip', 'product', 30, 'A fast-growing root vegetable. Grows in 1 day.', 0),
(7, 'Carrot', 'product', 60, 'A crunchy orange vegetable. Grows in 2 days.', 0),
(8, 'Potato', 'product', 100, 'A starchy tuber. Grows in 3 days.', 0),
(9, 'Tomato', 'product', 60, 'A juicy red fruit. Grows in 2 days.', 0),
(10,'Pumpkin', 'product', 220, 'A large orange squash. Grows in 5 days.', 0),

(11, 'Chicken', 'animal', 3, 'A chicken that lays eggs. Ready after 3 days.', 9999),
(12, 'Cow', 'animal', 5, 'A cow that makes milk. Ready in 5 days.', 9999),
(13, 'Pig', 'animal', 8, 'A Pig that grows truffles. Ready in 8 days.', 9999),
(14, 'Sheep', 'animal', 10, 'A Sheep with shaveable wool. Ready in 10 days.', 9999),
(15, 'Bee', 'animal', 15, 'A honey-making bee. Ready in 15 days.', 9999),

(16, 'Egg', 'product', 30, 'A nice egg.', 0),
(17, 'Milk', 'product', 50, 'Moo moo milkers', 0),
(18, 'Truffle', 'product', 100, 'Did you know pigs can produce truffles?', 0),
(19, 'Wool', 'product', 200, 'Yes sir, yes sir, three bags full!.', 0),
(20,'Honey', 'product', 500, 'Yes darling?', 0);
