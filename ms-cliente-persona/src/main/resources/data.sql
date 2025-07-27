-- Inserción de datos en la tabla "clients"
INSERT INTO clients (name, gender, age, identification, address, phone, client_id, password, status) VALUES
('Juan Pérez', 'MALE', 30, 'ID123456', 'Calle Falsa 123', '555-1234', 'CLI-1111-UUID', 'pass123', TRUE),
('María García', 'FEMALE', 25, 'ID234567', 'Avenida Siempre Viva 742', '555-5678', 'CLI-2222-UUID', 'pass234', TRUE),
('Carlos López', 'MALE', 40, 'ID345678', 'Boulevard de los Sueños 10', '555-9012', 'CLI-3333-UUID', 'pass345', TRUE),
('Ana Torres', 'FEMALE', 35, 'ID456789', 'Plaza Mayor 5', '555-3456', 'CLI-4444-UUID', 'pass456', TRUE);

-- Inserción de datos en la tabla "accounts"
INSERT INTO accounts (account_number, account_type, initial_balance, status, client_id) VALUES
('1234567890', 'SAVINGS', 1000.00, TRUE, 1),
('2345678901', 'CURRENT', 2000.00, TRUE, 2),
('3456789012', 'SAVINGS', 1500.00, TRUE, 3),
('4567890123', 'CURRENT', 3000.00, TRUE, 4);

-- Inserción de datos en la tabla "movements"
INSERT INTO movements (date, movement_type, amount, balance, account_id) VALUES
('2025-01-15', 'DEPOSIT', 500.00, 1500.00, 1),
('2025-01-16', 'WITHDRAWAL', -200.00, 1800.00, 2),
('2025-01-17', 'DEPOSIT', 300.00, 1800.00, 3),
('2025-01-18', 'WITHDRAWAL', -100.00, 2900.00, 4);
