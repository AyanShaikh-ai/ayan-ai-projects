import java.util.Set; 
import java.util.HashMap; 
import java.util.ArrayList;
/**
 * Class Room represents a single location within the game's map.
 * Each room can have multiple exits leading to other rooms, items that can be picked up by the player,
 * and potentially a robot. Exits can also be locked or unlocked.
 * This class handles operations related to room navigation, item management, and robot interactions.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class Room 
{ 
    private String id; 
    private HashMap<String, Room> exits; 
    private HashMap<String, Boolean> lockedExits;
    private ArrayList items;
    private Robot robot; 
    /**
     * Constructs a room identified by an id. Initializes lists and maps for managing exits, items, and robots.
     * @param id The unique identifier for the room.
     */ 
    public Room(String id) { 
        this.id = id; 
        exits = new HashMap<>(); 
        lockedExits = new HashMap<>(); 
        items = new ArrayList<>(); 
        robot = null;  
    } 
 
    /**
     * Defines an exit from this room to another.
     * @param direction The cardinal direction of the exit (e.g., "north").
     * @param neighbor The room that lies in the specified direction.
     */ 
    public void setExit(String direction, Room neighbor) { 
        exits.put(direction, neighbor); 
        lockedExits.put(direction, false); 
    } 
     
    /**
     * Locks an exit in a specified direction.
     * @param direction The direction in which to lock the exit.
     */
    public void lockExit(String direction) { 
        if (lockedExits.containsKey(direction)) { 
            lockedExits.put(direction, true); 
        } 
    } 
     
    /**
     * Unlocks an exit in a specified direction.
     * @param direction The direction in which to unlock the exit.
     */
    public void unlockExit(String direction) { 
        if (lockedExits.containsKey(direction)) { 
            lockedExits.put(direction, false); 
        } 
    } 
     
    /**
     * Checks if an exit in a specified direction is locked.
     * @param direction The direction to check.
     * @return true if the exit is locked, false otherwise.
     */
    public boolean isExitLocked(String direction) { 
        return lockedExits.getOrDefault(direction, false); 
    } 
     
    /** 
     * @return The id of the room. 
     */ 
    public String getId() { 
        return id; 
    } 
     
    /**
     * Returns the room that lies in the given direction.
     * If the exit in that direction is locked, it returns null.
     * @param direction The direction of the exit.
     * @return The room in the specified direction, or null if the exit is locked.
     */ 
    public Room getExit(String direction) { 
        if (isExitLocked(direction)) { 
            System.out.println("The door in the " + direction + " direction is locked."); 
            return null; 
        } 
        return exits.get(direction); 
    } 
     
    /** 
     * Add an item to the room. 
     * @param item The item to add. 
     */ 
    public void addItem(String item) { 
        items.add(item); 
    } 
     
    /** 
     * Remove an item from the room. 
     * @param item The item to remove. 
     * @return true if the item was removed, false otherwise. 
     */ 
    public boolean removeItem(String item) { 
        return items.remove(item); 
    } 
     
    /**
     * Returns a list of items currently in the room.
     * @return A list containing the names of all items in the room.
     */ 
    public ArrayList<String> getItems() { 
        return items; 
    } 
     
    /** 
     * Add a robot to the room. 
     */ 
    public void addRobot(Robot robot) { 
        this.robot = robot; 
    } 
     
    /** 
     * Check if the room has a robot. 
     * @return true if there is a robot in the room, false otherwise. 
     */ 
    public boolean hasRobot() { 
        return robot != null; 
    } 
     
    /** 
     * Get the robot in the room. 
     * @return The robot, or null if no robot is present. 
     */ 
    public Robot getRobot() { 
        return robot; 
    } 
     
    /** 
     * Get the locked status of exits in the room. 
     * @return A map of exits and their lock status. 
     */ 
    public HashMap<String, Boolean> getLockedExits() { 
        return lockedExits; 
    } 
     
    /** 
     * Display locked and unlocked exits for debugging or map rendering. 
     * @return A string showing the locked status of all exits. 
     */ 
    public String getExitsStatus() { 
        StringBuilder status = new StringBuilder("Exit status:"); 
        for (String direction : lockedExits.keySet()) { 
            status.append(" ").append(direction).append("("); 
            status.append(lockedExits.get(direction) ? "Locked" : "Unlocked"); 
            status.append(")"); 
        } 
        return status.toString(); 
    } 
     
    /**
     * Provides a detailed description of the room, including items and the presence of any robot.
     * @return A string describing the room's contents and any robot present.
     */
    public String searchRoom() { 
        StringBuilder description = new StringBuilder(); 
        if (!items.isEmpty()) { 
            description.append("Items in the room: ").append(String.join(", ", items)).append(".\n"); 
        } else { 
            description.append("No items in the room.\n"); 
        } 
        if (robot != null) { 
            if (robot.isDisabled()) { 
                description.append("There is a disabled robot in the room.\n"); 
            } else { 
                description.append("There is an active robot in the room.\n"); 
            } 
        } 
        return description.toString(); 
    } 
     
    /** 
     * Check if the room contains a specific item. 
     * @param item The item to check. 
     * @return true if the item is in the room, false otherwise. 
     */ 
    public boolean hasItem(String item) { 
        return items.contains(item); 
    } 
    
    /**
     * Remove the robot from the room.
     */
    public void removeRobot() {
        this.robot = null;
    }
    
    /**
     * Get all exits of the room.
     * @return A map of exit directions to the corresponding rooms.
     */
    public HashMap<String, Room> getExits() {
        return exits;
    }
}