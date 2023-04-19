CREATE OR REPLACE PROCEDURE delete_card(
    IN cid INT -- card identifier
)
    LANGUAGE 'plpgsql'
AS
$$
DECLARE
    boardId int = -1;
    listId int = -1;
    listIdx int = -1;
BEGIN
    SELECT bid, lid, indexlist INTO boardId, listId, listIdx FROM cards where id = cid;
    IF boardId = -1 then Raise EXCEPTION ' The board id must be higher than 1';
    END IF;
    IF listId = -1 then Raise EXCEPTION ' The List id must be higher than 1';
    END IF;
    IF listIdx = -1 then Raise EXCEPTION ' The List index must be higher than 1';
    END IF;

    DELETE FROM cards WHERE id = cid;
    UPDATE cards  as c SET indexlist = c.indexlist - 1  WHERE c.lid = listId and  c.bid = boardId and c.indexlist >= listIdx;

END;
$$;
