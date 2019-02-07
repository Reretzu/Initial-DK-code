
package bfst19.linedrawing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Label label = new Label("Line drawing!!!");
		BorderPane pane = new BorderPane(label);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setMinWidth(640);
		stage.setMinHeight(480);
		stage.show();
	}
}
