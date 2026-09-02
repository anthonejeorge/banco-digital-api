ALTER TABLE contas 
ADD CONSTRAINT checa_saldo_negativo CHECK (saldo >= 0);