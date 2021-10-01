package mfurmane.log.aggregator.datemapper;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
    @Override
    public LocalTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
        return LocalTime.parse(arg0.getText());
    }
}
