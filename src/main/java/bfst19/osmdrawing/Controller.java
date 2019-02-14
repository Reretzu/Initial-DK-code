package bfst19.osmdrawing;

import javafx.geometry.Point2D;
import javafx.stage.Stage;

public class Controller {
	Model model;
	View view;
	double x, y;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		view.scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case V:
					new Controller(model, new View(model, new Stage()));
					break;
			}
		});
		view.scene.setOnMousePressed(e -> {
			x = e.getX();
			y = e.getY();
		});
		view.scene.setOnMouseDragged(e -> {
			if (e.isPrimaryButtonDown()) view.pan(e.getX() - x, e.getY() - y);
			x = e.getX();
			y = e.getY();
		});
		view.scene.setOnScroll(e -> {
			view.zoom(e.getDeltaY(), e.getX(), e.getY());
		});
	}
}
