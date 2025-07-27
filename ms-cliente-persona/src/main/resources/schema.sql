-- Eliminar tablas si existen (para development)
DROP TABLE IF EXISTS movements CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS clients CASCADE;

-- Creación de la tabla "clients"
CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
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

-- Creación de la tabla "accounts"
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    initial_balance DOUBLE PRECISION NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    client_id BIGINT NOT NULL REFERENCES clients(id) ON DELETE CASCADE
);

-- Creación de la tabla "movements"
CREATE TABLE movements (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    movement_type VARCHAR(20) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_clients_client_id ON clients(client_id);
CREATE INDEX idx_clients_identification ON clients(identification);
CREATE INDEX idx_accounts_account_number ON accounts(account_number);
CREATE INDEX idx_accounts_client_id ON accounts(client_id);
CREATE INDEX idx_movements_account_id ON movements(account_id);
CREATE INDEX idx_movements_date ON movements(date);
