-- ユーザーテーブルへのデータ挿入
INSERT INTO users (username, email, password, role) VALUES
('john_doe', 'john@example.com', 'password123', 'USER'),
('jane_doe', 'jane@example.com', 'password456', 'ADMIN');

-- 商品テーブルへのデータ挿入
INSERT INTO products (name, description, price) VALUES 
('ランニングシューズ', '軽量で通気性のあるランニングシューズ', 9900),
('レザーブーツ', 'アウトドアに適した耐久性のあるレザーブーツ', 12900);

-- サイズテーブル
INSERT INTO sizes (value) VALUES
(25.0),
(26.0),
(27.0),
(28.0),
(29.0);

-- バリエーション
INSERT INTO stocks (product_id, size_id, quantity) VALUES
(1, 1, 100),  -- ランニングシューズ, 25.0cm
(1, 2, 80),   -- ランニングシューズ, 26.0cm
(1, 3, 60),   -- ランニングシューズ, 27.0cm
(2, 4, 50),  -- レザーブーツ, 28.0cm
(2, 5, 40);  -- レザーブーツ, 29.0cm

-- バリエーション
INSERT INTO images (image_url, alt_text, product_id) VALUES
('/images/sneaker1.jpg', 'sneaker', 1),
('/images/slip-ons1.jpg', 'slip-ons', 2);

-- 注文テーブルへのデータ挿入
INSERT INTO orders (order_date, user_id) VALUES
(NOW(), 1),
(NOW(), 2);

-- 注文項目テーブルへのデータ挿入
INSERT INTO order_items (order_id, product_id, quantity) VALUES
(1, 1, 1),
(1, 2, 2),
(2, 2, 1);
