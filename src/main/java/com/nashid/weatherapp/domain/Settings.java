package com.nashid.weatherapp.domain;

import com.nashid.weatherapp.enums.PrecipitationUnit;
import com.nashid.weatherapp.enums.TemperatureUnit;
import com.nashid.weatherapp.enums.TimeZone;
import com.nashid.weatherapp.enums.WindSpeedUnit;

import jakarta.persistence.*;

@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TemperatureUnit temperatureUnit;
    @Enumerated(EnumType.STRING)
    private TimeZone timeZone;
    @Enumerated(EnumType.STRING)
    private WindSpeedUnit windSpeedUnit;
    @Enumerated(EnumType.STRING)
    private PrecipitationUnit precipitationUnit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Settings() {
    }

    public Settings(Long id, TemperatureUnit temperatureUnit, TimeZone timeZone, WindSpeedUnit windSpeedUnit, PrecipitationUnit precipitationUnit, User user) {
        this.id = id;
        this.temperatureUnit = temperatureUnit;
        this.timeZone = timeZone;
        this.windSpeedUnit = windSpeedUnit;
        this.precipitationUnit = precipitationUnit;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
