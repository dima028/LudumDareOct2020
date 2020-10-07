package adventure;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Adventure{

    /* ======== Required public methods ========== */
        /* note,  you don't have to USE all of these
        methods but you do have to provide them.
        We will be using them to test your code */

    public ArrayList<Room> listAllRooms(JSONArray roomArray){
        ArrayList allRooms = new ArrayList<String>();
        for(Object currentRoom : roomArray) {
            JSONObject currentRoomJSON = (JSONObject) currentRoom;
            String currentRoomName = (String) currentRoomJSON.get("name");
            allRooms.add(currentRoomName);
        }
        return allRooms;
    }

    public ArrayList<Item> listAllItems(JSONArray itemArray){
        ArrayList allItems = new ArrayList<String>();
        for(Object currentItem : itemArray) {
            JSONObject currentItemJSON = (JSONObject) currentItem;
            String currentItemName = (String) currentItemJSON.get("name");
            allItems.add(currentItemName);
        }
        return allItems;
    }

    public String getCurrentRoomDescription(JSONArray roomArray, long roomId){
        String roomDescription = "";
        for(Object currentRoom : roomArray) {
            JSONObject currentRoomJSON = (JSONObject) currentRoom;
            long currentRoomId = (long) currentRoomJSON.get("id");
            if (roomId == currentRoomId) {
                roomDescription = (String) currentRoomJSON.get("short_description");
            }
        }
        return roomDescription;
    }

}