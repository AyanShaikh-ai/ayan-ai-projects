import java.util.HashMap;
import java.util.Map;

/**
 * The Rook class extends the Robot class and represents a specific type of robot within the game.
 * It is characterized by its strategic movements and the ability to lock or unlock doors in the game environment,
 * affecting the player's navigation options. The Rook's behavior includes moving toward the player to create
 * challenges and dynamically altering the accessibility of exits to influence the game's flow.
 *
 * This class implements specific movement logic that calculates the optimal path to approach the player,
 * considering the current layout of the game map. Additionally, it includes mechanisms to lock or unlock exits
 * based on its strategy to either aid or hinder the player, depending on the game situation.
 *
 * The Rook enhances the interactive and strategic elements of the game, offering obstacles and assistance
 * that are pivotal for increasing the game's complexity and engagement level.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class Rook extends Robot {

    public Rook() {
        super("Rook");
    }

    /**
     * Determines the direction to move to either approach the player.
     * @param currentRoom The current room of the Rook.
     * @param playerRoom The current room of the Player.
     * @return The direction to move.
     */
    public String determineMove(Room currentRoom, Room playerRoom) {
        HashMap<String, Room> exits = currentRoom.getExits();
        String bestDirection = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Map.Entry<String, Room> exit : exits.entrySet()) {
            if (exit.getValue() != null && !currentRoom.isExitLocked(exit.getKey())) {
                int distance = calculateDistance(exit.getValue(), playerRoom);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestDirection = exit.getKey();
                }
            }
        }

        return bestDirection; // Null if no valid moves
    }

    /**
     * Strategically locks or unlocks a door to assist the Bishop.
     * @param currentRoom The current room of the Rook.
     * @param playerRoom The current room of the Player.
     */
    public void lockOrUnlockDoor(Room currentRoom, Room playerRoom) {
        HashMap<String, Room> exits = currentRoom.getExits();
        String targetDirection = null;
        int bestDistance = Integer.MAX_VALUE;
    
        // Find the exit that brings the Bishop closer to the player
        for (Map.Entry<String, Room> exit : exits.entrySet()) {
            Room nextRoom = exit.getValue();
            if (nextRoom != null) {
                int distance = calculateDistance(nextRoom, playerRoom);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    targetDirection = exit.getKey();
                }
            }
        }
    
        // Toggle the lock status of the chosen exit
        if (targetDirection != null) {
            Room targetRoom = currentRoom.getExit(targetDirection);
            if (targetRoom != null) { 
                if (currentRoom.isExitLocked(targetDirection)) {
                    currentRoom.unlockExit(targetDirection);
                    System.out.println("Rook unlocks the door to the " + targetDirection + " (" + targetRoom.getId() + ").");
                } else {
                    currentRoom.lockExit(targetDirection);
                    System.out.println("Rook locks the door to the " + targetDirection + " (" + targetRoom.getId() + ").");
                }
            }
        }
    }

    /**
     * Calculates the Manhattan distance between two rooms.
     * @param room1 First room.
     * @param room2 Second room.
     * @return The Manhattan distance.
     */
    private int calculateDistance(Room room1, Room room2) {
        int x1 = Integer.parseInt(room1.getId().substring(0, 1));
        int y1 = Integer.parseInt(room1.getId().substring(1, 2));
        int x2 = Integer.parseInt(room2.getId().substring(0, 1));
        int y2 = Integer.parseInt(room2.getId().substring(1, 2));

        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}