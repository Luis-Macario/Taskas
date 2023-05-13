CREATE OR REPLACE FUNCTION create_board(
    IN userId INT, -- userID
    IN bName VARCHAR, -- board name
    IN bDescription VARCHAR -- board description
)
    returns int
    LANGUAGE 'plpgsql'
AS
$$
DECLARE
    newBoardId INT = -1;
BEGIN
    INSERT INTO boards (name, description) VALUES (bName, bDescription);
    SELECT currval(pg_get_serial_sequence('boards', 'id')) INTO newBoardId;

    if (newBoardId = -1) then
        raise exception 'INSERT INTO boards FAILLED';
    end If;

    INSERT INTO userboards(uid, bid) VALUES (userId, newBoardId);

    return newBoardId;
END;
$$;
