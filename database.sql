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
(1, 'Điện thoại'),
(2, 'Laptop'),
(3, 'Phụ kiện');

-- Insert Customers
-- BCrypt encoded passwords for admin123 and customer123
INSERT INTO customer (id, name, username, password, customer_since, role) VALUES
(1, 'Admin User', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2024-01-01', 'ADMIN'),
(2, 'Customer One', 'customer', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2024-01-15', 'CUSTOMER');

-- Insert Products
INSERT INTO product (id, name, price, in_stock, category_id) VALUES
(1, 'iPhone 15', 25000000, true, 1),
(2, 'Samsung Galaxy S24', 22000000, true, 1),
(3, 'MacBook Pro M3', 45000000, true, 2),
(4, 'Dell XPS 15', 35000000, true, 2),
(5, 'Tai nghe AirPods Pro', 6000000, true, 3),
(6, 'Chuột Logitech MX Master', 2500000, false, 3);

-- Insert Comments
INSERT INTO comment (id, text, product_id) VALUES
(1, 'Sản phẩm rất tốt!', 1),
(2, 'Giá hợp lý, chất lượng cao', 3),
(3, 'Máy chạy mượt, pin trâu!', 1),
(4, 'Giá hơi đắt nhưng xứng đáng', 2);

-- Insert Orders
INSERT INTO orders (id, date, customer_id) VALUES
(1, '2024-01-20 10:30:00', 2);

-- Insert Order Lines
INSERT INTO order_line (id, amount, purchase_price, order_id, product_id) VALUES
(1, 1, 25000000, 1, 1),
(2, 2, 6000000, 1, 5);
