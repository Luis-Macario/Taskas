--apaga todos os dados existentes nas tabelas
BEGIN transaction;

DELETE FROM users;
DELETE FROM boards;
DELETE FROM tasklists;
DELETE FROM cards;
DELETE FROM userboards;

COMMIT transaction;