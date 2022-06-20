package f1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

	@FXML
	private Button btnNuovaAuto;

	public void handleBtnNuovaAuto(ActionEvent action) {
		try {
			Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/componentiAuto.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
