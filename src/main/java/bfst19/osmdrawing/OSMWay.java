package bfst19.osmdrawing;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class OSMWay extends ArrayList<OSMNode> {
	public void stroke(GraphicsContext gc) {
		gc.beginPath();
		OSMNode first = get(0);
		gc.moveTo(first.getLon(), first.getLat());
		for (int i = 1 ; i < size() ; i++) {
			OSMNode cur = get(i);
			gc.lineTo(cur.getLon(), cur.getLat());
		}
		gc.stroke();
	}
}