set search_path to public;

create table admin
(
    id       serial primary key,
    username varchar(50)  not null unique,
    password varchar(255) not null
);

create table product
(
    id    serial primary key,
    name  varchar(100)   not null,
    brand varchar(50)    not null,
    price decimal(12, 2) not null,
    stock int            not null
);

create table customer
(
    id      serial primary key,
    name    varchar(100) not null,
    phone   varchar(20),
    email   varchar(100) unique,
    address varchar(255)
);

create table invoice
(
    id           serial primary key,
    customer_id  int references customer (id),
    created_at   timestamp default current_timestamp,
    total_amount decimal(12, 2) not null
);

create table invoice_details
(
    id         serial primary key,
    invoice_id int references invoice (id),
    product_id int references product (id),
    quantity   int            not null,
    unit_price decimal(12, 2) not null
);

-- Xóa dữ liệu cũ để reset ID về 1 cho đồng bộ (nếu cần)
TRUNCATE TABLE invoice_details, invoice, customer, product, admin RESTART IDENTITY;

-- 1. Admin
INSERT INTO admin(username, password)
VALUES ('vanh', '123'),
       ('thang', '456');

-- 2. Product (Sẽ có ID từ 1 đến 8)
INSERT INTO product (name, brand, price, stock)
VALUES ('iPhone 15 Pro', 'Apple', 28000000.00, 25),           -- ID 1
       ('Samsung S24 Ultra', 'Samsung', 26500000.00, 15),-- ID 2
       ('Sony WH-1000XM5', 'Sony', 7500000.00, 30),           -- ID 3
       ('iPad Air M2', 'Apple', 16000000.00, 10),             -- ID 4
       ('Chuột Gaming G502', 'Logitech', 1200000.00, 40),-- ID 5
       ('Loa Marshall Emberton', 'Marshall', 3800000.00, 12), -- ID 6
       ('Sạc dự phòng Anker 20k', 'Anker', 950000.00, 100),   -- ID 7
       ('Cáp USB-C 2m', 'Baseus', 150000.00, 200);
-- ID 8

-- 3. Customer (ID từ 1 đến 6)
INSERT INTO customer (name, phone, email, address)
VALUES ('Lê Minh Anh', '0922111222', 'minhanh@gmail.com', '10 Láng Hạ, HN'),
       ('Hoàng Đức Thịnh', '0933444555', 'thinhhd@gmail.com', '22 Nguyễn Huệ, HCM'),
       ('Phạm Thanh Thảo', '0944555666', 'thaoptt@yahoo.com', '15 Lê Duẩn, ĐN'),
       ('Nguyễn Quốc Huy', '0955666777', 'huynguyen@outlook.com', '101 Quang Trung, HCM'),
       ('Vũ Minh Đức', '0966777888', 'ducvm@gmail.com', '55 Trần Hưng Đạo, HP'),
       ('Đỗ Thùy Linh', '0977888999', 'linhdt@gmail.com', '88 Lý Thường Kiệt, HN');

-- 4. Invoice (ID từ 1 đến 6)
INSERT INTO invoice (customer_id, total_amount)
VALUES (3, 29200000.00), -- ID 1 (Thảo mua iPhone + Chuột)
       (4, 7500000.00),  -- ID 2 (Huy mua Sony)
       (5, 1100000.00),  -- ID 3 (Đức mua Sạc + Cáp)
       (6, 30300000.00), -- ID 4 (Linh mua Samsung + Loa)
       (1, 16000000.00), -- ID 5 (Anh mua iPad)
       (2, 28000000.00);
-- ID 6 (Thịnh mua iPhone)

-- 5. Invoice Details (Đã chỉnh lại invoice_id và product_id cho khớp)
INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price)
VALUES (1, 1, 1, 28000000.00), -- Hóa đơn 1, sản phẩm 1 (iPhone)
       (1, 5, 1, 1200000.00),  -- Hóa đơn 1, sản phẩm 5 (Chuột)
       (2, 3, 1, 7500000.00),  -- Hóa đơn 2, sản phẩm 3 (Sony)
       (3, 7, 1, 950000.00),   -- Hóa đơn 3, sản phẩm 7 (Sạc)
       (3, 8, 1, 150000.00),   -- Hóa đơn 3, sản phẩm 8 (Cáp)
       (4, 2, 1, 26500000.00), -- Hóa đơn 4, sản phẩm 2 (Samsung)
       (4, 6, 1, 3800000.00),  -- Hóa đơn 4, sản phẩm 6 (Loa)
       (5, 4, 1, 16000000.00), -- Hóa đơn 5, sản phẩm 4 (iPad)
       (6, 1, 1, 28000000.00); -- Hóa đơn 6, sản phẩm 1 (iPhone)