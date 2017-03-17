DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS fx_rates CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS operations CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TYPE IF EXISTS ROLE CASCADE;


CREATE TYPE ROLE AS ENUM ('ROLE_USER', 'ROLE_ADMIN');

CREATE TABLE accounts
(
  id BIGSERIAL PRIMARY KEY NOT NULL,
  user_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  amount NUMERIC(10,2) NOT NULL,
  type VARCHAR(255) NOT NULL,
  currency_code VARCHAR(3)
);
CREATE TABLE categories
(
  id BIGSERIAL PRIMARY KEY NOT NULL,
  parent_id BIGINT,
  level INTEGER,
  name VARCHAR(255) NOT NULL,
  user_id BIGINT,
  type VARCHAR(45) NOT NULL
);
CREATE TABLE fx_rates
(
  base_currency_code VARCHAR(3),
  variable_currency_code VARCHAR(3),
  rate NUMERIC,
  date TIMESTAMP
);
CREATE TABLE operations
(
  id BIGSERIAL PRIMARY KEY NOT NULL,
  correspondent_id BIGINT,
  category_id BIGINT NOT NULL,
  acc_id BIGINT,
  date TIMESTAMP NOT NULL,
  amount NUMERIC(10,2) NOT NULL,
  amount_after NUMERIC(10,2) NOT NULL,
  comment VARCHAR(255),
  amount_in_base_currency NUMERIC(10,2)
);
CREATE TABLE user_roles
(
  user_id BIGINT NOT NULL,
  role ROLE
);
CREATE TABLE users
(
  id BIGSERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  registered TIMESTAMP DEFAULT now(),
  confirmed BOOLEAN,
  enabled BOOLEAN,
  basic_currency_code VARCHAR(3)
);