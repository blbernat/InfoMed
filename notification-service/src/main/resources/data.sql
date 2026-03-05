-- inserir registros

insert IGNORE into TIPO_USUARIO (id, tipo_usuario)
values (1, 'PACIENTE'), (2, 'MEDICO'), (3, 'ENFERMEIRO');


insert IGNORE into USUARIO (nome, login, senha, email, cpf, data_nascimento, data_criacao, data_atualizacao, tipo_usuario_id)
values ('Maria da Silva', 'maria', 'password', 'maria@fiap.com', '11122233344','2000-10-15', '2026-01-01', '2026-02-02', 'PACIENTE');

insert IGNORE into USUARIO (nome, login, senha, email, cpf, data_nascimento, data_criacao, data_atualizacao, tipo_usuario_id)
values ('Ana Carolina', 'ana', 'password2', 'ana@fiap.com', '12345678900','1965-06-22', '2026-01-01', '2026-02-02', 'MEDICO');

insert IGNORE into USUARIO (nome, login, senha, email, cpf, data_nascimento, data_criacao, data_atualizacao, tipo_usuario_id)
values ('Joao Sousa', 'joao', 'password3', 'joao@fiap.com', '99988877766','1970-12-07', '2026-01-01', '2026-02-02', 'ENFERMEIRO');
