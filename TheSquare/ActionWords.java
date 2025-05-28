/**
 * The ActionWords class manages a predefined list of valid action words known to the game. 
 * It is used to recognize and validate commands entered by the player, ensuring that only 
 * supported actions are processed. This class supports the Parser by providing a method to 
 * check whether a given string is a recognized action word, enhancing the robustness and 
 * reliability of command parsing.
 *
 * By maintaining a centralized repository of valid actions, the ActionWords class helps 
 * standardize command interpretation across the game, simplifying command validation and 
 * execution. It also aids in providing feedback to the player about possible actions they 
 * can take, which is crucial for gameplay guidance and user interaction.
 * 
 * This class is integral to the command handling architecture of the game, facilitating 
 * efficient and error-free player interactions.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class ActionWords
{
    // a constant array that holds all valid action words
    private static final String[] validActions = {
        "go", "quit", "help", "drop", "lock", "map", "pick-up", "search", "unlock"
    };

    /**
     * Constructor - initialise the action words.
     */
    public ActionWords()
    {
        // nothing to do
    }

    /**
     * Check whether a given String is a valid action word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isAction(String aString)
    {
        for (String action : validActions) {
            if (action.equals(aString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print all valid actions to System.out.
     */
    public void showAll() 
    {
        for (String action : validActions) {
            System.out.print(action + "  ");
        }
        System.out.println();
    }
} 