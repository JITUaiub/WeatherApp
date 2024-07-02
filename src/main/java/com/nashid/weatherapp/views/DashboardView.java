package com.nashid.weatherapp.views;

import com.nashid.weatherapp.dto.Location;
import com.nashid.weatherapp.services.LocationService;
import com.nashid.weatherapp.views.filters.LocationFilter;
import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.html.H3;
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
    public DashboardView(LocationService locationService) {
        addClassName("location-view");
        locationService.setLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
        H3 noDataLabel = new H3("No data available");
        H3 searchDataLabel = new H3("Search by country or city name to get started");
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
        cityTextField.setPlaceholder("Country or City Name");
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

        Grid<Location> grid = new Grid<>(Location.class, false);
        Grid.Column<Location> locationColumn = grid.addColumn(createLocationRenderer()).setWidth("350px").setFlexGrow(0);
        Grid.Column<Location> latitudeColumn = grid.addColumn(Location::getLatitude).setTextAlign(ColumnTextAlign.CENTER);
        Grid.Column<Location> longitudeColumn = grid.addColumn(Location::getLongitude).setTextAlign(ColumnTextAlign.CENTER);
        Grid.Column<Location> countryCodeColumn = grid.addColumn(Location::getCountryCode).setTextAlign(ColumnTextAlign.CENTER);
        Grid.Column<Location> countryColumn = grid.addColumn(Location::getCountry).setTextAlign(ColumnTextAlign.CENTER);
        Map<String, Grid.Column> components = new HashMap();
        components.put("locationColumn", locationColumn);
        components.put("latitudeColumn", latitudeColumn);
        components.put("longitudeColumn", longitudeColumn);
        components.put("countryCodeColumn", countryCodeColumn);
        components.put("countryColumn", countryColumn);

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
            locationService.addLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
            List<Location> locations = locationService.getLocationList(cityTextField.getValue(), locationService.getLocationCount());
            loadAndSetLocations(locations, grid, components, noDataLabel, loadMoreButton, searchDataLabel);
            loadMoreButton.setVisible(locationService.getLoadMore());
            horizontalLayout.setVisible(true);
        });
        searchButton.addClickListener(buttonClickEvent -> {
            if (cityTextField.getValue() != null && cityTextField.getValue() != "") {
                locationService.setLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
                List<Location> locations = locationService.getLocationList(cityTextField.getValue(), locationService.getLocationCount());
                loadAndSetLocations(locations, grid, components, noDataLabel, loadMoreButton, searchDataLabel);
                horizontalLayout.setVisible(true);
            }
            else {
                noDataLabel.setVisible(false);
                searchDataLabel.setVisible(true);
            }
            loadMoreButton.setVisible(locationService.getLoadMore());
        });
    }

    private static void loadAndSetLocations(List<Location> locations, Grid<Location> grid, Map<String, Grid.Column> components, H3 noDataLabel, Button loadMoreButton, H3 searchDataLabel) {
        GridListDataView<Location> dataView = grid.setItems(locations);
        LocationFilter locationFilter = new LocationFilter(dataView);

        if (grid.getHeaderRows().isEmpty()) {
            HeaderRow headerRow = grid.appendHeaderRow();
            headerRow.getCell(components.get("locationColumn")).setComponent(createFilterHeader("Location", locationFilter::setLocation));
            headerRow.getCell(components.get("countryCodeColumn")).setText("Country Code");
            headerRow.getCell(components.get("countryColumn")).setText("Country");
            headerRow.getCell(components.get("latitudeColumn")).setText("Latitude");
            headerRow.getCell(components.get("longitudeColumn")).setText("Longitude");
            headerRow.getCell(components.get("countryColumn")).setText("Country");
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
            loadMoreButton.setVisible(true);

        }
        else {
            searchDataLabel.setVisible(false);
            noDataLabel.setVisible(true);
            grid.setVisible(false);
            loadMoreButton.setVisible(false);
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

    private static Renderer<Location> createLocationRenderer() {
        return LitRenderer.<Location> of("<vaadin-horizontal-layout style=\"align-items: center;color: #3c91e6;cursor: pointer;text-decoration: underline;margin-left: 10px;\" theme=\"spacing\">"
                 + "  <span> ${item.location} </span>"
                 + "</vaadin-horizontal-layout>")
                .withProperty("location", Location::getLocation);
    }
}
