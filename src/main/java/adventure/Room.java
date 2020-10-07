package adventure;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Room{
    /* required public methods */

    public ArrayList<Item> listItems(JSONArray roomArray){
        ArrayList itemsInRoom = new ArrayList<String>();
        //lists all the items in the room
        for(Object currentRoom : roomArray) {
            JSONObject currentRoomJSON = (JSONObject) currentRoom;
            JSONArray currentRoomLoot = (JSONArray) currentRoomJSON.get("loot");
            for(Object lootingItem : currentRoomLoot) {
                    JSONObject lootingItemJSON = (JSONObject) lootingItem;
                    long lootingItemName = (long) lootingItemJSON.get("name");
                    itemsInRoom.add(lootingItemName);
                }
        }
        return itemsInRoom;
    }

    public String getName(JSONObject roomObject){
        String roomObjectName = (String) roomObject.get("name");
        return roomObjectName;
    }

    public String getLongDescription(JSONObject roomObject){
        String roomObjectDesc = (String) roomObject.get("long_description");
        return roomObjectDesc;
    }

    public String getConnectedRoom(String direction, JSONObject roomObject) {
        String connectedRoomId = "";
        ArrayList entrances = (ArrayList) roomObject.get("entrance");
        for(Object entrance : entrances) {
            JSONObject entranceJSON = (JSONObject) entrance;
            String entranceDirection = (String) entranceJSON.get("dir");
            if (entranceDirection == direction){
            connectedRoomId = (String) entranceJSON.get("id");
            }

        }
        return connectedRoomId;
    }
}