
-- Create database
CREATE DATABASE IF NOT EXISTS farming_game;
USE farming_game;

-- PLAYER TABLE
CREATE TABLE IF NOT EXISTS players (
    playerID INT AUTO_INCREMENT PRIMARY KEY,
    playername VARCHAR(50) UNIQUE NOT NULL,
    wallet INT DEFAULT 150,
    current_day INT DEFAULT 1,
    current_season ENUM('Spring', 'Summer', 'Fall', 'Winter') DEFAULT 'Spring',
    current_year INT DEFAULT 1
);

-- ITEMS TABLE
CREATE TABLE IF NOT EXISTS items (
    itemID INT PRIMARY KEY AUTO_INCREMENT,
    itemname VARCHAR(50) NOT NULL,
    itemtype ENUM('crop', 'animal', 'tool', 'gift', 'product') NOT NULL,
    specialvalue INT DEFAULT 0,
    descript VARCHAR(255),
    quantity INT DEFAULT 0
);

-- INVENTORY TABLE
CREATE TABLE IF NOT EXISTS inventories (
    playerID INT,
    itemID INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (playerID, itemID),
    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES items(itemID) ON DELETE CASCADE
);

-- CROP TABLE
CREATE TABLE IF NOT EXISTS crops (
    cropID INT PRIMARY KEY AUTO_INCREMENT,
    playerID INT,
    itemID INT,
    cropname VARCHAR(50),
    growth_time INT,
    produceID INT,
    readytoharvest BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);

-- ANIMALS TABLE
CREATE TABLE IF NOT EXISTS animals (
    animalID INT PRIMARY KEY AUTO_INCREMENT,
    playerID INT,
    itemID INT,
    animalname VARCHAR(50),
    age INT,
    producedays INT,
    produceID INT,
    readytoharvest BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE,
    FOREIGN KEY (itemID) REFERENCES items(itemID)
);

-- FARM TABLE
CREATE TABLE IF NOT EXISTS farms (
    playerID INT PRIMARY KEY,
    cropID INT,
    animalID INT,
    FOREIGN KEY (playerID) REFERENCES players(playerID) ON DELETE CASCADE
    FOREIGN KEY (cropID) REFERENCES crops(cropID) ON DELETE CASCADE
    FOREIGN KEY (animalID) REFERENCES animals(animalID) ON DELETE CASCADE
);

-- NPCS TABLE
CREATE TABLE IF NOT EXISTS npcs (
    npcID INT PRIMARY KEY AUTO_INCREMENT,
    npcname VARCHAR(50) UNIQUE NOT NULL,
    givinggifttoday BOOLEAN DEFAULT FALSE
);

-- RELATIONS TABLE
CREATE TABLE IF NOT EXISTS relations (
    playerID INT,
    npcID INT,
    npchearts INT DEFAULT 0,
    PRIMARY KEY (playerID, npcID),
    FOREIGN KEY (playerID) REFERENCES player(playerID) ON DELETE CASCADE,
    FOREIGN KEY (npcID) REFERENCES npcs(npcID)
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
