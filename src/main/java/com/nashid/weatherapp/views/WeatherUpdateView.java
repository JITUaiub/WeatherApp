package com.nashid.weatherapp.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("wu-update")
public class WeatherUpdateView extends VerticalLayout {

    private TextField cityTextField;
    private Button searchButton;
    private H1 searchedCity;
    private H1 currentTemperature;
    private Paragraph currentTime;
    private H2 currentDay;
    private Paragraph weatherType;
    private Span humidity;
    private Span wind;

    public WeatherUpdateView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Search bar and city name
        cityTextField = new TextField();
        cityTextField.setPlaceholder("Search by city name");
        cityTextField.setClassName("search-form");
        searchButton = new Button("Search");
        searchedCity = new H1("Bristol");
        searchedCity.setClassName("city-title");

        HorizontalLayout searchLayout = new HorizontalLayout(cityTextField, searchButton, searchedCity);
        searchLayout.setAlignItems(Alignment.CENTER);
        add(searchLayout);

        Anchor celciusLink = new Anchor("#", "C°");
        Anchor fahrenheitLink = new Anchor("#", "F°");
        Span measurements = new Span(celciusLink, new Text(" | "), fahrenheitLink);
        measurements.setClassName("measurements");
        add(measurements);

        // Current weather
        currentTemperature = new H1("4°");
        currentTime = new Paragraph("11:00");
        currentDay = new H2("Today");
        weatherType = new Paragraph("Cloudy");

        VerticalLayout todaysInfo = new VerticalLayout(currentTime, currentDay, weatherType);
        todaysInfo.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        humidity = new Span();
        humidity.setTitle("Humidity: ");
        wind = new Span();
        wind.setTitle("Wind: ");

        UnorderedList sideInfoList = new UnorderedList();
//        sideInfoList.add(new ListItem("Humidity: ", humidity), new ListItem("Wind: ", wind));
        sideInfoList.add(new ListItem(humidity), new ListItem(wind));
        Div sideInfo = new Div(sideInfoList);
        sideInfo.setClassName("side-info");

        HorizontalLayout currentWeatherLayout = new HorizontalLayout(currentTemperature, todaysInfo, sideInfo);
        currentWeatherLayout.setAlignItems(Alignment.CENTER);
        currentWeatherLayout.setClassName("current-weather");
        add(currentWeatherLayout);
        add(new Hr());

        // 5-day forecast
        HorizontalLayout weekForecastLayout = new HorizontalLayout();
        weekForecastLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        String[][] forecastData = {
                {"Fri", "Rain", "2°", "https://img.icons8.com/color-glass/42/000000/rain.png"},
                {"Sat", "Cloudy", "4°", "https://img.icons8.com/color-glass/42/000000/cloud.png"},
                {"Sun", "Partly cloudy", "6°", "https://img.icons8.com/color-glass/42/000000/partly-cloudy-day.png"},
                {"Mon", "Sunny", "8°", "https://img.icons8.com/color-glass/42/000000/sun.png"},
                {"Tues", "Windy", "5°", "https://img.icons8.com/color-glass/42/000000/wind.png"}
        };

        for (String[] forecast : forecastData) {
            VerticalLayout dayForecastLayout = new VerticalLayout();
            dayForecastLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            dayForecastLayout.add(
                    new H3(forecast[0]),
                    new Image(forecast[3], forecast[1]),
                    new Paragraph(forecast[1]),
                    new Span(forecast[2])
            );
            weekForecastLayout.setClassName("week-forecast");
            weekForecastLayout.add(dayForecastLayout);
        }

        add(weekForecastLayout);

        // Footer
        Paragraph footer = new Paragraph();
        footer.add(new Text("Designed and coded by "),
                new Anchor("https://github.com/Tumekab", "Tumeka"),
                new Text("✌🏼"));
        footer.setClassName("footer");
        add(footer);
    }
}
