CREATE TABLE products (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description varchar(255) DEFAULT NULL,
  name varchar(255) NOT NULL,
  price_cost decimal(19,2) DEFAULT NULL,
  price_sale decimal(19,2) NOT NULL,
  id_category bigint(20) NOT NULL,
  FOREIGN KEY (id_category) REFERENCES categories(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8