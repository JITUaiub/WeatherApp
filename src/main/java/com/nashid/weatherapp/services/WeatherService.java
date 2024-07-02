package com.nashid.weatherapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpMethod;
import com.nashid.weatherapp.core.api.ApiClient;
import com.nashid.weatherapp.core.api.WeatherApi;
import com.nashid.weatherapp.dto.Location;
import com.nashid.weatherapp.dto.LocationResult;
import com.nashid.weatherapp.dto.WeatherResult;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WeatherService {
    public WeatherResult getCurrentWeather(String latitude, String longitude) {
        //todo Dynamic from settings
        String weatherParam = "&current=temperature_2m,wind_speed_10m,rain&timeformat=unixtime&timezone=Asia/Dhaka&wind_speed_unit=kmh&precipitation_unit=inch";
        String weatherData = ApiClient.callUrl(WeatherApi.WEATHER_API_FORECAST_URL.concat("?latitude=").concat(latitude).concat("&longitude=").concat(longitude).concat(weatherParam), HttpMethod.GET);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            WeatherResult weatherResult = objectMapper.readValue(weatherData, WeatherResult.class);
            return weatherResult;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
