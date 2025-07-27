
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
    itemtype ENUM('crop', 'animal', 'tool', 'gift', 'product') NOT NULL,        -- Item Type                                        
    descript VARCHAR(255),                                                       -- Description of the item
    itemprice INT DEFAULT 0,                                                     -- Price of the item
    buyable BOOLEAN DEFAULT FALSE                                                -- Flag indicating if the item is buyable
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

-- NPCS TABLE
CREATE TABLE IF NOT EXISTS npcs (
    npcID INT PRIMARY KEY AUTO_INCREMENT,                  -- Unique NPC ID
    npcname VARCHAR(50) UNIQUE NOT NULL,                   -- Name of the NPC, must be unique
    givinggifttoday BOOLEAN DEFAULT FALSE                  -- Indicates if the NPC is giving a gift today
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
    transactionID INT AUTO_INCREMENT PRIMARY KEY,           -- Unique transaction IDplayers
    playerID INT,                                           -- The player who made the transaction
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
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);

INSERT INTO items (itemname, itemtype, descript, itemprice, buyable) VALUES
-- Crops
('Turnip Seeds', 'crop', 'Basic spring crop. Fast-growing.', 20, TRUE),
('Carrot Seeds', 'crop', 'Crunchy and nutritious crop.', 25, TRUE),
('Tomato Seeds', 'crop', 'Juicy red tomatoes. Great in salads.', 35, TRUE),

-- Animals
('Chicken', 'animal', 'Produces eggs every few days.', 500, TRUE),
('Cow', 'animal', 'Produces milk every few days.', 1000, TRUE),

-- Tools
('Hoe', 'tool', 'Used for tilling soil.', 0, FALSE),
('Watering Can', 'tool', 'Used for watering crops.', 0, FALSE),

-- Gifts
('Flower Bouquet', 'gift', 'A lovely bouquet. Brightens anyone’s day.', 80, TRUE),
('Book of Poems', 'gift', 'Touching verses. Appreciated by artistic NPCs.', 100, TRUE),

-- Products
('Egg', 'product', 'Laid by chickens.', 60, FALSE),
('Milk', 'product', 'Produced by cows.', 120, FALSE),
('Turnip', 'product', 'Harvested turnip crop.', 40, FALSE),
('Carrot', 'product', 'Harvested carrot crop.', 50, FALSE),
('Tomato', 'product', 'Harvested tomato crop.', 70, FALSE);

INSERT INTO npcs (npcname, givinggifttoday) VALUES
('Dionysus', FALSE),
('Mari', FALSE),
('Ena', FALSE),
('Tommy', FALSE),
('Farmer Joe', FALSE);

