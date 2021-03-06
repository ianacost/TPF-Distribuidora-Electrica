--DROP DATABASE udee;
--CREATE DATABASE udee;
--USE udee;

CREATE TABLE IF NOT EXISTS tariff_types (
    id INT NOT NULL AUTO_INCREMENT,
    type_name VARCHAR(50),
    CONSTRAINT PK_tariff_types PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tariffs (
    id INT NOT NULL AUTO_INCREMENT,
    tariff_type_id INT,
    tariff_value FLOAT,
    CONSTRAINT PK_tariffs PRIMARY KEY (id),
    CONSTRAINT FK_tariffs_tariff_types FOREIGN KEY (tariff_type_id) REFERENCES tariff_types(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_types (
    id INT NOT NULL AUTO_INCREMENT,
    type_name VARCHAR(50),
    CONSTRAINT PK_user_types PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    user_type_id INT NOT NULL,
    username VARCHAR(50),
    password VARCHAR(128),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT FK_users_user_types FOREIGN KEY (user_type_id) REFERENCES user_types(id)
);

CREATE TABLE IF NOT EXISTS meter_brands (
    id INT NOT NULL AUTO_INCREMENT,
    brand_name VARCHAR(50),
    CONSTRAINT PK_meter_brands PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS meter_models (
    id INT NOT NULL AUTO_INCREMENT,
    brand_id INT,
    model_name VARCHAR(50),
    CONSTRAINT PK_meter_models PRIMARY KEY (id),
    CONSTRAINT FL_meter_models_brands FOREIGN KEY (brand_id) REFERENCES meter_brands(id)
);

CREATE TABLE IF NOT EXISTS electric_meters (
    id INT NOT NULL AUTO_INCREMENT,
    model_id INT,
    serial_number VARCHAR(50),
    password VARCHAR(50),
    CONSTRAINT PK_electric_meters PRIMARY KEY (id),
    CONSTRAINT FK_electric_meters_meter_models FOREIGN KEY (model_id) REFERENCES meter_models(id)
);

CREATE TABLE IF NOT EXISTS addresses (
    id INT NOT NULL AUTO_INCREMENT,
    tariff_id INT,
    electric_meter_id INT,
    client_id INT,
    street VARCHAR(50),
    address_number VARCHAR(50),
    CONSTRAINT PK_addresses PRIMARY KEY (id),
    CONSTRAINT FK_addresses_tariffs FOREIGN KEY (tariff_id) REFERENCES tariffs(id),
    CONSTRAINT FK_addresses_electric_meters FOREIGN KEY (electric_meter_id) REFERENCES electric_meters(id),
    CONSTRAINT FK_addresses_users FOREIGN KEY (client_id) REFERENCES users(id),
    CONSTRAINT UNQ_electric_meter_id UNIQUE (electric_meter_id)
);

CREATE TABLE IF NOT EXISTS measurements (
    id INT NOT NULL AUTO_INCREMENT,
    electric_meter_id INT,
    measure FLOAT NOT NULL,
    measure_date_time DATETIME,
    price FLOAT,
    CONSTRAINT PK_measurements PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bills (
    id INT NOT NULL AUTO_INCREMENT,
    address_id INT,
    initial_measure_id INT,
    final_measure_id INT,
    bill_date DATE,
    amount_payed FLOAT,
    consumption FLOAT,
    total FLOAT,
    CONSTRAINT PK_bills PRIMARY KEY (id),
    CONSTRAINT FK_bills_address FOREIGN KEY (address_id) REFERENCES addresses(id),
    CONSTRAINT FK_bills_initial_measurement FOREIGN KEY (initial_measure_id) REFERENCES measurements(id),
    CONSTRAINT FK_bills_final_measurement FOREIGN KEY (final_measure_id) REFERENCES measurements(id)
);