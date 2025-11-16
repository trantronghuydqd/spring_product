CREATE DATABASE IF NOT EXISTS product_th;
USE product_th;

-- Drop tables if exists (for clean setup)
DROP TABLE IF EXISTS order_line;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS customer;

-- Create Category table
CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- Create Customer table
CREATE TABLE customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    customer_since DATE,
    role VARCHAR(50) NOT NULL
);

-- Create Product table
CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    in_stock BOOLEAN DEFAULT true,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
);

-- Create Comment table
CREATE TABLE comment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    text TEXT,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Create Orders table
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATETIME NOT NULL,
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

-- Create OrderLine table
CREATE TABLE order_line (
    id INT PRIMARY KEY AUTO_INCREMENT,
    amount INT NOT NULL,
    purchase_price DECIMAL(15,2) NOT NULL,
    order_id INT,
    product_id INT,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Insert Categories
INSERT INTO category (id, name) VALUES
(1, 'Thuốc bảo vệ thực vật'),
(2, 'Phân bón'),
(3, 'Tạp hóa');

-- Insert Customers
-- BCrypt encoded passwords for admin123 and customer123
INSERT INTO customer (id, name, username, password, customer_since, role) VALUES
-- (1, 'Admin User', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2024-01-01', 'ADMIN'),
-- (2, 'Customer One', 'customer', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2024-01-15', 'CUSTOMER');

-- Insert Products
INSERT INTO product (id, name, price, in_stock, category_id) VALUES
(1, 'Thuốc trừ sâu Bayer 500ml', 150000, true, 1),
(2, 'Thuốc diệt cỏ Glyphosate 1L', 180000, true, 1),
(3, 'Phân NPK 16-16-8 (50kg)', 450000, true, 2),
(4, 'Phân DAP (50kg)', 550000, true, 2),
(5, 'Dầu ăn Neptune 5L', 200000, true, 3),
(6, 'Nước mắm Nam Ngư 500ml', 35000, false, 3);

-- Insert Comments
INSERT INTO comment (id, text, product_id) VALUES
(1, 'Thuốc rất hiệu quả, diệt sâu nhanh!', 1),
(2, 'Phân bón tốt, cây xanh tươi', 3),
(3, 'Sử dụng dễ dàng, hiệu quả cao!', 1),
(4, 'Giá cả hợp lý, chất lượng đảm bảo', 2);

-- Insert Orders
INSERT INTO orders (id, date, customer_id) VALUES
(1, '2024-01-20 10:30:00', 2);

-- Insert Order Lines
INSERT INTO order_line (id, amount, purchase_price, order_id, product_id) VALUES
(1, 2, 150000, 1, 1),
(2, 3, 200000, 1, 5);
