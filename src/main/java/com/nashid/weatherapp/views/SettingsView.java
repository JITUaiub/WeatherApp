package com.nashid.weatherapp.views;

import com.nashid.weatherapp.core.notification.NotificationUtils;
import com.nashid.weatherapp.enums.PrecipitationUnit;
import com.nashid.weatherapp.enums.TemperatureUnit;
import com.nashid.weatherapp.enums.TimeZone;
import com.nashid.weatherapp.enums.WindSpeedUnit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

public class SettingsView extends Dialog {
    public SettingsView() {
        setHeaderTitle("Settings");
        setWidth("600px");
        setHeight("400px");

        Button saveButton = new Button("Save");
        Button closeButton = new Button("Close");
        closeButton.addClickListener(buttonClickEvent -> {
            close();
        });
        getFooter().add(saveButton);
        getFooter().add(closeButton);

        VerticalLayout verticalLayout = new VerticalLayout();
        Select<TimeZone> timeZoneSelect = new Select<>();
        timeZoneSelect.setLabel("Time Zone:");
        timeZoneSelect.setItems(TimeZone.values());
        timeZoneSelect.setValue(TimeZone.Asia_Dhaka);
        verticalLayout.add(timeZoneSelect);
        Select<TemperatureUnit> temperatureUnitSelect = new Select<>();
        temperatureUnitSelect.setLabel("Temperature Unit:");
        temperatureUnitSelect.setItems(TemperatureUnit.values());
        temperatureUnitSelect.setValue(TemperatureUnit.fahrenheit);
        verticalLayout.add(temperatureUnitSelect);
        Select<WindSpeedUnit> windSpeedUnitSelect = new Select<>();
        windSpeedUnitSelect.setLabel("Wind Speed Unit:");
        windSpeedUnitSelect.setItems(WindSpeedUnit.values());
        windSpeedUnitSelect.setValue(WindSpeedUnit.kmh);
        verticalLayout.add(windSpeedUnitSelect);
        Select<PrecipitationUnit> precipitationUnitSelect = new Select<>();
        precipitationUnitSelect.setLabel("Precipitation Unit:");
        precipitationUnitSelect.setItems(PrecipitationUnit.values());
        precipitationUnitSelect.setValue(PrecipitationUnit.mm);
        verticalLayout.add(precipitationUnitSelect);
        add(verticalLayout);

        saveButton.addClickListener(buttonClickEvent -> {
            System.out.println("Save values: " + timeZoneSelect.getValue().name() + " " + temperatureUnitSelect.getValue().name() + " " + windSpeedUnitSelect.getValue().name() + " " + precipitationUnitSelect.getValue().name());
            NotificationUtils.showSuccessMessage("Settings has been updated successfully");
            close();
        });

        open();
    }
}
