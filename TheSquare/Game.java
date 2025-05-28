import java.util.Random;
import java.util.HashSet;
import java.util.Set;
/**
 * The Game class orchestrates the mechanics of the game, initializing the game environment,
 * processing user actions, and managing robot interactions and movements.
 * It uses other classes such as Parser for command interpretation, Player to represent the user,
 * Square to manage the game map, and MapRenderer for displaying the game state.
 * The game loop continues until the player quits,loses or achieves the win condition.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class Game {
    private Parser parser;
    private Player player;
    private Square theSquare;
    private MapRenderer mapRenderer;

    public Game() {
        parser = new Parser();
        theSquare = new Square();
        Room startRoom = theSquare.getRoom("11");// Start at Room (1,1)
        player = new Player(startRoom);
        mapRenderer = new MapRenderer(player, theSquare);
        initializeRobots();
    }

    /**
     * Initializes robots by placing them randomly in the game square,
     * ensuring they do not start in the player's initial room.
     */
    private void initializeRobots() {
        Random random = new Random();
        int rookX, rookY, bishopX, bishopY;

        do {
            rookX = random.nextInt(5) + 1;
            rookY = random.nextInt(5) + 1;
        } while (rookX == 1 && rookY == 1);  // Ensure not Room (1,1)

        do {
            bishopX = random.nextInt(5) + 1;
            bishopY = random.nextInt(5) + 1;
        } while (bishopX == 1 && bishopY == 1);  // Ensure not Room (1,1)

        theSquare.getRoom(rookX + "" + rookY).addRobot(new Rook());
        theSquare.getRoom(bishopX + "" + bishopY).addRobot(new Bishop());
    }

    /**
     * Starts the game loop which continues until the player decides to quit or wins/loses.
     * Processes user input, displays game states, and handles robot interactions.
     */
    public void play() {
        printWelcome();
    
        boolean finished = false;
        while (!finished) {
            Action action = parser.getAction();
    
            // Skip robot-related actions for "map" or "help"
            if (action.getActionWord() != null &&
                (action.getActionWord().equals("map") || action.getActionWord().equals("help"))) {
                if (action.getActionWord().equals("map")) {
                    mapRenderer.drawMap();
                } else {
                    printHelp();
                }
                continue; // Skip robot actions
            }
    
            // Process player action
            finished = processAction(action);
    
            // Handle interaction with robots in the current room immediately after player action
            if (!finished) { // Check interactions only if the game isn't finished
                handleRobotInteraction();
    
                // Check if the action was "quit" to avoid moving robots
                if (action.getActionWord() != null && action.getActionWord().equals("quit")) {
                    break; // Exit the loop immediately to avoid further processing
                }
    
                // Handle robot movement after player action and interaction
                handleRobotMovement();
    
                // Check for interactions again after robots have moved
                handleRobotInteraction();
                
                // Check if the player has reached the exit room
                if (player.getCurrentRoom().getId().equals("55")) {
                    System.out.println("Congratulations! You've reached the Exit and won the game!");
                    finished = true;
                }
            }
        }
        System.out.println("Thank you for playing. Goodbye.");
    }

    /**
     * Manages the movement of robots after the player's turn.
     * Ensures robots move strategically based on their type and player's actions.
     */
    private void handleRobotMovement() {
        Set<Robot> movedRobots = new HashSet<>();
    
        for (Room room : theSquare.getAllRooms()) {
            if (room.hasRobot()) {
                Robot robot = room.getRobot();
    
                // Skip if this robot has already moved or is disabled
                if (movedRobots.contains(robot) || robot.isDisabled()) {
                    continue;
                }
    
                if (robot instanceof Bishop) {
                    Bishop bishop = (Bishop) robot;
                    boolean avoidPlayer = player.hasItem("screwdriver");
                    String moveDirection = bishop.determineMove(room, player.getCurrentRoom(), avoidPlayer);
                    if (moveDirection != null) {
                        Room nextRoom = room.getExit(moveDirection);
                        if (nextRoom != null && !nextRoom.hasRobot()) {
                            nextRoom.addRobot(bishop);
                            room.removeRobot();
                            System.out.println("Bishop moves to room " + nextRoom.getId());
                        }
                    }
                } else if (robot instanceof Rook) {
                    Rook rook = (Rook) robot;
                    String moveDirection = rook.determineMove(room, player.getCurrentRoom());
                    if (moveDirection != null) {
                        Room nextRoom = room.getExit(moveDirection);
                        if (nextRoom != null && !nextRoom.hasRobot()) {
                            nextRoom.addRobot(rook);
                            room.removeRobot();
                            System.out.println("Rook moves to room " + nextRoom.getId());
                        }
                    }
                    // Rook locks or unlocks a door after moving
                    rook.lockOrUnlockDoor(room, player.getCurrentRoom());
                }
    
                // Mark the robot as moved
                movedRobots.add(robot);
            }
        }
    }

    /**
     * Handles interactions with robots in the same room as the player.
     * Interactions vary based on robot type and whether the player has a scrrwdriver or not.
     */
    private void handleRobotInteraction() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.hasRobot()) {
            Robot robot = currentRoom.getRobot();
            if (!robot.isDisabled()) {
                if (robot instanceof Bishop) {
                    if (player.hasItem("screwdriver")) {
                        robot.disable();
                        System.out.println("You encountered a Bishop and disabled it with your screwdriver.");
                    } else {
                        System.out.println("You encountered a Bishop but didn't have a screwdriver. Game over.");
                        System.exit(0); // Ends the game if player meets Bishop without a screwdriver
                    }
                } else if (robot instanceof Rook) {
                    if (player.hasItem("screwdriver")) {
                        robot.disable();
                        System.out.println("You encountered a Rook and disabled it with your screwdriver.");
                    } else {
                        System.out.println("You encountered a Rook, but nothing happens.");
                    }
                }
            }
        }
    }

    /**
     * Prints a welcoming message at the start of the game and indicates the initial room.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to The Square.");
        System.out.println("Type 'help' if you need help.");
        System.out.println("You are in room id " + player.getCurrentRoom().getId());
        System.out.println();
    }

    /**
     * Processes the player's action based on the command parsed from the input.
     * @param action The action object containing the player's command.
     * @return true if the player wants to quit, false otherwise.
     */
    private boolean processAction(Action action) {
        boolean wantToQuit = false;

        if (action.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String actionWord = action.getActionWord();
        if (actionWord.equals("help")) {
            printHelp();
        } else if (actionWord.equals("go")) {
            goRoom(action);
        } else if (actionWord.equals("quit")) {
            wantToQuit = quit(action);
        } else if (actionWord.equals("drop")) {
            dropItem(action);
        } else if (actionWord.equals("lock")) {
            lockExit(action);
        } else if (actionWord.equals("pick-up")) {
            pickUpItem(action);
        } else if (actionWord.equals("search")) {
            searchRoom();
        } else if (actionWord.equals("unlock")) { 
            unlockExit(action);
        }

        return wantToQuit;
    }
     
    /** 
     * Unlock an exit in the current room. 
     * @param action The action containing the direction to unlock. 
     */ 
    private void unlockExit(Action action) { 
        if (!action.hasSecondWord()) { 
            System.out.println("Unlock which direction?"); 
            return; 
        } 
     
        String direction = action.getSecondWord(); 
     
        if (player.hasItem("key")) { 
            Room currentRoom = player.getCurrentRoom(); 
            if (currentRoom.isExitLocked(direction)) { 
                currentRoom.unlockExit(direction); 
                System.out.println("You unlocked the door to the " + direction + "."); 
            } else { 
                System.out.println("The door to the " + direction + " is already unlocked."); 
            } 
        } else { 
            System.out.println("You need a key to unlock the door."); 
        } 
    } 
     
    /**
     * Searches the current room and displays any items or robots present.
     */
    private void searchRoom() {
            Room currentRoom = player.getCurrentRoom();
            System.out.println(currentRoom.searchRoom());
    }

    /**
     * Displays help information about how to play the game, listing all available actions and the current room.
     */
    private void printHelp() {
        System.out.println("You are trapped in The Square");
        System.out.println("and must find your way out.");
        System.out.println("Beware of Bishop & Rook.");         
        System.out.println();
        System.out.println("Your action words are:");
        parser.showActions();
        System.out.println("You are in room id " + player.getCurrentRoom().getId());
    }

    /**
     * Processes the go action to move the player to another room based on the specified direction.
     * It checks if the exit is locked or if there is no door in the specified direction.
     */
    private void goRoom(Action action) {
        if (!action.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
    
        String direction = action.getSecondWord();
        Room nextRoom = player.getCurrentRoom().getExit(direction);
    
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else if (player.getCurrentRoom().isExitLocked(direction)) {
            System.out.println("That door is locked!");
        } else {
            player.setCurrentRoom(nextRoom);
            System.out.println("You are in room " + player.getCurrentRoom().getId());
        }
    }

    /**
     * Handles the pick-up action, allowing the player to take an item from the current room and add it to their inventory.
     * It verifies that the player is not already carrying an item.
     */
    private void pickUpItem(Action action) {
        if (!action.hasSecondWord()) {
            System.out.println("Pick up what?");
            return;
        }

        String item = action.getSecondWord();
        Room currentRoom = player.getCurrentRoom();

        if (player.getInventory() != null) {
            System.out.println("You are already carrying an item. Drop it first.");
            return;  // Stop further processing if an item is already in the inventory
        }

        if (currentRoom.hasItem(item)) {
            player.addItem(item);
            currentRoom.removeItem(item);
        } else {
            System.out.println("That item is not in this room.");
        }
    }

    /**
     * Allows the player to drop an item from their inventory into the current room.
     */    
    private void dropItem(Action action) {
        if (!action.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String item = action.getSecondWord();

        if (player.hasItem(item)) {
            player.removeItem(item);
            player.getCurrentRoom().addItem(item);
            System.out.println("You dropped: " + item);
        } else {
            System.out.println("You don't have that item.");
        }
    }

    /**
     * Handles the lock action, allowing the player to lock an exit in the current room if they have a key.
     */
    private void lockExit(Action action) {
        if (!action.hasSecondWord()) {
            System.out.println("Lock which direction?");
            return;
        }

        String direction = action.getSecondWord();

        if (player.hasItem("key")) {
            player.getCurrentRoom().lockExit(direction);
            System.out.println("You locked the door to the " + direction + ".");
        } else {
            System.out.println("You need a key to lock the door.");
        }
    }

    /**
     * Processes the quit action, allowing the player to end the game.
     * It checks for extraneous words to ensure the "quit" command is clear.
     */
    private boolean quit(Action action) {
        if (action.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  
        }
    }
}