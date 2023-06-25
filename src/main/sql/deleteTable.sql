--apaga todos os dados existentes nas tabelas
BEGIN transaction;

TRUNCATE users CASCADE;
TRUNCATE boards CASCADE;
TRUNCATE tasklists CASCADE;
TRUNCATE cards CASCADE;
TRUNCATE userboards CASCADE;

COMMIT transaction;