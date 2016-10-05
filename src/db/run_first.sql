DROP TABLE IF EXISTS REQUESTS;
DROP TABLE IF EXISTS ROOM;
DROP TABLE IF EXISTS CLIENTS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS TYPE_ROOM;
DROP VIEW IF EXISTS AVAILABLE_ROOMS;

CREATE TABLE USERS (
  user_id       INT(11)     NOT NULL AUTO_INCREMENT,
  user_login    VARCHAR(50) NOT NULL,
  user_password VARCHAR(50) NOT NULL,
  is_admin      TINYINT(1)  NOT NULL DEFAULT 0,

  PRIMARY KEY (user_id)
);


CREATE TABLE CLIENTS (
  client_id        INT(11)     NOT NULL AUTO_INCREMENT,
  user_id          INT(11)     NULL,
  last_name        VARCHAR(45) NOT NULL,
  first_name       VARCHAR(45) NOT NULL,
  patronymic_name  VARCHAR(45),
  email            VARCHAR(45),
  pasp_series      CHAR(5)     NOT NULL,
  pasp_number      INT         NOT NULL,
  pasp_prsl_number CHAR(14)    NOT NULL,
  birthday         DATE        NOT NULL,
  address          VARCHAR(80) NOT NULL,
  telephone        VARCHAR(15) NOT NULL,
  is_banned        TINYINT(1)  NOT NULL DEFAULT 0,
  FOREIGN KEY (user_id)
  REFERENCES USERS (user_id)
    ON DELETE CASCADE,

  PRIMARY KEY (client_id)
);

CREATE TABLE TYPE_ROOM (
  room_type VARCHAR(30) NOT NULL,
  cost      DECIMAL     NOT NULL,
  PRIMARY KEY (room_type)
);

CREATE TABLE ROOM (
  room_id    INT(11)     NOT NULL AUTO_INCREMENT,
  room_type  VARCHAR(30) NOT NULL,
  numb_seats INT         NOT NULL,
  FOREIGN KEY (room_type)
  REFERENCES TYPE_ROOM (room_type)
    ON DELETE CASCADE,
  PRIMARY KEY (room_id)
);

CREATE TABLE REQUESTS (
  req_id        INT(11)      NOT NULL AUTO_INCREMENT,
  client_id     INT(11)      NOT NULL,
  room_id       INT(11)      NULL,
  room_type     VARCHAR(30)  NOT NULL,
  req_date      TIMESTAMP(6) NOT NULL,
  start_date    DATE         NOT NULL,
  end_date      DATE         NOT NULL,
  persons_count INT          NOT NULL,
  status        ENUM('cancel', 'approved', 'pending'),
  FOREIGN KEY (client_id)
  REFERENCES CLIENTS (client_id)
    ON DELETE CASCADE,
  FOREIGN KEY (room_id)
  REFERENCES ROOM (room_id)
    ON DELETE CASCADE,
  PRIMARY KEY (req_id)
);

CREATE OR REPLACE VIEW AVAILABLE_ROOMS
AS (
  SELECT
    r.room_id,
    r.numb_seats,
    r.room_type,
    t.cost,
    req.room_id       AS req_room,
    req.persons_count,
    MAX(req.end_date) AS end_date
  FROM room r
    JOIN type_room t USING (room_type)
    LEFT JOIN requests req USING (room_id)
  GROUP BY r.room_id
  HAVING ((req.room_id IS NULL) OR (r.numb_seats - req.persons_count > 0) OR MAX(req.end_date) < NOW())
);




