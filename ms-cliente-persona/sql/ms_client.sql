-- Creación de la tabla "clients" para MS-Cliente-Persona
CREATE TABLE IF NOT EXISTS clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    age INTEGER NOT NULL,
    identification VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(200),
    phone VARCHAR(20),
    client_id VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    status BOOLEAN DEFAULT TRUE
);

-- Inserción de datos iniciales en la tabla "clients"
INSERT INTO clients (name, gender, age, identification, address, phone, client_id, password, status) VALUES
('Juan Pérez', 'MALE', 30, 'ID123456', 'Calle Falsa 123', '555-1234', 'CLI-1111-UUID', 'pass123', TRUE),
('María García', 'FEMALE', 25, 'ID234567', 'Avenida Siempre Viva 742', '555-5678', 'CLI-2222-UUID', 'pass234', TRUE),
('Carlos López', 'MALE', 40, 'ID345678', 'Boulevard de los Sueños 10', '555-9012', 'CLI-3333-UUID', 'pass345', TRUE),
('Ana Torres', 'FEMALE', 35, 'ID456789', 'Plaza Mayor 5', '555-3456', 'CLI-4444-UUID', 'pass456', TRUE)
ON CONFLICT (identification) DO NOTHING;
