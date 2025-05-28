/**
 * The Action class represents a command issued by the player during the game. It encapsulates an action word,
 * which is the primary command, and an optional second word that provides further specifics about the action,
 * such as a target or a direction. This structure allows for easy interpretation and processing of player commands
 * within the game logic.
 * 
 * Action objects are created by the Parser class after interpreting user input. They are used throughout the game
 * to determine the appropriate response to player actions, supporting complex interactions within the game environment.
 * The class distinguishes between recognized and unrecognized commands, helping to guide player input and enforce
 * game rules.
 * 
 * This class is essential for the command execution framework of the game, facilitating clear and effective
 * communication between the player's inputs and the game's response mechanisms.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */

public class Action
{
    private String actionWord;
    private String secondWord;

    /**
     * Create a action object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the action. Null if the action
     *                  was not recognised.
     * @param secondWord The second word of the action.
     */
    public Action(String firstWord, String secondWord)
    {
        actionWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Return the action word (the first word) of this action. If the
     * action was not understood, the result is null.
     * @return The action word.
     */
    public String getActionWord()
    {
        return actionWord;
    }

    /**
     * @return The second word of this action. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return true if this action was not understood.
     */
    public boolean isUnknown()
    {
        return (actionWord == null);
    }

    /**
     * @return true if the action has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}  