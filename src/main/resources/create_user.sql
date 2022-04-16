CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    phonenumber VARCHAR(8) NOT NULL,
    address VARCHAR(100) NOT NULL,
    user_type VARCHAR(100) NOT NULL,
    PRIMARY KEY (username)
);


insert into users values ('keith', '{noop}keithpw', 'cskeith', '1234568','address','ROLE_ADMIN');

INSERT INTO users VALUES ('john', '{noop}johnpw', 'john', '1234568','address','ROLE_USER');
