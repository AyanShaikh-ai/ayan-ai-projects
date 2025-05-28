import java.util.ArrayList;
import java.util.HashMap;

/**
 * The MapRenderer class is responsible for visually representing the current state of the game map to the player.
 * It displays the positions of the player and robots, as well as the status of each room, using a simple textual 
 * representation. This class enhances player navigation and strategic planning by providing a clear and up-to-date 
 * visualization of the game environment.
 *
 * By drawing the game map with characters representing different entities (e.g., player, robots) and room statuses 
 * (e.g., locked doors), the MapRenderer helps the player make informed decisions about movement and interactions.
 * It is crucial for gameplay, allowing the player to assess their surroundings and the impact of their actions 
 * within the game world.
 * 
 * This class plays a vital role in the user interface by bridging the gap between the game's logical structure and 
 * the player's perception, improving the user experience and engagement.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class MapRenderer {
    private Player player;
    private Square theSquare;

    /**
     * Constructs a MapRenderer with references to the player and the game map.
     * This allows the renderer to access the player's current location and the
     * layout of the game environment.
     * @param player The player whose position will be indicated on the map.
     * @param theSquare The game environment containing all the rooms.
     */
    public MapRenderer(Player player, Square theSquare) {
        this.player = player;
        this.theSquare = theSquare;
    }

    /**
     * Draws the entire game map to the console, showing the position of the player,
     * the locations of robots, and the state of each room. It indicates the player's
     * position with 'P', robots with 'R' (Rook) or 'B' (Bishop), and empty rooms with '.'.
     * It also shows the locked status of the exits in the current player's room,
     * enhancing the player's situational awareness.
     */
    public void drawMap() {
        System.out.println("Game Map:");
        for (int y = 5; y >= 1; y--) {
            for (int x = 1; x <= 5; x++) {
                String roomId = x + "" + y;
                Room room = theSquare.getRoom(roomId);
                if (player.getCurrentRoom().getId().equals(roomId)) {
                    System.out.print("P ");
                } else if (room.hasRobot()) {
                    if (room.getRobot() instanceof Bishop) {
                        System.out.print("B ");
                    } else {
                        System.out.print("R ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }

        Room currentRoom = player.getCurrentRoom();
        System.out.println("\nDoor Status in Current Room:");
        for (String direction : currentRoom.getLockedExits().keySet()) {
            boolean isLocked = currentRoom.isExitLocked(direction);
            System.out.println(direction + ": " + (isLocked ? "Locked" : "Unlocked"));
        }

        System.out.println("\nLegend:");
        System.out.println("P - Player");
        System.out.println("R - Rook");
        System.out.println("B - Bishop");
        System.out.println(". - Empty Room");
    }
} 