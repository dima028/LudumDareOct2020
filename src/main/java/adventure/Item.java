package adventure;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Item{
    /* you will need to add some private member variables */

    /* required public methods */

    public String getName(JSONObject itemObject){
        String itemObjectName = (String) itemObject.get("name");
        return itemObjectName;
    }

    public String getLongDescription(JSONObject itemObject){
        String itemObjectDesc = (String) itemObject.get("desc");
        return itemObjectDesc;
    }

    public ArrayList getContainingRoom(JSONArray roomArray, long itemId){
        ArrayList containingRoom = new ArrayList<String>();

        for(Object currentRoom : roomArray) {
            JSONObject currentRoomJSON = (JSONObject) currentRoom;
            String currentRoomName = (String) currentRoomJSON.get("name");

            try {
                JSONArray currentRoomLoot = (JSONArray) currentRoomJSON.get("loot");
                for(Object lootingItem : currentRoomLoot) {
                    JSONObject lootingItemJSON = (JSONObject) lootingItem;
                    long lootingItemId = (long) lootingItemJSON.get("id");
                    if (lootingItemId == itemId){
                        containingRoom.add(currentRoomName);
                    }
                }
            } catch (Exception e) {
                // ignore, no loot in room
            }
        }
        return containingRoom;
    }
}