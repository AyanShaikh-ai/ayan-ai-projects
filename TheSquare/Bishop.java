import java.util.HashMap;
import java.util.Map;

/**
 * The Bishop class extends the Robot class and represents a unique type of robot in the game.
 * Unlike the Rook, the Bishop's movement strategy is based on either approaching or avoiding the player,
 * depending on specific game conditions, such as the player possessing a particular item like a screwdriver.
 * This behavior makes the Bishop a dynamically challenging opponent, as it alters its tactics based on the player's actions.
 *
 * This class implements a sophisticated movement algorithm that assesses the game map and player position
 * to decide its next move, enhancing the tactical depth of the game. The Bishop can influence the player's
 * navigation and strategy significantly, adding layers of complexity to the game's interactive environment.
 *
 * The Bishop adds a strategic component to the game, providing variability and unpredictability in interactions,
 * which enhances player engagement and the overall challenge of the game.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class Bishop extends Robot {

    public Bishop() {
        super("Bishop");
    }

    /**
     * Determines the direction to move to either approach or avoid the player.
     * @param currentRoom The current room of the Bishop.
     * @param playerRoom The current room of the Player.
     * @param avoidPlayer Whether the Bishop should avoid the player (true) or approach them (false).
     * @return The direction to move.
     */
    public String determineMove(Room currentRoom, Room playerRoom, boolean avoidPlayer) {
        HashMap<String, Room> exits = currentRoom.getExits();
        String bestDirection = null;
        int bestDistance = avoidPlayer ? -1 : Integer.MAX_VALUE;
    
        for (Map.Entry<String, Room> exit : exits.entrySet()) {
            Room nextRoom = exit.getValue();
            if (nextRoom != null && !currentRoom.isExitLocked(exit.getKey())) { // Check if the exit is locked
                int distance = calculateDistance(nextRoom, playerRoom);
                if ((avoidPlayer && distance > bestDistance) || (!avoidPlayer && distance < bestDistance)) {
                    bestDistance = distance;
                    bestDirection = exit.getKey();
                }
            }
        }
    
        return bestDirection; // Null if no valid moves
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