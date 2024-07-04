package com.nashid.weatherapp.core.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nashid.weatherapp.core.api.WeatherApi;

import java.io.IOException;
import java.time.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UnixTimestampDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String unixTimestamp = jsonParser.getText().trim();
        Instant instant = Instant.ofEpochSecond(Long.valueOf(unixTimestamp));
        ZoneId zoneId = ZoneId.of(WeatherApi.DEFAULT_TIMEZONE);
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }
}
