
DROP DATABASE IF EXISTS banco_pizzaria_delivery;
CREATE DATABASE banco_pizzaria_delivery;
USE banco_pizzaria_delivery;

CREATE TABLE cliente (
    cpf       CHAR(11)     PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    telefone  VARCHAR(11)  NOT NULL,
    endereco  VARCHAR(150) NOT NULL
);

CREATE TABLE entregador (
    cpf         CHAR(11)     PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL,
    telefone    VARCHAR(11)  NOT NULL,
    placa_moto  CHAR(7)      NOT NULL UNIQUE,
    disponivel  BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE pizza (
    codigo   INT          PRIMARY KEY,
    sabor    VARCHAR(50)  NOT NULL,
    tamanho  VARCHAR(10)  NOT NULL,
    preco    DECIMAL(6,2) NOT NULL
);

CREATE TABLE pedido (
    numero          INT AUTO_INCREMENT PRIMARY KEY,
    data_hora       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cpf_cliente     CHAR(11)     NOT NULL,
    codigo_pizza    INT          NOT NULL,
    quantidade      INT          NOT NULL,
    tipo            ENUM('DELIVERY', 'RETIRADA') NOT NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'RECEBIDO',
    cpf_entregador  CHAR(11)     NULL,          -- só preenchido no delivery
    taxa_entrega    DECIMAL(5,2) NULL,          -- só preenchido no delivery
    observacao      VARCHAR(100) NULL,          -- só preenchido na retirada
    FOREIGN KEY (cpf_cliente)    REFERENCES cliente(cpf),
    FOREIGN KEY (codigo_pizza)   REFERENCES pizza(codigo),
    FOREIGN KEY (cpf_entregador) REFERENCES entregador(cpf)
);

INSERT INTO pizza VALUES
    (1, 'Calabresa Acebolada',   'Grande', 49.90),
    (2, 'Frango com Catupiry',   'Grande', 54.90),
    (3, 'Quatro Queijos',        'Grande', 57.90),
    (4, 'Portuguesa',            'Media',  42.90),
    (5, 'Marguerita',            'Media',  39.90),
    (6, 'Chocolate com Morango', 'Broto',  32.90);

INSERT INTO cliente VALUES
    ('11122233344', 'Ana Beatriz Lima',  '87991234567', 'Rua Dantas Barreto, 120, Centro'),
    ('55566677788', 'Carlos Eduardo Sá', '87998765432', 'Av. Santo Antônio, 45, Heliópolis');

INSERT INTO entregador (cpf, nome, telefone, placa_moto) VALUES
    ('99988877766', 'Ricardo Motoboy', '87996541234', 'PEX4A21'),
    ('44433322211', 'Joana Entregas',  '87995553322', 'KGT7B08');

INSERT INTO pedido (cpf_cliente, codigo_pizza, quantidade, tipo, status, cpf_entregador, taxa_entrega) VALUES
    ('11122233344', 1, 2, 'DELIVERY', 'SAIU PARA ENTREGA', '99988877766', 7.00);

INSERT INTO pedido (cpf_cliente, codigo_pizza, quantidade, tipo, observacao) VALUES
    ('55566677788', 5, 1, 'RETIRADA', 'sem cebola');


SELECT p.numero,
       c.nome AS cliente,
       pz.sabor,
       p.quantidade,
       p.tipo,
       p.status,
       e.nome AS entregador,
       CASE
           WHEN p.tipo = 'DELIVERY' THEN pz.preco * p.quantidade + p.taxa_entrega
           ELSE pz.preco * p.quantidade * 0.90
       END AS valor_final
FROM pedido p
JOIN cliente c     ON c.cpf = p.cpf_cliente
JOIN pizza pz      ON pz.codigo = p.codigo_pizza
LEFT JOIN entregador e ON e.cpf = p.cpf_entregador;
