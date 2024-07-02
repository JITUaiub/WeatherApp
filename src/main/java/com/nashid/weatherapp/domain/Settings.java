package com.nashid.weatherapp.domain;

import com.nashid.weatherapp.enums.PrecipitationUnit;
import com.nashid.weatherapp.enums.TemperatureUnit;
import com.nashid.weatherapp.enums.TimeZone;
import com.nashid.weatherapp.enums.WindSpeedUnit;

public class Settings {
    private TemperatureUnit temperatureUnit;
    private TimeZone timeZone;
    private WindSpeedUnit windSpeedUnit;
    private PrecipitationUnit precipitationUnit;

    public Settings() {
    }

    public Settings(TemperatureUnit temperatureUnit, TimeZone timeZone, WindSpeedUnit windSpeedUnit, PrecipitationUnit precipitationUnit) {
        this.temperatureUnit = temperatureUnit;
        this.timeZone = timeZone;
        this.windSpeedUnit = windSpeedUnit;
        this.precipitationUnit = precipitationUnit;
    }

    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public WindSpeedUnit getWindSpeedUnit() {
        return windSpeedUnit;
    }

    public void setWindSpeedUnit(WindSpeedUnit windSpeedUnit) {
        this.windSpeedUnit = windSpeedUnit;
    }

    public PrecipitationUnit getPrecipitationUnit() {
        return precipitationUnit;
    }

    public void setPrecipitationUnit(PrecipitationUnit precipitationUnit) {
        this.precipitationUnit = precipitationUnit;
    }
}
