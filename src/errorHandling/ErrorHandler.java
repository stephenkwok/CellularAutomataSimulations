package errorHandling;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorHandler {

	public static void handleError(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(errorMessage);
		alert.show();
	}
		
}
