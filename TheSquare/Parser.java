import java.util.Scanner;

/**
 * The Parser class is responsible for interpreting user input into actions within the game. 
 * It reads strings entered by the player, breaking them down into actions and parameters, 
 * and then verifies whether these are valid commands within the game's context.
 *
 * This class works in tandem with the ActionWords class to check if a given command is recognized 
 * and valid, providing a direct interface for user interaction. It ensures that player inputs are 
 * correctly translated into actions that the game engine can understand and respond to, facilitating
 * a seamless and intuitive gameplay experience.
 *
 * The parser's ability to distinguish between known and unknown commands is crucial for guiding the 
 * player's interactions and for providing appropriate feedback, which is essential for maintaining
 * game flow and user engagement.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class Parser 
{
    private ActionWords actions;  
    private Scanner reader;

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        actions = new ActionWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next action from the user.
     */
    public Action getAction() 
    {
        String inputLine;   
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      
                // ignore the rest of the input line.
            }
        }

        if(actions.isAction(word1)) {
            return new Action(word1, word2);
        }
        else {
            return new Action(null, word2); 
        }
    }

    /**
     * Print out a list of valid action words.
     */
    public void showActions()
    {
        actions.showAll();
    }
}