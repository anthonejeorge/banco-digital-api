-- Create the table
CREATE TABLE contas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    saldo NUMERIC(15, 2) NOT NULL DEFAULT 0.00
);

-- Insert initial data
INSERT INTO contas (nome, saldo) VALUES ('Joe Silva', 500.00);
INSERT INTO contas (nome, saldo) VALUES ('Karl Souza', 450.50);
INSERT INTO contas (nome, saldo) VALUES ('John Santos', 50.50);
INSERT INTO contas (nome, saldo) VALUES ('Maria Oliveira', 4500.00);
INSERT INTO contas (nome, saldo) VALUES ('Darcy Ribeiro', 4.00);
INSERT INTO contas (nome, saldo) VALUES ('Mc Maya', 48000.00);