/**
 * The Robot class serves as the base class for all robotic entities in the game, such as the Bishop and Rook.
 * It manages common attributes and behaviors that are shared across different types of robots, including
 * whether a robot is disabled or not. This class allows for polymorphic interactions within the game,
 * enabling robots to be managed generically while supporting specific behaviors through its subclasses.
 * 
 * The class encapsulates fundamental robot functionalities such as enabling/disabling robots, which is crucial
 * for game mechanics where players can interact with these robots. Subclasses extend this class to implement
 * specific movement strategies and interactions unique to their type.
 * 
 * @author Ayan Shaikh
 * @version 2024.12.10
 */
public class Robot 
{ 
    private boolean isDisabled; 
    private String type; 

    /** 
     * Create a robot. Initially, it is not disabled. 
     */ 
    public Robot(String type) { 
        this.type = type; 
        isDisabled = false; 
    } 
     
    public String getType() { 
        return type; 
    } 
     
    /** 
     * Disable the robot. 
     */ 
    public void disable() { 
        isDisabled = true; 
    } 
     
    /** 
     * Check if the robot is disabled. 
     * @return true if the robot is disabled, false otherwise. 
     */ 
     
    public boolean isDisabled() { 
        return isDisabled; 
    } 
}