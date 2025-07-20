-- Crops
INSERT INTO items (itemname, itemtype, descript, itemprice, buyable) VALUES
('Parsnip Seeds', 'crop', 'Basic spring crop', 20, TRUE),
('Cauliflower Seeds', 'crop', 'Large spring vegetable', 80, TRUE),
('Corn Seeds', 'crop', 'Grows in summer and fall', 150, TRUE);

-- Animal types (e.g., livestock)
INSERT INTO items (itemname, itemtype, descript, itemprice, buyable) VALUES
('Chicken', 'animal', 'Produces eggs every few days', 800, TRUE),
('Cow', 'animal', 'Produces milk', 1500, TRUE);

-- Tools (not buyable in-game, but for logic reference or expansion)
INSERT INTO items (itemname, itemtype, descript, itemprice, buyable) VALUES
('Hoe', 'tool', 'Used for tilling soil', 0, FALSE),
('Watering Can', 'tool', 'Used to water crops', 0, FALSE);

-- Gifts (used in gifting logic, not buyable directly)
INSERT INTO items (itemname, itemtype, descript, itemprice, buyable) VALUES
('Amethyst', 'gift', 'A violet-colored gem', 100, FALSE),
('Coconut', 'gift', 'A tasty tropical fruit', 50, FALSE),
('Salad', 'gift', 'Healthy veggie dish', 220, FALSE),
('Wine', 'gift', 'Aged fruit beverage', 400, FALSE);

-- Products (produced by animals or crops)
INSERT INTO items (itemname, itemtype, descript, itemprice, buyable) VALUES
('Parsnip', 'product', 'Harvested root vegetable', 35, FALSE),
('Cauliflower', 'product', 'Harvested spring crop', 175, FALSE),
('Corn', 'product', 'Multi-season grain crop', 100, FALSE),
('Egg', 'product', 'Laid by chickens', 50, FALSE),
('Milk', 'product', 'Collected from cows', 125, FALSE);
