-- Insert accounts
INSERT INTO accounts (client, account_number, account_type, initial_balance, status) VALUES
('Jose Lema', '478758', 'Ahorros', 2000.00, true),
('Marianela Montalvo', '225487', 'Corriente', 100.00, true),
('Juan Osorio', '495878', 'Ahorros', 0.00, true),
('Marianela Montalvo', '496825', 'Ahorros', 540.00, true),
('Jose Lema', '585545', 'Corriente', 1000.00, true);

-- Insert transacciones
INSERT INTO transactions (date, type, amount, balance, account_number) VALUES
('2022-10-02', 'DEBIT', 575.00, 1425.00, '478758'),
('2022-10-02', 'CREDIT', 600.00, 700.00, '225487'),
('2022-08-02', 'DEBIT', 150.00, 150.00, '495878'),
('2022-08-02', 'CREDIT', 540.00, 0.00, '496825');