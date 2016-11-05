DELETE FROM operations;
DELETE FROM categories;
DELETE FROM accounts;
DELETE FROM users;

INSERT INTO `budget`.`users` (`name`, `email`, `password`, `confirmed`) VALUES ('samchuk', 'codealist@gmail.com', 'qwerty+1', '1');
INSERT INTO `budget`.`users` (`name`, `email`, `password`, `confirmed`) VALUES ('kalashnyk', 'kalashnyck@gmail.com', 'qwerty+1', '1');

INSERT INTO `budget`.`user_roles` (`user_id`, `role`) VALUES ('1', 'ROLE_USER');
INSERT INTO `budget`.`user_roles` (`user_id`, `role`) VALUES ('2', 'ROLE_USER');
INSERT INTO `budget`.`user_roles` (`user_id`, `role`) VALUES ('2', 'ROLE_ADMIN');

INSERT INTO `budget`.`categories` (`name`, `user_id`, `type`) VALUES ('Food', '1', 'EXPENSE');
INSERT INTO `budget`.`categories` (`name`, `user_id`, `type`) VALUES ('Sallary', '1', 'INCOME');
INSERT INTO `budget`.`categories` (`name`, `user_id`, `type`) VALUES ('Transport', '2', 'EXPENSE');
INSERT INTO `budget`.`categories` (`name`, `user_id`, `type`) VALUES ('Sallary', '2', 'INCOME');

INSERT INTO `budget`.`accounts` (`user_id`, `name`, `currency`, amount, `type`) VALUES ('1', 'Кошелек', 'UAH', '0', 'CASH');
INSERT INTO `budget`.`accounts` (`user_id`, `name`, `currency`, amount, `type`) VALUES ('1', 'Зарплатная карта', 'UAH', '0', 'DEBIT_CARD');
INSERT INTO `budget`.`accounts` (`user_id`, `name`, `currency`, amount, `type`) VALUES ('2', 'Кошелек', 'UAH', '0', 'CASH');
INSERT INTO `budget`.`accounts` (`user_id`, `name`, `currency`, amount, `type`) VALUES ('2', 'Приват карта', 'UAH', '0', 'DEBIT_CARD');
