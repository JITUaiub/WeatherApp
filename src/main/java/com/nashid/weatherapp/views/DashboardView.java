package com.nashid.weatherapp.views;

import com.nashid.weatherapp.dto.Location;
import com.nashid.weatherapp.services.LocationService;
import com.nashid.weatherapp.services.WeatherService;
import com.nashid.weatherapp.views.filters.LocationFilter;
import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Route("dashboard")
@CdiComponent
public class DashboardView extends Div {
    public static final Integer DEFAULT_LOCATION_PAGINATION_MAX = 10;

    private static Map<String, TextField> filterHeaders = new HashMap<>();

    @Inject
    public DashboardView(LocationService locationService, WeatherService weatherService) {
        addClassName("location-view");
        locationService.setLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
        H3 noDataLabel = new H3("Your text doesn't match with any Address, City or Zip Code");
        H3 searchDataLabel = new H3("Please type a Address, City or Zip Code to get started");
        noDataLabel.setVisible(false);
        //Top Layout - Part 1
        Span appName = new Span(LoginView.APPLICATION_NAME);
        appName.addClassName("logo");
        HorizontalLayout layout1 = new HorizontalLayout(appName);
        layout1.setAlignItems(FlexComponent.Alignment.START);
        layout1.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layout1.addClassName("app-logo");

        //Top Layout - Part 2
        TextField cityTextField = new TextField();
        cityTextField.setPlaceholder("Search your Address, City or Zip Code");
        Button searchButton = new Button();
        searchButton.setIcon(VaadinIcon.SEARCH.create());
        HorizontalLayout layout2 = new HorizontalLayout(cityTextField, searchButton);
        layout2.setAlignItems(FlexComponent.Alignment.CENTER);
        layout2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout2.addClassName("search-form");

        //Top Layout - Part 3
        Button settingsButton = new Button();
        Icon settingIcon = VaadinIcon.COG_O.create();
        settingIcon.setColor("white");
        settingsButton.setIcon(settingIcon);
        Button userButton = new Button();
        Icon userIcon = VaadinIcon.USER.create();
        userIcon.setColor("white");
        userButton.setIcon(userIcon);
        HorizontalLayout layout3 = new HorizontalLayout(settingsButton, userButton);
        layout3.setAlignItems(FlexComponent.Alignment.END);
        layout3.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        //Top Layout
        HorizontalLayout topLayout = new HorizontalLayout(layout1, layout2, layout3);
        topLayout.addClassName("top-layout");
        add(topLayout);

        //Weather Dialog
        // Create a dialog
        Dialog dialog = new Dialog();
        dialog.setWidth("800px");
        dialog.setHeight("600px");

        WeatherUpdateView weatherUpdateView = new WeatherUpdateView();
        Button closeButton = new Button("Close", event -> dialog.close());

        dialog.getFooter().add(closeButton);
        dialog.add(weatherUpdateView);

        Grid<Location> grid = new Grid<>(Location.class, false);
        Grid.Column<Location> locationColumn = grid.addColumn(new ComponentRenderer<>(location -> {
            Span locationLink = new Span(location.getLocation());
            locationLink.addClassName("clickable-link-text");
            Span countryCodeBadge = new Span(location.getCountryCode());
            countryCodeBadge.getElement().getThemeList().add("badge primary");
            locationLink.addClickListener(event -> {
                dialog.setHeaderTitle("Weather forecast for " + location.getLocation());
                dialog.open();
            });
            HorizontalLayout horizontalLayout = new HorizontalLayout(locationLink, countryCodeBadge);
            return horizontalLayout;
        })).setWidth("350px").setFlexGrow(0).setHeader("Location");
        Grid.Column<Location> latitudeColumn = grid.addColumn(Location::getLatitude).setTextAlign(ColumnTextAlign.CENTER).setHeader("Latitude");
        Grid.Column<Location> longitudeColumn = grid.addColumn(Location::getLongitude).setTextAlign(ColumnTextAlign.CENTER).setHeader("Longitude");
        Grid.Column<Location> countryColumn = grid.addColumn(Location::getCountry).setTextAlign(ColumnTextAlign.CENTER).setHeader("Country");
        Grid.Column<Location> temperatureColumn = grid.addColumn(Location::getTemperature).setTextAlign(ColumnTextAlign.CENTER).setHeader("Temperature");
        Grid.Column<Location> surfaceWindColumn = grid.addColumn(Location::getSurfaceWind).setTextAlign(ColumnTextAlign.CENTER).setHeader("Surface Wind");
        Grid.Column<Location> rainColumn = grid.addColumn(Location::getRain).setTextAlign(ColumnTextAlign.CENTER).setHeader("Rain");
        Grid.Column<Location> lastUpdatedColumn = grid.addColumn(Location::getLastUpdatedTime).setTextAlign(ColumnTextAlign.CENTER).setHeader("Last Updated");
        Map<String, Grid.Column> components = new HashMap();
        components.put("locationColumn", locationColumn);
        components.put("latitudeColumn", latitudeColumn);
        components.put("longitudeColumn", longitudeColumn);
        components.put("countryColumn", countryColumn);
        components.put("temperatureColumn", temperatureColumn);
        components.put("surfaceWindColumn", surfaceWindColumn);
        components.put("rainColumn", rainColumn);
        components.put("lastUpdatedColumn", lastUpdatedColumn);

        Button loadMoreButton = new Button("Load more ...");
        loadMoreButton.setWidthFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout(loadMoreButton);
        layout3.setAlignItems(FlexComponent.Alignment.CENTER);
        layout3.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        grid.addClassName("custom-grid-header");
        add(searchDataLabel, noDataLabel, grid);
        add(horizontalLayout);

        grid.setVisible(false);
        horizontalLayout.setVisible(false);
        loadMoreButton.addClickListener(buttonClickEvent1 -> {
            loadMoreButton.setEnabled(false);
            locationService.addLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
            List<Location> locations = locationService.getLocationList(cityTextField.getValue(), locationService.getLocationCount());
            locations.forEach(location -> {
                location.setWeatherResult(weatherService.getCurrentWeather(location.getLatitude(), location.getLongitude()));
            });
            loadAndSetLocations(locations, grid, components, noDataLabel, searchDataLabel, locationService);
            horizontalLayout.setVisible(locationService.getLoadMore());
            loadMoreButton.setEnabled(true);
        });
        searchButton.addClickListener(buttonClickEvent -> {
            if (cityTextField.getValue() != null && cityTextField.getValue() != "") {
                locationService.setLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
                List<Location> locations = locationService.getLocationList(cityTextField.getValue(), locationService.getLocationCount());
                locations.forEach(location -> {
                    location.setWeatherResult(weatherService.getCurrentWeather(location.getLatitude(), location.getLongitude()));
                });
                loadAndSetLocations(locations, grid, components, noDataLabel, searchDataLabel, locationService);
                horizontalLayout.setVisible(locationService.getLoadMore());
            }
            else {
                grid.setVisible(false);
                noDataLabel.setVisible(false);
                searchDataLabel.setVisible(true);
                horizontalLayout.setVisible(false);
            }
        });
    }

