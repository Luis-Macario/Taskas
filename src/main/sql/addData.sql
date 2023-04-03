--BEGIN transaction;

INSERT INTO users(name, email, token)
VALUES ('Francisco Medeiros', 'a46631@alunos.isel.pt', '160ee838-150b-4ca1-a2ff-2e964383c315');
INSERT INTO users(name, email, token)
VALUES ('Ricardo Pinto', 'a47673@alunos.isel.pt', '12971dc2-6816-4851-b110-e19065747785');
INSERT INTO users(name, email, token)
VALUES ('Luis Macario', 'a47671@alunos.isel.pt', '658baaa9-4035-415e-9674-6957704600ba');

INSERT INTO boards(name, description)
VALUES ('Start backend work 123', 'Backend work for the Compose Desktop project');
INSERT INTO boards(name, description)
VALUES ('Start model work 12345', 'Create the package model for the Compose Desktop project');

INSERT INTO tasklists(bid,name)
VALUES (1,'To Do');
INSERT INTO tasklists(bid,name)
VALUES (2,'Doing');
INSERT INTO tasklists(bid,name)
VALUES (1,'Done');
INSERT INTO tasklists(bid,name)
VALUES (2,'To Do');
INSERT INTO tasklists(bid,name)
VALUES (1,'Doing');
INSERT INTO tasklists(bid,name)
VALUES (2,'Done');

INSERT INTO cards(bid, lid, name, description, initdate)
VALUES (1, 1, 'Creating X', 'Creating X using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, name, description, initdate)
VALUES (1, 2, 'Creating Z', 'Creating Z using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, name, description, initdate)
VALUES (2, 1, 'Creating X', 'Creating X using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, name, description, initdate)
VALUES (2, 2, 'Creating Z', 'Creating Z using Y', Date('2023-03-27'));

INSERT INTO userboards
VALUES (1, 1);
INSERT INTO userboards
VALUES (1, 2);
INSERT INTO userboards
VALUES (2, 1);
INSERT INTO userboards
VALUES (2, 2);
INSERT INTO userboards
VALUES (3, 1);
INSERT INTO userboards
VALUES (3, 2);
--COMMIT TRANSACTION;