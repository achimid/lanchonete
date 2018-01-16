CREATE TABLE IF NOT EXISTS categoria(
  id_categoria bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome varchar(255) NOT NULL,
  descricao varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8