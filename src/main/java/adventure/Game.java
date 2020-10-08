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
        System.out.println("Welcome to the GAME!");
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        String filename = "5x5_dungeon.json";
        

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
            String currentRoomStart = (String) currentRoomJSON.get("player_start");
            if (currentRoomStart.equals("true")){
                currentRoomId = (long) currentRoomJSON.get("id");
                break;
            }
        }

        // 5. Begin game loop here
        Boolean userConsent = true;
        while (userConsent == true) {

            // 6. Get the user input. You'll need a Scanner
            String userCommand = scanner.nextLine();            

            // assume room doesn't exist unless proven otherwise
            boolean invalidRoom = true;

            // navigate to current room
            for(Object currentRoomInLoop : roomArray) {
                JSONObject currentRoomInLoopJSON = (JSONObject) currentRoomInLoop;
                long currentRoomInLoopId = (long) currentRoomInLoopJSON.get("id");
                if (currentRoomId == currentRoomInLoopId) {
                    JSONArray currentRoomEntrances = (JSONArray) currentRoomInLoopJSON.get("entrance");
                    
                    // loop through all entrances in current room
                    for(Object currentEntrance : currentRoomEntrances) {
                        JSONObject currentEntranceJSON = (JSONObject) currentEntrance;
                        String entranceDir = (String) currentEntranceJSON.get("dir");

                        // if entrance option exists, go to new room
                        if (userCommand.equals(entranceDir)){
                            currentRoomId = (long) currentEntranceJSON.get("id");
                            invalidRoom = false;
                        }
                    }
                }
            }
            if (invalidRoom == true) {
                System.out.println("Invalid direction.");
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

}