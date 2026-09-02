CREATE TABLE auditoria (
    id BIGSERIAL PRIMARY KEY,
    remetente_id BIGINT NOT NULL,
    favorecida_id BIGINT NOT NULL,
    valor NUMERIC(19, 2) NOT NULL,
    data_hora_transacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);