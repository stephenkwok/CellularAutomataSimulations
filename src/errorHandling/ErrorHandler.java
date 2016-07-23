package errorHandling;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Handles errors
 * 
 * @author Stephen
 *
 */
public class ErrorHandler {

	/**
	 * Generates alert dialog informing user of an error
	 * @param errorMessage: Message to display to the user
	 */
	public static void handleError(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(errorMessage);
		alert.show();
	}
		
}
