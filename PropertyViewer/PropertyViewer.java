import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed. Modified, GUI uses JavaFX instead of Swing.
 * 
 * Original authors: Michael Kölling and Josh Murphy
 *
 * @author Jeffery Raphael
 * @version 2.0
 */
public class PropertyViewer extends Application implements EventHandler<ActionEvent>{

    private Portfolio portfolio;
    private PropertyViewerGUI gui;
    private int currentIndex = 0; // Tracks the current index of the property
    private List<Property> favourites = new ArrayList<>(); // List to store favourite properties
    private int favouriteIndex = 0; // Index of the currently displayed favourite property
    private int totalFavourites = 0; // Total number of favourite properties
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        
        gui = new PropertyViewerGUI(this);
        portfolio = new Portfolio("airbnb-london.csv");
        
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene mainScene = new Scene(gui.getMainPane(), 500, 350); //width, height
        
        stage.setTitle("Property Viewer");
        stage.setScene(mainScene);
        stage.setWidth(500);
        stage.setHeight(325);
        stage.setResizable(false);
        
        // Displays the first property in the portfolio automatically as the application is started
        if (portfolio.numberOfProperties() > 0){
            Property firstProperty = portfolio.getProperty(0);
            gui.showProperty(firstProperty);
        }
        
        // Show the Stage (window)
        stage.show();
    }    
    
    
    /**
     * Handles click events, i.e., executed when button is clicked.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        String text = ((Button)actionEvent.getSource()).getText();
        
        switch(text) {
            case "Next":
                nextClick();
                break;
            case "Previous":
                prevClick();
                break;
            case "Toggle Favourite":
                favClick();
            break;
            case "Nearest Neighbour":
                nearClick();
            break;
            default:
                System.out.println("Unknown Button Press");
        }
    }
    
    
    /**
     * Displays the next property in the portfolio.
     * If reaches the end of the list, will loop back to the first property.
     */
    private void nextClick() {
        // increments to next index so can go to next property
        currentIndex = currentIndex + 1;
        
        // Checks if we need to loop to first property
        if (currentIndex >= portfolio.numberOfProperties()){
            currentIndex = 0;
        }
        
        // Displays next property
        Property nextProperty = portfolio.getProperty(currentIndex);
        gui.showProperty(nextProperty);
        
        // Update the favourite label if it's a favourite
        updateFavouriteLabelForCurrentProperty(nextProperty);

    }

    
    /**
     * Displays the previous property in the portfolio.
     * If it reaches the begning of the list, will loop to the last property.
     */    
    private void prevClick() {
        // decrements to previous index so can go to previous property
        currentIndex = currentIndex - 1;
        
        // Checks if we need to loop to last property
        if (currentIndex < 0){
            currentIndex = portfolio.numberOfProperties() - 1;
        }
        
        // Displays previous property
        Property previousProperty = portfolio.getProperty(currentIndex);
        gui.showProperty(previousProperty);
        
        updateFavouriteLabelForCurrentProperty(previousProperty);
    }

    
    /**
     * Toggles favourite status of the property being currently displayed.
     * Gets removed from favourite if it is already marked as favourite.
     * Gets added to favourite if it isnt marked as favourite.
     */    
    private void favClick() {
        Property currentProperty = portfolio.getProperty(currentIndex);

        // Toggle the favourite status on the property
        currentProperty.toggleFavourite();

        if (currentProperty.isFavourite()) {
            // Add to favourites if it was not previously favourite
            favourites.add(currentProperty);
        } else {
            // Remove from favourites if it was previously favourite
            favourites.remove(currentProperty);
        }
        
        updateFavouriteLabelForCurrentProperty(currentProperty);
        
        // Update the favourite index and total favourites
        totalFavourites = favourites.size();
        favouriteIndex = favourites.indexOf(currentProperty) + 1;// for 1-based index

        // Update the favourite label only if the property is a favourite
        if (currentProperty.isFavourite()) {
            gui.updateFavouriteLabel("Favourite " + favouriteIndex + " of " + totalFavourites);
        } else {
            gui.updateFavouriteLabel(""); // Clear the label if not a favourite
        }
    }



    
    /**
     * Finds and displays propetry closest to current property using its location (longitude and latitude).
     */    
    private void nearClick() {
        Property currentProperty = portfolio.getProperty(currentIndex);
        ArrayList<Double> currentVector = currentProperty.getVector();
        
        Property nearestProperty = null;
        double minDistance = Double.MAX_VALUE; //maximum value a double can hold
        
        for(int i=0 ; i < portfolio.numberOfProperties(); i ++){
            if (i != currentIndex){ // so doesnt compare with itself
                Property property = portfolio.getProperty(i);
                ArrayList<Double> propertyVector = property.getVector();
                Double distance = calculateEuclideanDistance(currentVector,propertyVector);
                
                if (distance < minDistance){
                    minDistance = distance;
                    nearestProperty = property;
                }
            }
        }
        
        if (nearestProperty != null){
            gui.showProperty(nearestProperty);
        }
    }
    
    /**
     *  Calculates the Euclidean distance between two vectors.
     *  Will calculate the distance by iterating through each element in the vectors,
     *  Calculating the squared difference for each corresponding pair of elements,
     *  Summing these differences,then taking the square root of the sum.
     */
    private double calculateEuclideanDistance(ArrayList<Double> vector1, ArrayList<Double> vector2 ){
        double sum = 0.0;
        
        for(int i = 0 ; i < vector1.size() ; i++){
            sum += Math.pow(vector1.get(i) - vector2.get(i), 2);
        }
        
        return Math.sqrt(sum);
    }
    

    /**
     * Updates the favourite label for the currently displayed property.
     * If the property is marked as a favourite, displays its position within the favourites list.
     * If not, clears the label.
     */
    private void updateFavouriteLabelForCurrentProperty(Property property) {
        if (property.isFavourite()) {
            // Find position within favourites list
            favouriteIndex = favourites.indexOf(property) + 1;
            totalFavourites = favourites.size();
            gui.updateFavouriteLabel("Favourite " + favouriteIndex + " of " + totalFavourites);
        } else {
            // Clear the label if the property is not a favourite
            gui.updateFavouriteLabel("");
        }
    }
}
