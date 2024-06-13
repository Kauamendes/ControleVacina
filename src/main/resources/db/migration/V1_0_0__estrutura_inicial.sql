CREATE TABLE IF NOT EXISTS BAIRRO (
    ID SERIAL PRIMARY KEY,
    NOME VARCHAR(50),
);

CREATE TABLE IF NOT EXISTS VACINA (
    ID SERIAL PRIMARY KEY,
    NOME VARCHAR(200),
    DOENCAS_EVITADAS VARCHAR(300),
    DOSAGEM BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS VACINA_BAIRRO (
    ID SERIAL PRIMARY KEY,
    VACINA_ID INTEGER,
    BAIRRO_ID INTEGER,
    DOSE VARCHAR(2),
    DATA_APLICACAO TIMESTAMP WITHOUT TIME ZONE DEFAULT timezone('America/Sao_Paulo' :: TEXT, now()),
    APLICADOR VARCHAR(200),
    CREATED_DATE TIMESTAMP WITHOUT TIME ZONE DEFAULT timezone('America/Sao_Paulo' :: TEXT, now()),
    MODIFIED_DATE TIMESTAMP WITHOUT TIME ZONE DEFAULT timezone('America/Sao_Paulo' :: TEXT, now())
    FOREIGN KEY (VACINA_ID) REFERENCES VACINA(ID),
    FOREIGN KEY (BAIRRO_ID) REFERENCES BAIRRO(ID)
);

CREATE TABLE IF NOT EXISTS USUARIO (
    ID SERIAL PRIMARY KEY,
    LOGIN VARCHAR(100) UNIQUE,
    SENHA VARCHAR(50),
    CARGO VARCHAR(100),
    CREATED_DATE TIMESTAMP WITHOUT TIME ZONE DEFAULT timezone('America/Sao_Paulo' :: TEXT, now()),
    MODIFIED_DATE TIMESTAMP WITHOUT TIME ZONE DEFAULT timezone('America/Sao_Paulo' :: TEXT, now())
);
