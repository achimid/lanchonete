CREATE TABLE IF NOT EXISTS forma_pagamento(
  id_forma_pagamento bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome varchar(255) NOT NULL,
  ativo boolean DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8