    private static void loadAndSetLocations(List<Location> locations, Grid<Location> grid, Map<String, Grid.Column> components, H3 noDataLabel, H3 searchDataLabel, LocationService locationService) {
        GridListDataView<Location> dataView = grid.setItems(locations);
        LocationFilter locationFilter = new LocationFilter(dataView);

        if (grid.getHeaderRows().isEmpty()) {
            HeaderRow headerRow = grid.appendHeaderRow();
            headerRow.getCell(components.get("locationColumn")).setComponent(createFilterHeader("Location", locationFilter::setLocation));
            headerRow.getCell(components.get("countryColumn")).setText("Country");
            headerRow.getCell(components.get("latitudeColumn")).setText("Latitude");
            headerRow.getCell(components.get("longitudeColumn")).setText("Longitude");
            headerRow.getCell(components.get("countryColumn")).setText("Country");
            headerRow.getCell(components.get("temperatureColumn")).setText("Temperature");
            headerRow.getCell(components.get("surfaceWindColumn")).setText("Surface Wind");
            headerRow.getCell(components.get("rainColumn")).setText("Rain");
            headerRow.getCell(components.get("lastUpdatedColumn")).setText("Last Updated");
        }
        else {
            grid.getHeaderRows().forEach( headerRow -> {
                headerRow.getCell(components.get("locationColumn")).setComponent(createFilterHeader("Location", locationFilter::setLocation));
            });
        }

        if (locations != null && !locations.isEmpty()) {
            searchDataLabel.setVisible(false);
            noDataLabel.setVisible(false);
            grid.setVisible(true);
        }
        else {
            searchDataLabel.setVisible(false);
            noDataLabel.setVisible(true);
            grid.setVisible(false);
        }
    }

    private static Component createFilterHeader(String labelText, Consumer<String> filterChangeConsumer) {
        TextField textField;
        if (filterHeaders.get(labelText) != null) {
            textField = filterHeaders.get(labelText);
            textField.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));
        }
        else {
            textField = new TextField();
        }
        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }
}
