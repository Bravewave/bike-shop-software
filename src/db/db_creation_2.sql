
CREATE TABLE addresses (
    address_id SERIAL,
    house VARCHAR(255),
    street VARCHAR(255), 
    city VARCHAR(255), 
    postcode VARCHAR(7),
    PRIMARY KEY (address_id)
);
CREATE TABLE customers (
    customer_id SERIAL, 
    first_name VARCHAR(255), 
    last_name VARCHAR(255), 
    address_id BIGINT UNSIGNED,
    PRIMARY KEY (customer_id),
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);
CREATE TABLE staff (
    staff_id SERIAL,
    first_name VARCHAR(255), 
    last_name VARCHAR(255), 
    username VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY (staff_id)
);
CREATE TABLE frame_sets (
    frameset_id SERIAL,
    name VARCHAR(255),
    brand VARCHAR(255), 
    serial_number VARCHAR(255), 
    type VARCHAR(8),
    size INT, 
    gears INT, 
    has_shock_absorbers BOOLEAN, 
    unit_cost DOUBLE, 
    stock_count INT,
    PRIMARY KEY (frameset_id)
);
CREATE TABLE wheels (
    wheels_id SERIAL,
    name VARCHAR(255), 
    brand VARCHAR(255), 
    serial_number VARCHAR(255), 
    diameter INT, 
    brake_type VARCHAR(4), 
    wheel_type VARCHAR(8), 
    unit_cost DOUBLE, 
    stock_count INT,
    PRIMARY KEY (wheels_id)
);
CREATE TABLE handlebars (
    handlebars_id SERIAL,
    name VARCHAR(255), 
    brand VARCHAR(255), 
    serial_number VARCHAR(255), 
    handlebar_type VARCHAR(7), 
    unit_cost DOUBLE, 
    stock_count INT,
    PRIMARY KEY (handlebars_id)
);
CREATE TABLE bikes (
    bike_id SERIAL,
    name VARCHAR(255), 
    brand VARCHAR(255), 
    serial_number VARCHAR(255), 
    frameset_id BIGINT UNSIGNED,
    wheels_id BIGINT UNSIGNED,
    handlebars_id BIGINT UNSIGNED,
    unit_cost DOUBLE, 
    stock_count INT,
    custom BOOLEAN,
    PRIMARY KEY (bike_id),
    FOREIGN KEY (frameset_id) REFERENCES frame_sets(frameset_id),
    FOREIGN KEY (wheels_id) REFERENCES wheels(wheels_id),
    FOREIGN KEY (handlebars_id) REFERENCES handlebars(handlebars_id)
);
CREATE TABLE orders (
    order_id SERIAL,
    customer_id BIGINT UNSIGNED, 
    staff_id BIGINT UNSIGNED,
    bike_id BIGINT UNSIGNED,
    order_date DATE, 
    order_status VARCHAR(10),
    PRIMARY KEY (order_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id),
    FOREIGN KEY (bike_id) REFERENCES bikes(bike_id)
);