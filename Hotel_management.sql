CREATE DATABASE hotel_db;
USE hotel_db;

CREATE TABLE reservation(
res_id INT auto_increment primary key,
Name VARCHAR(50) NOT NULL,
Room_no INT NOT NULL,
Contact Varchar(10) NOT NULL,
res_date timestamp DEFAULT CURRENT_TIMESTAMP
);

select * from reservation