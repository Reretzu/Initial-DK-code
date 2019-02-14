package bfst19.osmdrawing;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static javax.xml.stream.XMLStreamConstants.*;

public class Model implements Iterable<OSMWay> {
	List<OSMWay> ways = new ArrayList<>();
	List<Runnable> observers = new ArrayList<>();

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
		while (reader.hasNext()) {
			switch (reader.next()) {
				case START_ELEMENT:
					switch (reader.getLocalName()) {
						case "node":
							long id = Long.parseLong(reader.getAttributeValue(null, "id"));
							float lat = Float.parseFloat(reader.getAttributeValue(null, "lat"));
							float lon = Float.parseFloat(reader.getAttributeValue(null, "lon"));
							idToNode.put(id, new OSMNode(lon, lat));
							break;
						case "way":
							way = new OSMWay();
							break;
						case "nd":
							long ref = Long.parseLong(reader.getAttributeValue(null, "ref"));
							way.add(idToNode.get(ref));
							break;

					}
					break;
				case END_ELEMENT:
					switch (reader.getLocalName()) {
						case "way":
							ways.add(way);
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

	@Override
	public Iterator<OSMWay> iterator() {
		return ways.iterator();
	}
}
