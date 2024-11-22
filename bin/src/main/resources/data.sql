INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (17, 'TO', 'TOCANTINS', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (16, 'PI', 'PIAUÍ', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (14, 'RR', 'RORAIMA', 2);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (13, 'MT', 'MATO GROSSO', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (12, 'RN', 'RIO GRANDE DO NORTE', 2);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (10, 'UU', 'FJHSADF', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (9, 'TY', '123849762134', 2);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (8, 'MA', 'MARANHÃO', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (7, 'CE', 'CEARÁ', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (6, 'IN', 'INEXISTENTE', 2);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (5, 'RS', 'RIO GRANDE DO SUL', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (4, 'MG', 'MINAS GERAIS', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (3, 'SP', 'SÃO PAULO', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (2, 'RJ', 'RIO DE JANEIRO', 1);
INSERT INTO tb_uf (codigoUF, sigla, nome, status) VALUES (1, 'ES', 'ESPÍRITO SANTO', 1);

ALTER SEQUENCE SEQ_CODIGOUF RESTART WITH 18;


INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (11, 5, 'FORMIGA', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (10, 4, 'PATOS DE MINAS', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (9, 4, 'UBERLÂNDIA', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (8, 4, 'UBERABA', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (6, 3, 'CONTAGEM', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (5, 3, 'BELO HORIZONTE', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (4, 2, 'PARATI', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (3, 2, 'ANGRA DOS REIS', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (7, 1, 'VITÓRIA', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (2, 1, 'VILA VELHA', 1);
INSERT INTO tb_municipio (codigo_municipio, codigoUF, nome, status) VALUES (1, 1, 'GUARAPARI', 1);

ALTER SEQUENCE SEQ_CODIGOMUNICIPIO RESTART WITH 12;

