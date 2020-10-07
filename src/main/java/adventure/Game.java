package adventure;

// scanner import
import java.util.Scanner;

// JSON imports
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

// IO imports
import java.io.FileReader;
import java.io.Reader;

// misc util imports
import java.util.Stack;
import java.util.ArrayList;


public class Game{

    /* this is the class that runs the game.
    You may need some member variables */
    private static long currentRoomId;
    private static String currentRoomDescription;

    public static void main(String args[]){

        /* You will need to instantiate an object of type
        game as we're going to avoid using static methods
        for this assignment */

        Game game = new Game();
        // 1. Print a welcome message to the user
        System.out.println("Welcome to the GAME!");

        // 2. Ask the user if they want to load a json file.
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Would you like to load a json file? (Y/N)");
        String loadJSON = scanner.nextLine();

        /* 3. Parse the file the user specified to create the
        adventure, or load your default adventure*/ 
        String filename = "";
        if (loadJSON.equals("Y")||loadJSON.equals("y")){
            System.out.println("Please provide the path to the json file:");
            filename = scanner.nextLine();
        } else if (loadJSON.equals("N")||loadJSON.equals("n")){
            filename = "default_adventure.json";
        } else {
            System.out.println("Invalid input.");
            return;
        }

        // Instantiate JSON adventure object
        JSONObject adventure_json = (JSONObject) loadAdventureJson(filename);


        // getting first layer of JSON: "adventure"
        JSONObject adventureObject = (JSONObject) adventure_json.get("adventure");

        // getting next layer of JSON: "room"        
        JSONArray roomArray = (JSONArray) adventureObject.get("room");

        // 4. Print the beginning of the adventure
        // iterating through rooms until it finds the starting room
        for(Object currentRoom : roomArray) {
            JSONObject currentRoomJSON = (JSONObject) currentRoom;
            String currentRoomStart = (String) currentRoomJSON.get("start");
            if (currentRoomStart.equals("true")){
                currentRoomDescription = (String) currentRoomJSON.get("short_description");
                currentRoomId = (long) currentRoomJSON.get("id");
                System.out.println(currentRoomDescription);
                break;
            }
        }

        // 5. Begin game loop here
        Boolean userConsent = true;
        while (userConsent == true) {

            // 6. Get the user input. You'll need a Scanner
            String userCommand = scanner.nextLine();
            String[] userCommandSplit = userCommand.split(" ");
            String userCommandAction = userCommandSplit[0];
            String userCommandItemOrDirection = null;
            try {
                userCommandItemOrDirection = userCommandSplit[1];
            } catch (Exception e) {
                // ignore error, user didn't specify an item/dxn
            }

            // if user uses "go" command
            if (userCommandAction.equals("go")) {

                // assume room doesn't exist unless proven otherwise
                boolean invalidRoom = true;

                // navigate to current room
                for(Object currentRoomInLoop : roomArray) {
                    JSONObject currentRoomInLoopJSON = (JSONObject) currentRoomInLoop;
                    long currentRoomInLoopId = (long) currentRoomInLoopJSON.get("id");
                    if (currentRoomId == currentRoomInLoopId) {
                        currentRoomDescription = (String) currentRoomInLoopJSON.get("short_description");
                        JSONArray currentRoomEntrances = (JSONArray) currentRoomInLoopJSON.get("entrance");
                        
                        // loop through all entrances in current room
                        for(Object currentEntrance : currentRoomEntrances) {
                            JSONObject currentEntranceJSON = (JSONObject) currentEntrance;
                            String entranceDir = (String) currentEntranceJSON.get("dir");

                            // if entrance option exists, go to new room
                            if (userCommandItemOrDirection.equals(entranceDir)){
                                currentRoomId = (long) currentEntranceJSON.get("id");
                                invalidRoom = false;

                                // navigating to the next room and printing description
                                for(Object nextRoomInLoop : roomArray) {
                                    JSONObject nextRoomInLoopJSON = (JSONObject) nextRoomInLoop;
                                    long nextRoomInLoopId = (long) nextRoomInLoopJSON.get("id");
                                    if (currentRoomId == nextRoomInLoopId) {
                                        String nextRoomName = (String) nextRoomInLoopJSON.get("name");
                                        System.out.println(nextRoomName);
                                        JSONArray nextRoomLoot = (JSONArray) nextRoomInLoopJSON.get("loot");
                                        try {
                                            JSONArray itemArray = (JSONArray) adventureObject.get("item");
                                            
                                            Stack itemsStack = new Stack();

                                            for (Object itemObject : itemArray) {
                                                JSONObject itemObjectJSON = (JSONObject) itemObject;
                                                long itemObjectId = (long) itemObjectJSON.get("id");

                                                for (Object lootItem : nextRoomLoot) {
                                                    JSONObject lootItemJSON = (JSONObject) lootItem;
                                                    long lootItemId = (long) lootItemJSON.get("id");

                                                    if (lootItemId == itemObjectId) {
                                                        String itemObjectName = (String) itemObjectJSON.get("name");
                                                        itemsStack.add(itemObjectName);
                                                    }
                                                }
                                            }
                                        System.out.println("Items in room: " + itemsStack.toString());
                                        } catch (Exception e) {
                                            //do nothing, no items exist in room
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (invalidRoom == true) {
                    System.out.println("Invalid direction.");
                }

            } else if (userCommandAction.equals("look")) {
                if (userCommandItemOrDirection != null){
                    // looking at an item
                    JSONArray itemArray = (JSONArray) adventureObject.get("item");
                                            
                    for (Object itemObject : itemArray) {
                        JSONObject itemObjectJSON = (JSONObject) itemObject;
                        String itemObjectName = (String) itemObjectJSON.get("name");

                        if (userCommandItemOrDirection.equals(itemObjectName)) {
                            String itemObjectDesc = (String) itemObjectJSON.get("desc");
                            System.out.println(itemObjectDesc);                        }
                    }  
                
                } else {
                    for(Object currentRoomInLoop : roomArray) {
                        JSONObject currentRoomInLoopJSON = (JSONObject) currentRoomInLoop;
                        long currentRoomInLoopId = (long) currentRoomInLoopJSON.get("id");
                        if (currentRoomId == currentRoomInLoopId) {
                            currentRoomDescription = (String) currentRoomInLoopJSON.get("long_description");
                            System.out.println(currentRoomDescription);
                        }
                    }
                }
                
            } else if (userCommandAction.equals("use")) {
                // use item and print some action statement corresponding to the item
                if (userCommandItemOrDirection != null){
                    // looking at an item
                    JSONArray itemArray = (JSONArray) adventureObject.get("item");
                                            
                    for (Object itemObject : itemArray) {
                        JSONObject itemObjectJSON = (JSONObject) itemObject;
                        String itemObjectName = (String) itemObjectJSON.get("name");

                        if (userCommandItemOrDirection.equals(itemObjectName)) {
                            String itemObjectAction = (String) itemObjectJSON.get("action");
                            System.out.println(itemObjectAction);                        }
                    }  
                
                } else {
                    System.out.println("Use what?");
                }
                
            } else {
                System.out.println("Invalid command.");
            }  
        }
    }

    public static JSONObject loadAdventureJson(String filename){

        // Initialize JSONObject to hold contents of file
        JSONObject adventure_json;

        // Initialize JSON Parser
        JSONParser parser = new JSONParser();

        // Initialize file reader with provided file, check if file exists
        try (Reader reader = new FileReader(filename)) {
            adventure_json = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            System.out.println("File not found.");
            adventure_json = null;
        }
        return adventure_json;
    }


    // public Adventure generateAdventure(JSONObject obj) {
        // Adventure newAdventure = new Adventure();
        // ArrayList allRooms = newAdventure.listAllRooms(roomArray);
        
        // JSONArray itemArraylol = (JSONArray) adventureObject.get("item");
        // ArrayList allItems = newAdventure.listAllItems(itemArraylol);

        // String currentDescription = newAdventure.getCurrentRoomDescription(roomArray, 105);

    // }

}