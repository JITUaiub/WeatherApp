package com.nashid.weatherapp.dto;

import java.util.List;

public class LocationResult {
    List<Location> results;
    Integer generationtime_ms;

    public LocationResult() {}

    public LocationResult(List<Location> results, Integer generationtime_ms) {
        this.results = results;
        this.generationtime_ms = generationtime_ms;
    }

    public List<Location> getResults() {
        return results;
    }

    public void setResults(List<Location> results) {
        this.results = results;
    }

    public Integer getGenerationtime_ms() {
        return generationtime_ms;
    }

    public void setGenerationtime_ms(Integer generationtime_ms) {
        this.generationtime_ms = generationtime_ms;
    }
}
