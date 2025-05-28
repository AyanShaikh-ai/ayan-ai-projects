import java.util.HashMap; 
import java.util.Random; 
import java.util.Collection;

/**
 * The Square class represents the game map, which is structured as a grid of rooms. It manages the layout and 
 * the interconnections of rooms within this grid, facilitating room navigation and item placement throughout the game.
 * This class is pivotal in setting up the game environment, initializing rooms with exits in valid directions,
 * and randomly distributing items like keys and screwdrivers to enhance gameplay complexity and interaction.
 *
 * Each room in the Square can be accessed via unique identifiers, and the class provides methods to retrieve rooms,
 * validate positions, and handle item randomization. This setup supports dynamic game interactions and exploration,
 * underpinning the navigational logic and strategic elements of the game.
 *
 * The Square class is essential for constructing the game world and enabling coherent spatial navigation for the player,
 * serving as the foundation for the game's geographical setup and thematic immersion.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
 public class Square 
{   private HashMap<String, Room> theSquare; 
    private int WIDTH = 5; 
    private int HEIGHT = 5; 
    private Random random; 

    public Square() 
    { 
        theSquare = new HashMap<>(); 
        random = new Random(); 
        String roomId; 
        Room room; 
         
        for (int x=1; x<=WIDTH; x++) { 
             
            for (int y=1; y<=HEIGHT; y++) { 
                roomId = x + "" + y;         
                 
                if (!theSquare.containsKey(roomId)) 
                    theSquare.put(roomId, new Room(roomId)); 
                 
                room = theSquare.get(roomId);     
                addExit(room, "east", x+1, y); 
                addExit(room, "west", x-1, y); 
                addExit(room, "north", x, y+1); 
                addExit(room, "south", x, y-1);                 
            } 
        } 
         
        // Add keys and screwdrivers randomly 
        addRandomItems("key", 1 + random.nextInt(4));  // Randomly add 1 to 4 keys 
        addRandomItems("screwdriver", 1 + random.nextInt(4));  // Randomly add 1 to 4 screwdrivers 
    } 
     
    /** 
     * Add a given item to a random number of rooms. 
     * @param item The item to add ("key" or "screwdriver"). 
     * @param count The number of items to add. 
     */ 
    private void addRandomItems(String item, int count) { 
        for (int i = 0; i < count; i++) { 
            int x = 1 + random.nextInt(WIDTH); 
            int y = 1 + random.nextInt(HEIGHT); 
            String roomId = x + "" + y; 
            Room room = theSquare.get(roomId); 
            room.addItem(item); 
        } 
    } 
     
    /** 
     * Checks an x & y coordinate.  Returns true if the location is valid, 
     * i.e., it's within the grid (default is 5x5). 
     */ 
    public boolean isValid(int x, int y) { 
        return x >= 1 && x <= WIDTH && y >= 1 && y <= HEIGHT; 
    } 
     
    /** 
     * Will set the exit for a room if the exit should exists (that is, the room  
     * is in a valid location. 
     */ 
    private void addExit(Room room, String direction, int x, int y) { 
         
        if (isValid(x, y)) { 
            String exitId = x + "" + y;    
             
            if (!theSquare.containsKey(exitId)) { 
                theSquare.put(exitId, new Room(exitId));     
            } 
             
            Room exit = theSquare.get(exitId); 
            room.setExit(direction, exit);  
        } 
    } 
     
    /** 
     * Given a room id, returns the room object associate with that id. 
     * This method does not check if the roomId is valid. 
     */ 
    public Room getRoom(String roomId) { 
        return theSquare.get(roomId); 
    }
    
        /**
     * Get all rooms in the square.
     * @return A collection of all rooms.
     */
    public Collection<Room> getAllRooms() {
        return theSquare.values();
    }
}