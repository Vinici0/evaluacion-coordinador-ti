-- Creación de la tabla "accounts"
CREATE TABLE IF NOT EXISTS accounts
(
    id              SERIAL PRIMARY KEY,
    account_number  VARCHAR(20)      NOT NULL UNIQUE,
    account_type    VARCHAR(20)      NOT NULL,
    initial_balance DOUBLE PRECISION NOT NULL,
    status          BOOLEAN DEFAULT TRUE,
    client_id       INTEGER          NOT NULL
);

-- Creación de la tabla "movements"
CREATE TABLE IF NOT EXISTS movements
(
    id            SERIAL PRIMARY KEY,
    date          DATE             NOT NULL,
    movement_type VARCHAR(20)      NOT NULL,
    amount        DOUBLE PRECISION NOT NULL,
    balance       DOUBLE PRECISION NOT NULL,
    account_id    INTEGER          NOT NULL REFERENCES accounts (id)
);
