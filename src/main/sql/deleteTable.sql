--apaga todos os dados existentes nas tabelas
BEGIN transaction;

TRUNCATE users;
TRUNCATE boards;
TRUNCATE tasklists;
TRUNCATE cards;
TRUNCATE userboards;

COMMIT transaction;