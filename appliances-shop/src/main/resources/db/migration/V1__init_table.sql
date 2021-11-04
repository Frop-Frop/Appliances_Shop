DROP TABLE IF EXISTS deferreds;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS address;
CREATE TABLE category(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  super_category_id BIGINT NULL REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE customer(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  address_id BIGINT,
  cart_id BIGINT,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL
);

CREATE TABLE cart(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  customer_id BIGINT REFERENCES customer(id) ON DELETE CASCADE
);
CREATE TABLE address(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  country VARCHAR(50) NOT NULL,
  region VARCHAR(50) NOT NULL,
  city VARCHAR(50) NOT NULL,
  street VARCHAR(50) NOT NULL,
  house_number VARCHAR(50) NOT NULL,
  customer_id BIGINT  references customer(id) ON DELETE CASCADE
);

CREATE TABLE product(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  category_id BIGINT REFERENCES category(id),
  description VARCHAR(255),
  brand VARCHAR(50),
  price BIGINT NOT NULL
);
CREATE TABLE item(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  quantity BIGINT NOT NULL,
  product_id BIGINT REFERENCES product(id) ON DELETE CASCADE,
  cart_id BIGINT REFERENCES cart(id) ON DELETE CASCADE,
  customer_id BIGINT REFERENCES customer(id)  ON DELETE CASCADE
);
CREATE TABLE deferreds(
  customer_id BIGINT REFERENCES customer(id) ON DELETE CASCADE,
  item_id BIGINT REFERENCES item(id) ON DELETE CASCADE
)