DELETE FROM operations;
DELETE FROM categories;
DELETE FROM accounts;
DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM currencies;
DELETE FROM fx_rates;

INSERT INTO users (name, email, password, basic_currency_id, confirmed, enabled)
VALUES
  ('samchuk', 'codealist@gmail.com', '$2a$10$ob4tCIY9IJoYKRVpkeOTbuu/jIfYNh3QAySHY2LWi3Cu5h5vWIJpu', 980, '1', '1');
INSERT INTO users (name, email, password, basic_currency_id, confirmed, enabled)
VALUES
  ('kalashnyk', 'kalashnyck@gmail.com', '$2a$10$FOjfYI6Yt/52oUpbkLCrvus41dC57bBEI56CF6a/ZS3sxLnZQytrW', 980, '1', '1');

INSERT INTO user_roles (user_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO user_roles (user_id, role) VALUES (2, 'ROLE_USER');
INSERT INTO user_roles (user_id, role) VALUES (2, 'ROLE_ADMIN');

INSERT INTO currencies (id, char_id) VALUES (980, 'UAH');
INSERT INTO currencies (id, char_id) VALUES (840, 'USD');
INSERT INTO currencies (id, char_id) VALUES (978, 'EUR');

INSERT INTO categories (name, type) VALUES ('IN_TRANSFER', 'IN_TRANSFER');
INSERT INTO categories (name, type) VALUES ('OUT_TRANSFER', 'OUT_TRANSFER');
INSERT INTO categories (name, type) VALUES ('Изменение остатка', 'SERVICE');

INSERT INTO fx_rates (base_currency_id, variable_currency_id, rate, date) VALUES (980, 840, 27.079535, '2017-02-01');
INSERT INTO fx_rates (base_currency_id, variable_currency_id, rate, date) VALUES (980, 978, 29.12404, '2017-02-01');
