package com.nashid.weatherapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpMethod;
import com.nashid.weatherapp.core.api.ApiClient;
import com.nashid.weatherapp.core.api.WeatherApi;
import com.nashid.weatherapp.dto.Location;
import com.nashid.weatherapp.dto.LocationResult;
import com.nashid.weatherapp.views.DashboardView;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LocationService {

    private Integer locationCount;
    private Boolean loadMore = false;

    public Integer getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(Integer locationCount) {
        this.locationCount = locationCount;
    }

    public void addLocationCount(Integer locationCount) {
        this.locationCount = this.locationCount + locationCount;
    }

    public Boolean getLoadMore() {
        return loadMore;
    }

    public void setLoadMore(Boolean loadMore) {
        this.loadMore = loadMore;
    }

    public List<Location> getLocationList(String cityName, Integer maxResults) {
        maxResults++;
        String locationData = ApiClient.callUrl(WeatherApi.GEO_API_SEARCH_URL.concat("?name=").concat(cityName).concat("&count=").concat(maxResults.toString()), HttpMethod.GET);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            LocationResult locations = objectMapper.readValue(locationData, LocationResult.class);
            if (locations.getResults() != null) {
                if (locations.getResults().size() > getLocationCount()) {
                    List results = locations.getResults();
                    Integer removeIndex = (results.size() - 1);
                    results.remove(removeIndex);
                    this.loadMore = true;
                    return results;
                }
                this.loadMore = false;
                return locations.getResults();
            }
            return new ArrayList<>();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
