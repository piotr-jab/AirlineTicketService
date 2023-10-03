CREATE DATABASE  IF NOT EXISTS `airline_directory`;
USE `airline_directory`;

DROP FUNCTION IF EXISTS update_date;
DROP FUNCTION IF EXISTS generate_random_connections;
SET GLOBAL time_zone = '+0:00';

DELIMITER $$
-- Date update function - for a fresh dataset
CREATE FUNCTION update_date (flightDay INT, monthsToAdvance INT, flightHour VARCHAR(20))
RETURNS TIMESTAMP
BEGIN
	DECLARE result TIMESTAMP;
    -- Concatenate flightDay, month, year, and flightHour to create a date string
    SET result = STR_TO_DATE(CONCAT(
        flightDay, '-', 
        -- Protection against generating month 00 
        IF(MONTH(DATE_ADD(NOW(), INTERVAL monthsToAdvance MONTH)) = 12, 1, MONTH(DATE_ADD(NOW(), INTERVAL monthsToAdvance MONTH))), '-', 
        IF(MONTH(DATE_ADD(NOW(), INTERVAL monthsToAdvance MONTH)) < MONTH(NOW()), YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)), YEAR(NOW())), ' ', 
        flightHour), '%d-%m-%Y %H:%i:%s');
    RETURN result;
END$$
DELIMITER ;
DELIMITER $$

DELIMITER $$
CREATE FUNCTION generate_random_connections()
RETURNS VARCHAR(255)
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE prev_value varchar(45) DEFAULT " ";
  DECLARE date_value varchar(45) DEFAULT " ";
  
  -- Loop to insert 5000 random connections
  WHILE i <= 5000 DO
    -- Generate a random departure date using the update_date function
    SET date_value := update_date(FLOOR(RAND() * 28) + 1, FLOOR(RAND() * 3),  CONCAT(
            LPAD(FLOOR(RAND() * 24), 2, '0'), ':', 
            LPAD(FLOOR(RAND() * 60), 2, '0'), ':', 
            LPAD(FLOOR(RAND() * 60), 2, '0')));
	-- Insert a random connection into the 'connections' table
    INSERT INTO connections (id, origin_airport, destination_airport, departure_date, arrival_date)
    SELECT 
       i AS id,
      (SELECT ICAO FROM airport ORDER BY RAND() LIMIT 1) AS origin_airport,
      (SELECT ICAO FROM airport WHERE ICAO != origin_airport ORDER BY RAND() LIMIT 1) AS destination_airport,
      date_value AS departure_date,
      FROM_UNIXTIME(UNIX_TIMESTAMP(date_value) + FLOOR(RAND() * 14400) + 7200) AS arrival_date
    FROM 
      (SELECT 1) n1,
      (SELECT 1) n2
    LIMIT 1;
    
    -- Store the destination airport for the next iteration (avoids repetition of airports)
    SET prev_value := (SELECT destination_airport FROM connections ORDER BY id DESC LIMIT 1);
    
	-- Insert seat data
    -- jim
    SET @seat_number := CONCAT(FLOOR(RAND() * 8) + 1, CHAR(FLOOR(RAND() * 6) + 65));
    INSERT INTO seats (connection, seat_number, passenger)
    VALUES (i, @seat_number, 'jim');
    -- mary
    SET @seat_number := CONCAT(FLOOR(RAND() * 8) + 9, CHAR(FLOOR(RAND() * 6) + 65));
    INSERT INTO seats (connection, seat_number, passenger)
    VALUES (i, @seat_number, 'mary');
    -- susan
    SET @seat_number := CONCAT(FLOOR(RAND() * 8) + 17, CHAR(FLOOR(RAND() * 6) + 65));
    INSERT INTO seats (connection, seat_number, passenger)
    VALUES (i, @seat_number, 'susan');
    -- liam
    SET @seat_number := CONCAT(FLOOR(RAND() * 8) + 25, CHAR(FLOOR(RAND() * 6) + 65));
    INSERT INTO seats (connection, seat_number, passenger)
    VALUES (i, @seat_number, 'liam');
    
    SET i := i + 1;
  END WHILE;
  RETURN 'Random values inserted successfully.';
END $$
DELIMITER ;



-- Clearing data
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `plane`;
DROP TABLE IF EXISTS `airport`;
DROP TABLE IF EXISTS `connections`;
DROP TABLE IF EXISTS `seats`;
SET FOREIGN_KEY_CHECKS = 1;

-- Table structure for `users` table
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `gender` char(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- Data for table `users`
INSERT INTO `users` 
VALUES 
('john','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1, 'John', 'Krasinksi', 'm'),
('mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1, 'Mary', 'Sue', 'f'),
('susan','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1, 'Susan', 'Clarke', 'f'),
('jim','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1, 'Jim', 'Porter', 'm'),
('liam','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1, 'Liam', 'Howard', 'm');
-- password is "fun123"

-- Table structure for `authorities` table
CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- Data for table `authorities`
INSERT INTO `authorities` 
VALUES 
('john','ROLE_USER'),
('mary','ROLE_USER'),
('mary','ROLE_MANAGER'),
('susan','ROLE_USER'),
('susan','ROLE_ADMIN'),
('jim','ROLE_USER'),
('liam','ROLE_USER');

-- Table structure for `plane` table
CREATE TABLE `plane` (
  `registration` varchar(45) NOT NULL,
  `manufacturer` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  `production_year` varchar(45) NOT NULL,
  `version` varchar(45) NOT NULL,
  `deck_width` varchar(45) NOT NULL,
  `deck_length` varchar(45) NOT NULL,
  PRIMARY KEY (`registration`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data for table `plane`
INSERT INTO `plane` 
VALUES 
('SP-RZG','Boeing','737','2021','MAX 8-200', '6', '33'),
('EI-EBY','Boeing','737','2009','8AS', '6', '33'),
('SP-RSP','Boeing','737','2017','8AS', '6', '33'),
('G-RUKD','Boeing','737','2007','8-AS', '6', '33');

-- Table structure for `airport` table 
CREATE TABLE `airport` (
  `ICAO` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  PRIMARY KEY (`ICAO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- Data for `airport` table
INSERT INTO `airport` 
VALUES 
('EPWR','Poland','Wroclaw'),
('EPMO','Poland','Warsaw-Modlin'),
('LIRA','Italy','Rome-Campiano'),
('EGPF','United Kingdom','Glasgow'),
('LFPG','France','Paris'),
('LEBL','Spain','Barcelona');

-- Table structure for `connections` table
CREATE TABLE `connections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `origin_airport` varchar(45) DEFAULT NULL,
  `destination_airport` varchar(45) DEFAULT NULL,
  `departure_date` TIMESTAMP NOT NULL,
  `arrival_date` TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `connections_ibfk_1` FOREIGN KEY (`origin_airport`) REFERENCES `airport` (`ICAO`),
  CONSTRAINT `connections_ibfk_2` FOREIGN KEY (`destination_airport`) REFERENCES `airport` (`ICAO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- Data for table `connections`

-- Table structure for 'seats' table
CREATE TABLE `seats` (
	`connection` int(11) NOT NULL,
    `seat_number` varchar(45) NOT NULL,
    `passenger` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`connection`, `seat_number`),
    CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`connection`) REFERENCES `connections` (`id`),
    CONSTRAINT `seats_ibfk_2` FOREIGN KEY (`passenger`) REFERENCES `users` (`username`),
    CONSTRAINT `seats_seat_number_ck` CHECK (`seat_number` REGEXP '^(1[0-9]|[1-9])|[1-3][0-9][A-F]$')
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Call the function
SELECT generate_random_connections();
