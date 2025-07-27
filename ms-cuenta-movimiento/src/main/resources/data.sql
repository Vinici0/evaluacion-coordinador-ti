-- Inserción de datos en la tabla "accounts" (mínimo 4 registros)
-- Se asume que los IDs de clients insertados son 1, 2, 3 y 4 respectivamente.
INSERT INTO accounts (account_number, account_type, initial_balance, status, client_id)
VALUES ('1234567890', 'AHORRO', 1000.00, TRUE, 1),
       ('2345678901', 'CORRIENTE', 2000.00, TRUE, 2),
       ('3456789012', 'AHORRO', 1500.00, TRUE, 3),
       ('4567890123', 'CORRIENTE', 3000.00, TRUE, 4)
ON CONFLICT (account_number) DO NOTHING;

-- Inserción de datos en la tabla "movements" (mínimo 4 registros)
-- Se asume que los IDs de accounts insertados son 1, 2, 3 y 4 respectivamente.
INSERT INTO movements (date, movement_type, amount, balance, account_id)
VALUES ('2025-02-01', 'DEPOSIT', 500.00, 1500.00, 1),
       ('2025-02-02', 'WITHDRAWAL', -200.00, 1800.00, 2),
       ('2025-02-03', 'DEPOSIT', 300.00, 1800.00, 3),
       ('2025-02-04', 'WITHDRAWAL', -100.00, 2900.00, 4)
ON CONFLICT DO NOTHING;
