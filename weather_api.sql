CREATE DATABASE IF NOT EXISTS `weather_app`;

use `weather_app`;

CREATE TABLE IF NOT EXISTS `weather_app`.`user` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    role VARCHAR(255) NOT NULL ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `weather_app`.`settings` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    temperature_unit VARCHAR(100) NOT NULL,
    time_zone VARCHAR(100) NOT NULL,
    wind_speed_unit VARCHAR(100) NOT NULL,
    precipitation_unit VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_User FOREIGN KEY (user_id)
    REFERENCES `weather_app`.`user`(id)
);