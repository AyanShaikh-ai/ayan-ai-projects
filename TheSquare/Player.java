import java.util.HashMap; 

/**
 * The Player class represents the user's character within the game. This class manages the player's current location,
 * the single item they can carry, and provides methods to interact with the game environment such as moving between rooms,
 * picking up and dropping items. It encapsulates the state and behavior of the player, allowing for easy tracking of
 * their progress and interactions within the game world.
 * 
 * This class is integral for facilitating the core gameplay mechanics such as navigation and item management, 
 * ensuring a cohesive and interactive game experience.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class Player 
{   private Room currentRoom; 
    private String inventory; // Player can carry only one item at a time 
    
    /** 
     * Create a player starting in the given room. 
     * @param startRoom The room where the player starts. 
     */ 
    public Player(Room startRoom) { 
        currentRoom = startRoom; 
        inventory = null; // No item carried initially 
    } 
     
    /** 
     * Get the current room of the player. 
     * @return The current room. 
     */ 
    public Room getCurrentRoom() { 
        return currentRoom; 
    } 
     
    /** 
     * Set the current room of the player. 
     * @param room The new current room. 
     */ 
    public void setCurrentRoom(Room room) { 
        currentRoom = room; 
    } 
     
    /** 
     * Add an item to the player's inventory. 
     * @param item The item to add. 
     */ 
    public void addItem(String item) { 
        if (inventory != null) { 
            System.out.println("You are already carrying an item. Drop it first."); 
            return;  // Ensure the function exits here if an item is already carried. 
        } 
        inventory = item; 
        System.out.println("You picked up: " + item); 
    } 
     
    /** 
     * Remove the item from the player's inventory. 
     * @return true if the item was removed, false otherwise. 
     */ 
    public boolean removeItem(String item) { 
        if (inventory != null && inventory.equals(item)) { 
            inventory = null; 
            return true; 
        } 
        return false; 
    } 
     
    /** 
     * Check if the player has a specific item. 
     * @param item The item to check. 
     * @return true if the player has the item, false otherwise. 
     */ 
    public boolean hasItem(String item) { 
        return inventory != null && inventory.equals(item); 
    } 
     
    /** 
     * Get the item in the player's inventory. 
     * @return The item, or null if no item is carried. 
     */ 
    public String getInventory() { 
        return inventory; 
    } 
}