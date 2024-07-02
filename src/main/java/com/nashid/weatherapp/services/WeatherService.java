package com.nashid.weatherapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpMethod;
import com.nashid.weatherapp.core.api.ApiClient;
import com.nashid.weatherapp.core.api.WeatherApi;
import com.nashid.weatherapp.dto.CurrentWeatherResult;
import com.nashid.weatherapp.dto.HourlyWeatherResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WeatherService {
    public CurrentWeatherResult getCurrentWeather(String latitude, String longitude) {
        //todo Dynamic from settings
        String weatherParam = "&current=temperature_2m,wind_speed_10m,rain,weather_code&timeformat=unixtime&timezone=Asia/Dhaka&wind_speed_unit=kmh&precipitation_unit=inch";
        String weatherData = ApiClient.callUrl(WeatherApi.WEATHER_API_FORECAST_URL.concat("?latitude=").concat(latitude).concat("&longitude=").concat(longitude).concat(weatherParam), HttpMethod.GET);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            CurrentWeatherResult weatherResult = objectMapper.readValue(weatherData, CurrentWeatherResult.class);
            return weatherResult;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public HourlyWeatherResult getHourlyWeather(String latitude, String longitude) {
        //todo Dynamic from settings
        String weatherParam = "&forecast_days=1&hourly=temperature_2m,wind_speed_10m,rain,weather_code&timeformat=unixtime&timezone=Asia/Dhaka&wind_speed_unit=kmh&precipitation_unit=inch";
        String weatherData = ApiClient.callUrl(WeatherApi.WEATHER_API_FORECAST_URL.concat("?latitude=").concat(latitude).concat("&longitude=").concat(longitude).concat(weatherParam), HttpMethod.GET);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            HourlyWeatherResult weatherResult = objectMapper.readValue(weatherData, HourlyWeatherResult.class);
            return weatherResult;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
