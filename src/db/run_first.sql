DROP TABLE IF EXISTS REQUESTS ;
DROP TABLE IF EXISTS ROOM  ;
DROP TABLE IF EXISTS CLIENTS ;
DROP TABLE IF EXISTS USERS ;
DROP TABLE IF EXISTS TYPE_ROOM ;

CREATE TABLE USERS(
  user_id int(11) NOT NULL AUTO_INCREMENT,
  user_login VARCHAR(50) NOT NULL,
  user_password VARCHAR(50) NOT NULL,
  is_admin TINYINT(1) NOT NULL DEFAULT 0,

  PRIMARY KEY (user_id)
);


CREATE TABLE CLIENTS(
  client_id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NULL,
  last_name VARCHAR(45) NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  patronymic_name VARCHAR(45) ,
  email VARCHAR(45),
  pasp_series CHAR(5) NOT NULL ,
  pasp_number int NOT NULL ,
  pasp_prsl_number CHAR(14) NOT NULL ,
  birthday DATE NOT NULL ,
  address VARCHAR(80) NOT NULL ,
  telephone VARCHAR (15) NOT NULL ,
  is_banned TINYINT(1) NOT NULL DEFAULT 0,
  FOREIGN KEY (user_id)
              REFERENCES USERS(user_id)ON DELETE CASCADE,

  PRIMARY KEY (client_id)
);

CREATE TABLE TYPE_ROOM(
  room_type VARCHAR(30) NOT NULL,
  cost DECIMAL NOT NULL,
  PRIMARY KEY (room_type)
);

CREATE TABLE ROOM(
  room_id int(11) NOT NULL AUTO_INCREMENT,
  room_type VARCHAR(30) NOT NULL,
  numb_seats int NOT NULL,
  FOREIGN KEY (room_type)
              REFERENCES TYPE_ROOM(room_type)ON DELETE CASCADE,
  PRIMARY KEY (room_id)
);

CREATE TABLE REQUESTS(
  req_id int(11) NOT NULL AUTO_INCREMENT,
  client_id int(11) NOT NUll,
  room_id int(11)  NULL,
  room_type VARCHAR(30) NOT NULL,
  req_date TIMESTAMP(4) NOT NULL ,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  persons_count int NOT NULL,
  status ENUM('cancel', 'approved', 'pending'),
  FOREIGN KEY (client_id)
              REFERENCES CLIENTS(client_id)ON DELETE CASCADE,
  FOREIGN KEY (room_id)
              REFERENCES ROOM(room_id)ON DELETE CASCADE,
  PRIMARY KEY (req_id)
);



