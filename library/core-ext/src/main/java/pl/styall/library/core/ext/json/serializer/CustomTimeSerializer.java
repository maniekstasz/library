package pl.styall.library.core.ext.json.serializer;

import java.io.IOException;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomTimeSerializer extends JsonSerializer<LocalTime> {

	private static DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
	@Override
	public void serialize(LocalTime arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		arg1.writeString(formatter.print(arg0));
	}


}
