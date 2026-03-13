CREATE TABLE IF NOT EXISTS USUARIO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    login VARCHAR(255) UNIQUE,
    senha VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    cpf VARCHAR(11) UNIQUE,
    data_nascimento DATE,
    data_atualizacao DATE,
    tipo_usuario VARCHAR(50)
);