CREATE OR REPLACE PROCEDURE delete_card(
    IN cid INT -- card identifier
)
    LANGUAGE 'plpgsql'
AS
$$
DECLARE
    boardId int = -1;
    listId  int = -1;
    listIdx int = -1;
BEGIN
    SELECT bid, lid, indexlist INTO boardId, listId, listIdx FROM cards where id = cid;
    IF boardId = -1 or listId = -1 or listIdx = -1 then
        Raise EXCEPTION ' Card with that id [%] not found', cid;
    end if;

    DELETE FROM cards WHERE id = cid;
    UPDATE cards as c
    SET indexlist = c.indexlist - 1
    WHERE c.lid = listId and c.bid = boardId and c.indexlist >= listIdx;

END;
$$;
