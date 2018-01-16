CREATE TABLE venda_pagamento(
  id_venda_pagamento bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  venda_id_venda bigint(20) NOT NULL,
  forma_pagamento_id_forma_pagamento bigint(20) NOT NULL,
  valor decimal(19,2) DEFAULT NULL,
  FOREIGN KEY (venda_id_venda) REFERENCES venda(id_venda),
  FOREIGN KEY (forma_pagamento_id_forma_pagamento) REFERENCES forma_pagamento(id_forma_pagamento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8