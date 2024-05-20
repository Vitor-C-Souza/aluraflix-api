CREATE TABLE usuario_tb(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    usuario varchar(200) NOT NULL UNIQUE,
    senha varchar(200) NOT NULL,
    PRIMARY KEY (id)
);