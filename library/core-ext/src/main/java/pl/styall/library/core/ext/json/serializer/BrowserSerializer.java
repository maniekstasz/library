package pl.styall.library.core.ext.json.serializer;

import java.io.IOException;

import pl.styall.library.core.ext.BrowserWrapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BrowserSerializer extends JsonSerializer<BrowserWrapper<?>> {

	@Override
	public void serialize(BrowserWrapper<?> value, JsonGenerator jgen,
			SerializerProvider sp) throws IOException, JsonProcessingException {
		String name = value.getType().getSimpleName() + "s";
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		jgen.writeStartObject();
		jgen.writeObjectField(name, value.getResultList());
		jgen.writeObjectField("total", value.getTotal());
		jgen.writeEndObject();
	}

}
