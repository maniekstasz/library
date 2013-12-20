package pl.styall.library.core.ext.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class PointDeserializer extends JsonDeserializer<Point> {

	@Override
	public Point deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String location;
		location = jp.getText().replace(',', ' ');
		location = "POINT(" +location +")";
		Point p;
		WKTReader wktReader = new WKTReader();
		try{

			p = (Point) wktReader.read(location);
		}catch(ParseException e){
			throw new RuntimeException("Not a WKT string:" +location);
		}
		return p;
	}

}
