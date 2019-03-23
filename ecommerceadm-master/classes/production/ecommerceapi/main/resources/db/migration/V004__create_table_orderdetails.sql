CREATE TABLE orderdetails (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  id_order bigint(20) NOT NULL,
  id_product bigint(20) NOT NULL,
  price decimal(19,2) DEFAULT NULL,
  quantity decimal(19,2) DEFAULT NULL,
  FOREIGN KEY (id_order) REFERENCES orders(id),
  FOREIGN KEY (id_product) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8