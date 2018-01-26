CREATE TABLE IF NOT EXISTS mesa(
  id_mesa bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  descricao VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS venda_mesa(
  id_venda_mesa bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  mesa_id_mesa bigint(20) NOT NULL,
  venda_id_venda bigint(20) NOT NULL,
  status INT NOT NULL,
  FOREIGN KEY (mesa_id_mesa) REFERENCES mesa(id_mesa),
  FOREIGN KEY (venda_id_venda) REFERENCES venda(id_venda)
) ENGINE=InnoDB DEFAULT CHARSET=utf8