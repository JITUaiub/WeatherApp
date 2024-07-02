package com.nashid.weatherapp.core.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UnixTimestampListDeserializer extends JsonDeserializer<List<Date>> {

    @Override
    public List<Date> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        List<Date> finalList = new ArrayList<>();
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        for (JsonNode timeNode : jsonNode) {
            finalList.add(new Date(TimeUnit.SECONDS.toMillis(Long.valueOf(timeNode.asLong()))));
        }
        return finalList;
    }
}
