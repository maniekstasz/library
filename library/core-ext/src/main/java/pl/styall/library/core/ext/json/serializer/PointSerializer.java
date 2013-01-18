package pl.styall.library.core.ext.json.serializer;

import java.io.IOException;

import com.vividsolutions.jts.geom.Point;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PointSerializer extends JsonSerializer<Point> {

	@Override
	public void serialize(Point point, JsonGenerator jgen,
			SerializerProvider serializerProvider) throws IOException,
			JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeNumberField("x", point.getCoordinate().x);
		jgen.writeNumberField("y", point.getCoordinate().y);
		jgen.writeEndObject();
	}

}
