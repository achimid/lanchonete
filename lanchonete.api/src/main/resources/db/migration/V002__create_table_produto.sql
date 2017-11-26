CREATE TABLE IF NOT EXISTS produto (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome varchar(255) NOT NULL,
  descricao varchar(255) DEFAULT NULL,
  valor_venda decimal(19,2) NOT NULL,
  valor_custo decimal(19,2) DEFAULT NULL,
  categoria_id_categoria bigint(20) NOT NULL,
  FOREIGN KEY (categoria_id_categoria) REFERENCES categoria(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8