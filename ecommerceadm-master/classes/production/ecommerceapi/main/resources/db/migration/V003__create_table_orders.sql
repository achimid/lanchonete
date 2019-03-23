CREATE TABLE orders (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  date_order TIMESTAMP DEFAULT now(),
  final_price decimal(19,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8