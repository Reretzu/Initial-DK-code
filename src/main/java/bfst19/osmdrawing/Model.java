package bfst19.osmdrawing;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static javax.xml.stream.XMLStreamConstants.*;

public class Model {
	Map<WayType,List<OSMWay>> ways = new EnumMap<>(WayType.class); {
		for (WayType type : WayType.values()) {
			ways.put(type, new ArrayList<>());
		}
	}
	List<Runnable> observers = new ArrayList<>();
	float minlat, minlon, maxlat, maxlon;

	public Iterable<OSMWay> getWaysOfType(WayType type) {
		return ways.get(type);
	}

	public void addObserver(Runnable observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for (Runnable observer : observers) observer.run();
	}

	public Model(List<String> args) throws FileNotFoundException, XMLStreamException {
		String filename = args.get(0);
		XMLStreamReader reader = XMLInputFactory
			.newInstance()
			.createXMLStreamReader(new FileReader(filename));
		Map<Long,OSMNode> idToNode = new HashMap<>();
		OSMWay way = null;
		WayType type = null;
		while (reader.hasNext()) {
			switch (reader.next()) {
				case START_ELEMENT:
					switch (reader.getLocalName()) {
						case "bounds":
							minlat = Float.parseFloat(reader.getAttributeValue(null, "minlat"));
							minlon = Float.parseFloat(reader.getAttributeValue(null, "minlon"));
							maxlat = Float.parseFloat(reader.getAttributeValue(null, "maxlat"));
							maxlon = Float.parseFloat(reader.getAttributeValue(null, "maxlon"));
							break;
						case "node":
							long id = Long.parseLong(reader.getAttributeValue(null, "id"));
							float lat = Float.parseFloat(reader.getAttributeValue(null, "lat"));
							float lon = Float.parseFloat(reader.getAttributeValue(null, "lon"));
							idToNode.put(id, new OSMNode(lon, lat));
							break;
						case "way":
							type = WayType.UNKNOWN;
							way = new OSMWay();
							break;
						case "nd":
							long ref = Long.parseLong(reader.getAttributeValue(null, "ref"));
							way.add(idToNode.get(ref));
							break;
						case "tag":
							String k = reader.getAttributeValue(null, "k");
							String v = reader.getAttributeValue(null, "v");
							if (k.equals("building") && v.equals("yes")) {
								type = WayType.BUILDING;
							}
							break;

					}
					break;
				case END_ELEMENT:
					switch (reader.getLocalName()) {
						case "way":
							ways.get(type).add(way);
							way = null;
					}
					break;
				case PROCESSING_INSTRUCTION: break;
				case CHARACTERS: break;
				case COMMENT: break;
				case SPACE: break;
				case START_DOCUMENT: break;
				case END_DOCUMENT: break;
				case ENTITY_REFERENCE: break;
				case ATTRIBUTE: break;
				case DTD: break;
				case CDATA: break;
				case NAMESPACE: break;
				case NOTATION_DECLARATION: break;
				case ENTITY_DECLARATION: break;
			}
		}
	}
}
