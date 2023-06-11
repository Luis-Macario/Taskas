--BEGIN transaction;

INSERT INTO users(name, email, token, password)
VALUES ('Francisco Medeiros', 'a46631@alunos.isel.pt', '160ee838-150b-4ca1-a2ff-2e964383c315',
        'C04825961B2415A75D4DE08598E8BBF4D1ECDCAE1A44D58E7CE03111BDA25A3A');
INSERT INTO users(name, email, token, password)
VALUES ('Ricardo Pinto', 'a47673@alunos.isel.pt', '12971dc2-6816-4851-b110-e19065747785',
        '6559D8CAEFE3D38D0AD455B8A072BB5A11DA31AC19DA7AFFAD563FC4D0AFF0EF');
INSERT INTO users(name, email, token, password)
VALUES ('Luis Macario', 'a47671@alunos.isel.pt', '658baaa9-4035-415e-9674-6957704600ba',
        '132513E5601D28F9DBDEBD2590514E171FEFEC9A6BE60417D79B8D626077C3FB');

INSERT INTO boards(name, description)
VALUES ('Start backend work 123', 'Backend work for the Compose Desktop project');
INSERT INTO boards(name, description)
VALUES ('Start model work 12345', 'Create the package model for the Compose Desktop project');

INSERT INTO tasklists(bid, name)
VALUES (1, 'To Do');
INSERT INTO tasklists(bid, name)
VALUES (1, 'Doing');
INSERT INTO tasklists(bid, name)
VALUES (1, 'Done');
INSERT INTO tasklists(bid, name)
VALUES (2, 'To Do');
INSERT INTO tasklists(bid, name)
VALUES (2, 'Doing');
INSERT INTO tasklists(bid, name)
VALUES (2, 'Done');

INSERT INTO cards(bid, lid, indexList, name, description, initdate)
VALUES (1, 1, 1, 'Creating X1', 'Creating X using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, indexlist, name, description, initdate)
VALUES (1, 1, 2, 'Creating Z2', 'Creating Z using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, indexList, name, description, initdate)
VALUES (1, 1, 3, 'Creating X3', 'Creating X using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, indexList, name, description, initdate)
VALUES (1, 2, 1, 'Creating Z4', 'Creating Z using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, indexList, name, description, initdate)
VALUES (1, 2, 2, 'Creating X1', 'Creating X using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, indexlist, name, description, initdate)
VALUES (1, 2, 3, 'Creating Z2', 'Creating Z using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, indexList, name, description, initdate)
VALUES (2, 1, 1, 'Creating X3', 'Creating X using Y', Date('2023-03-27'));
INSERT INTO cards(bid, lid, indexList, name, description, initdate)
VALUES (2, 1, 2, 'Creating Z4', 'Creating Z using Y', Date('2023-03-27'));

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