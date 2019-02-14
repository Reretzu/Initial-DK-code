package bfst19.osmdrawing;

public class OSMNode {
	private float lat, lon;

	public float getLat() {
		return lat;
	}

	public float getLon() {
		return lon;
	}

	public OSMNode(float lon, float lat) {
		this.lat = lat;
		this.lon = lon;
	}
}
