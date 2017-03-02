DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS currencies CASCADE;
DROP TABLE IF EXISTS fx_rates CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS operations CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TYPE IF EXISTS ROLE CASCADE;


CREATE TABLE users
(
  id                SERIAL PRIMARY KEY,
  name              VARCHAR(255),
  email             VARCHAR(255),
  password          VARCHAR(255),
  basic_currency_id INT NOT NULL,
  registered        TIMESTAMP DEFAULT now(),
  confirmed         BOOLEAN,
  enabled           BOOLEAN
);

CREATE TABLE currencies
(
  id      INT        NOT NULL PRIMARY KEY,
  char_id VARCHAR(3) NOT NULL
);

CREATE TABLE fx_rates
(
  base_currency_id     INT       NOT NULL,
  variable_currency_id INT       NOT NULL,
  rate                 DECIMAL   NOT NULL,
  date                 TIMESTAMP NOT NULL,
  PRIMARY KEY (base_currency_id, variable_currency_id, date)
);

CREATE TABLE accounts (
  id          BIGSERIAL PRIMARY KEY,
  user_id     BIGINT         NOT NULL,
  name        VARCHAR(255)   NOT NULL,
  currency_id INT            NOT NULL,
  amount      DECIMAL(10, 2) NOT NULL,
  type        VARCHAR(255)   NOT NULL
);

CREATE TABLE categories (
  id        BIGSERIAL PRIMARY KEY,
  parent_id BIGINT,
  level     INT,
  name      VARCHAR(255) NOT NULL,
  user_id   BIGINT,
  type      VARCHAR(45)  NOT NULL
);

CREATE TABLE operations (
  id                   BIGSERIAL PRIMARY KEY,
  correspondent_id     BIGINT,
  category_id          BIGINT         NOT NULL,
  acc_id               BIGINT DEFAULT NULL,
  date                 TIMESTAMP      NOT NULL,
  amount               DECIMAL(10, 2) NOT NULL,
  amount_after         DECIMAL(10, 2) NOT NULL,
  comment              VARCHAR(255),
  base_to_this_fx_rate DECIMAL
);

CREATE TYPE ROLE AS ENUM ('ROLE_USER', 'ROLE_ADMIN');
CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role    ROLE
);