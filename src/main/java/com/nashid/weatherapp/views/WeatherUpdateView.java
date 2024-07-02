package com.nashid.weatherapp.views;

import com.nashid.weatherapp.dto.HourlyWeatherResult;
import com.nashid.weatherapp.dto.Location;
import com.nashid.weatherapp.services.WeatherService;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherUpdateView extends VerticalLayout {
    private Image weatherCode;
    private Span dailyForecast;
    private Span currentTemperature;
    private Span currentWindSurface;
    private Span currentRain;
    private Span hourlyForecast;

    public WeatherUpdateView(Location location, WeatherService weatherService) {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Daily Forecast
        dailyForecast = new Span("Daily Forecast");
        add(dailyForecast);
        add(new Hr());
        weatherCode = new Image(getWeatherCodeAsIconUrl(location.getWeatherCode(), "100"), getWeatherCodeAsTitle(location.getWeatherCode()));
        add(weatherCode);
        currentTemperature = new Span(location.getTemperature());
        currentWindSurface = new Span("Wind Surface: " + location.getSurfaceWind());
        currentRain = new Span("Rain: " + location.getRain());
        VerticalLayout todaysInfo = new VerticalLayout(currentTemperature, currentWindSurface, currentRain);
        todaysInfo.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(todaysInfo);
        add(new Hr());

        //Hourly Forecast
        hourlyForecast = new Span("Hourly Forecast");
        add(hourlyForecast);
        add(new Hr());

        // 5-day forecast
        HourlyWeatherResult hourlyWeatherResult = weatherService.getHourlyWeather(location.getLatitude(), location.getLongitude());
        List<Map> hourlyForecast = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        for (int i=0; i<hourlyWeatherResult.getHourly().getTime().size(); i++) {
            Map hourly = new HashMap();
            hourly.put("time", simpleDateFormat.format(hourlyWeatherResult.getHourly().getTime().get(i)));
            hourly.put("weatherTitle", getWeatherCodeAsTitle(hourlyWeatherResult.getHourly().getWeather_code().get(i)));
            hourly.put("weatherIconUrl", getWeatherCodeAsIconUrl(hourlyWeatherResult.getHourly().getWeather_code().get(i), "50"));
            hourly.put("temperature", hourlyWeatherResult.getHourly().getTemperature_2m().get(i).toString().concat(" ").concat(hourlyWeatherResult.getHourly_units().getTemperature_2m()));
            hourly.put("surfaceWind", hourlyWeatherResult.getHourly().getWind_speed_10m().get(i).toString().concat(" ").concat(hourlyWeatherResult.getHourly_units().getWind_speed_10m()));
            hourly.put("rain", hourlyWeatherResult.getHourly().getRain().get(i).toString().concat(" ").concat(hourlyWeatherResult.getHourly_units().getRain()));
            hourlyForecast.add(hourly);
        }

        HorizontalLayout weekForecastLayout = new HorizontalLayout();
        weekForecastLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        System.out.println("TESTTTT: " + hourlyForecast.size());
        for (Map forecast : hourlyForecast) {
            VerticalLayout hourlyForecastLayout = new VerticalLayout();
            hourlyForecastLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            hourlyForecastLayout.add(
                    new H3(forecast.get("time").toString()),
                    new Image(forecast.get("weatherIconUrl").toString(), forecast.get("weatherTitle").toString()),
                    new Paragraph(forecast.get("weatherTitle").toString()),
                    new Span(forecast.get("temperature").toString()),
                    new Span(forecast.get("surfaceWind").toString()),
                    new Span(forecast.get("rain").toString())
            );
            weekForecastLayout.add(hourlyForecastLayout);
        }
        weekForecastLayout.setClassName("week-forecast");
        add(weekForecastLayout);
    }

    public String getWeatherCodeAsIconUrl(Integer weatherCode, String size) {
        switch (weatherCode) {
            case 0:
                return "https://img.icons8.com/?size=" + size + "&id=50126&format=png&color=000000";
            case 1:
                return "https://img.icons8.com/?size=" + size + "&id=51462&format=png&color=000000";
            case 2:
                return "https://img.icons8.com/?size=" + size + "&id=51462&format=png&color=000000";
            case 3:
                return "https://img.icons8.com/?size=" + size + "&id=50126&format=png&color=000000";
            case 45:
                return "https://img.icons8.com/?size=" + size + "&id=51508&format=png&color=000000";
            case 48:
                return "https://img.icons8.com/?size=" + size + "&id=51508&format=png&color=000000";
            case 51:
                return "https://img.icons8.com/?size=" + size + "&id=51498&format=png&color=000000";
            case 53:
                return "https://img.icons8.com/?size=" + size + "&id=51498&format=png&color=000000";
            case 55:
                return "https://img.icons8.com/?size=" + size + "&id=51498&format=png&color=000000";
            case 56:
                return "https://img.icons8.com/?size=" + size + "&id=51512&format=png&color=000000";
            case 57:
                return "https://img.icons8.com/?size=" + size + "&id=51512&format=png&color=000000";
            case 61:
                return "https://img.icons8.com/?size=" + size + "&id=51451&format=png&color=000000";
            case 63:
                return "https://img.icons8.com/?size=" + size + "&id=51449&format=png&color=000000";
            case 65:
                return "https://img.icons8.com/?size=" + size + "&id=51510&format=png&color=000000";
            case 66:
                return "https://img.icons8.com/?size=" + size + "&id=51468&format=png&color=000000";
            case 67:
                return "https://img.icons8.com/?size=" + size + "&id=51468&format=png&color=000000";
            case 71:
                return "https://img.icons8.com/?size=" + size + "&id=51469&format=png&color=000000";
            case 73:
                return "https://img.icons8.com/?size=" + size + "&id=51470&format=png&color=000000";
            case 75:
                return "https://img.icons8.com/?size=" + size + "&id=51470&format=png&color=000000";
            case 77:
                return "https://img.icons8.com/?size=" + size + "&id=49873&format=png&color=000000";
            case 80:
                return "https://img.icons8.com/?size=" + size + "&id=51455&format=png&color=000000";
            case 81:
                return "https://img.icons8.com/?size=" + size + "&id=51455&format=png&color=000000";
            case 82:
                return "https://img.icons8.com/?size=" + size + "&id=51501&format=png&color=000000";
            case 85:
                return "https://img.icons8.com/?size=" + size + "&id=51469&format=png&color=000000";
            case 86:
                return "https://img.icons8.com/?size=" + size + "&id=51469&format=png&color=000000";
            case 95:
                return "https://img.icons8.com/?size=" + size + "&id=51499&format=png&color=000000";
            case 96:
                return "https://img.icons8.com/?size=" + size + "&id=51499&format=png&color=000000";
            case 99:
                return "https://img.icons8.com/?size=" + size + "&id=51499&format=png&color=000000";
        }
        return "Unknown";
    }

    public String getWeatherCodeAsTitle(Integer weatherCode) {
        switch (weatherCode) {
            case 0:
                return "Clear Sky";
            case 1:
                return "Mainly Clear";
            case 2:
                return "Partly Cloudy";
            case 3:
                return "Overcast";
            case 45:
                return "Fog";
            case 48:
                return "Depositing Rime Fog";
            case 51:
                return "Drizzle: Light";
            case 53:
                return "Drizzle: Moderate";
            case 55:
                return "Drizzle: Dense Intensity";
            case 56:
                return "Freezing Drizzle: Light";
            case 57:
                return "Freezing Drizzle: Dense Intensity";
            case 61:
                return "Rain: Sligh";
            case 63:
                return "Rain: Moderate ";
            case 65:
                return "Rain: Heavy Intensity";
            case 66:
                return "Freezing Rain: Light";
            case 67:
                return "Freezing Rain: Heavy Intensity";
            case 71:
                return "Snow Fall: Slight";
            case 73:
                return "Snow Fall: Moderate";
            case 75:
                return "Snow Fall: Heavy Intensity";
            case 77:
                return "Snow Grains";
            case 80:
                return "Rain Showers: Slight";
            case 81:
                return "Rain Showers: Moderate";
            case 82:
                return "Rain Showers: Violent";
            case 85:
                return "Snow Showers Slight";
            case 86:
                return "Snow Showers Heavy";
            case 95:
                return "Thunderstorm: Slight";
            case 96:
                return "Thunderstorm with Slight Hail";
            case 99:
                return "Thunderstorm with Heavy Hail";
        }
        return "Unknown";
    }
}
