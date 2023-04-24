CREATE OR REPLACE PROCEDURE move_card(
    IN cid INT, -- card identifier
    IN listId INT, -- the destination list identifier
    IN cix INT  -- he new index for the card in the destination list.
    )
    LANGUAGE 'plpgsql'
AS
$$
    DECLARE
        newBoardId INT = -1;
        oldIdx INT = -1;
        oldList INT = -1;
        oldBoard INT = -1;
BEGIN
   SELECT bid INTO newBoardId FROM tasklists where id = listId;
    IF newBoardId = -1 then Raise warning ' The board id must be higher than 0';
    END IF;
   SELECT indexlist, lid, bid INTO oldIdx, oldList, oldBoard FROM cards where id = cid;
   IF oldIdx = -1 then Raise warning ' The card indexList must be higher than 0';
   END IF;

   IF( oldList != listId) then
       -- Diferent lists:
        -- Updates the receiving list
       UPDATE cards  as c SET indexlist = c.indexlist + 1 WHERE c.lid = listId and  c.bid = newBoardId and c.indexlist >= cix;
        -- Updates the old list
       UPDATE cards  as c SET indexlist = c.indexlist - 1 WHERE c.lid = oldList and c.bid = oldBoard and oldIdx < c.indexlist;
   ELSE
       -- Same lists:
        if(oldIdx < cix) then
            UPDATE cards  as c SET indexlist = c.indexlist + 1 WHERE c.lid = listId and  c.bid = newBoardId and c.indexlist > cix;
        else
            UPDATE cards  as c SET indexlist = c.indexlist - 1  WHERE c.lid = listId and  c.bid = newBoardId and c.indexlist <= cix;
        end if;

   end if;



   UPDATE cards SET  indexList = cix, lid = listId , bid =  newBoardId WHERE id =  cid;
   END;
$$;
