package bfst19.osmdrawing;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;

public class View {
	Canvas canvas = new Canvas(640,480);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	BorderPane pane = new BorderPane(canvas);
	Scene scene = new Scene(pane);
	Affine transform = new Affine();
	Stage stage;
	Model model;

	public View(Model model, Stage stage) {
		transform.appendScale(0.56,-1);
		this.stage = stage;
		this.model = model;
		stage.widthProperty().addListener((obj, oldvalue, newvalue) -> {
			canvas.setWidth(newvalue.doubleValue());
			repaint();
		});
		stage.heightProperty().addListener((obj, oldvalue, newvalue) -> {
			canvas.setHeight(newvalue.doubleValue());
			repaint();
		});
		model.addObserver(this::repaint);
		stage.setScene(scene);
		repaint();
		stage.show();
	}

	public void repaint() {
		gc.setTransform(new Affine());
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setTransform(transform);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1/Math.sqrt(Math.abs(transform.determinant())));
		for (OSMWay way : model) way.stroke(gc);
	}

	public void pan(double dx, double dy) {
		transform.prependTranslation(dx, dy);
		repaint();
	}

	public void zoom(double factor, double x, double y) {
		transform.prependScale(factor, factor, x, y);
		repaint();
	}

	public Point2D modelCoords(double x, double y) {
		try {
			return transform.inverseTransform(x, y);
		} catch (NonInvertibleTransformException e) {
			e.printStackTrace();
			return null;
		}
	}
}
