package gui.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class Utils {
	
	// STAGE = PALCO
		public static Stage currentStage(ActionEvent event) {
			return (Stage) ((Node) event.getSource()).getScene().getWindow();
		}

		public static Integer tryParseToInt(String str) {
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException e) {
				return null;
			}
		}

}
