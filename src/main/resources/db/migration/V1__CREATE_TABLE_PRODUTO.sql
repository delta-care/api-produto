CREATE TABLE produto
(
    id                   INTEGER AUTO_INCREMENT,
    plano                VARCHAR(255) NOT NULL,
    subplano             VARCHAR(255) NOT NULL,
    valor                DECIMAL(15,2),
    data_inicio_vigencia DATE NOT NULL,
    data_fim_vigencia    DATE,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=396721;