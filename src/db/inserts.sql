INSERT INTO USERS (user_login, user_password, is_admin) VALUES
                  ('admin', md5('123'), 1);
INSERT INTO USERS (user_login, user_password, is_admin) VALUES
                  ('client', md5('123'), 0);
INSERT INTO USERS (user_login, user_password, is_admin) VALUES
                  ('anyuta', md5('123456'), 0);
INSERT INTO USERS (user_login, user_password, is_admin) VALUES
                  ('mashaPol', md5('123qwe'), 0);
INSERT INTO USERS (user_login, user_password, is_admin) VALUES
                  ('ivanovIA', md5('111qqq'), 0);
INSERT INTO USERS (user_login, user_password, is_admin) VALUES
                  ('petrovKM', md5('petrov123'), 0);
INSERT INTO USERS (user_login, user_password, is_admin) VALUES
                  ('sidorovMA', md5('sidorov123'), 0);


INSERT INTO CLIENTS
        (user_id, last_name, first_name, patronymic_name, email,
                  pasp_series, pasp_number, pasp_prsl_number, birthday, address, telephone, is_banned) VALUES
        (2, 'Karasev', 'Konstantin', 'Fedorovich',"karasevKF@mail.ru", "MP", 2819823, "4190405AO16PB4", '1993-06-12',
        "K Marksa 5, 12", "+375296531223", 0);

INSERT INTO CLIENTS
        (user_id, last_name, first_name, patronymic_name, email,
                  pasp_series, pasp_number, pasp_prsl_number, birthday, address, telephone, is_banned) VALUES
        (3, "Levina", "Anna", "Leonidovna","anyutalevina@mail.ru", "MP", 2812123, "4190205AO16PB4", '1993-06-12',
        "Kozlova 5, 21", "+375293746177", 0);
INSERT INTO CLIENTS
        (user_id, last_name, first_name, patronymic_name, email,
                  pasp_series, pasp_number, pasp_prsl_number, birthday, address, telephone, is_banned) VALUES
        (4, "Polyakova", "Maria", "Aleksandrovna", "polyakovaM@mail.ru", "MP", 2812153, "4190195RE16PB4", '1963-11-12',
        "Masherova 5, 12", "+375296784323", 0);
INSERT INTO CLIENTS
        (user_id, last_name, first_name, patronymic_name, email,
                  pasp_series, pasp_number, pasp_prsl_number, birthday, address, telephone, is_banned) VALUES
        (5, "Ivanov", "Ivan", "Petrovich", "ivanovia@gmail.com", "MP", 2819123, "4480195AO16PB4", '1985-01-21',
         "st Kozlova 7", "+375293246177", 1);
INSERT INTO CLIENTS
        (user_id, last_name, first_name, patronymic_name, email,
                  pasp_series, pasp_number, pasp_prsl_number, birthday, address, telephone, is_banned) VALUES
        (6, "Petrov", "Pavel", "Ivanovich", "petrovia@gmail.com", "MP", 2812523, "4190198SO16PB4",'1994-05-10',
        "st Kozlova 9", "+375293746199", 0);
INSERT INTO CLIENTS
        (user_id, last_name, first_name, patronymic_name, email,
                  pasp_series, pasp_number, pasp_prsl_number, birthday, address, telephone, is_banned) VALUES
        (7, "Sidorov", "Andrey", "Aleksandrovich", "sidorovia@gmail.com", "MP", 2812923, "4190176AO16PB4",'1990-12-05',
         "st Kozlova 12", "+375296541223", 0);
INSERT INTO CLIENTS
        (last_name, first_name, patronymic_name, email,
                  pasp_series, pasp_number, pasp_prsl_number, birthday, address, telephone, is_banned) VALUES
        ( "Prohorova", "Hadya", "Petrovna","prohorovaia@gmail.com", "MP", 2813123,  "4190195AO83PB4",'1968-07-16',
        "st Kozlova 1", "+375293946177", 0);


INSERT INTO TYPE_ROOM (room_type, cost) VALUES ('single', 70);
INSERT INTO TYPE_ROOM (room_type, cost) VALUES ('double', 150);
INSERT INTO TYPE_ROOM (room_type, cost) VALUES ('twin', 150);
INSERT INTO TYPE_ROOM (room_type, cost) VALUES ('lux', 200);
INSERT INTO TYPE_ROOM (room_type, cost) VALUES ('family', 240);
INSERT INTO TYPE_ROOM (room_type, cost) VALUES ('special', 240);


INSERT INTO ROOM (room_type, numb_seats) VALUES ('single', 1);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('double', 2);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('twin', 2);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('twin', 2);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('single', 1);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('single', 1);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('double', 2);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('lux', 2);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('lux', 1);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('twin', 2);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('double', 2);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('single', 1);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('family', 4);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('family', 4);
INSERT INTO ROOM (room_type, numb_seats) VALUES ('special', 4);


INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (1, 1, 'single', '2015-05-15', '2015-06-21', '2015-06-30', 1, 'approved');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (1, 1, 'single','2016-06-15', '2016-07-01', '2016-07-15', 1, 'approved');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (1, NULL, 'twin', '2016-05-15', '2017-06-15', '2017-06-21', 2, 'pending');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (2, 3, 'twin', '2016-05-15', '2016-07-15', '2016-07-21', 2, 'approved');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (2, NULL, 'twin', '2016-05-15', '2017-07-15', '2017-07-21', 2, 'pending');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (3, 2, 'double', '2015-05-15', '2015-05-15', '2015-05-25', 2, 'approved');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (3, NULL, 'family', '2016-08-15', '2017-07-20', '2017-07-28', 3, 'pending');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (3, 7, 'double', '2016-08-15', '2016-08-20', '2016-08-25', 2, 'approved');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (4, NULL, 'family', '2016-06-21', '2017-07-21', '2017-08-02', 3, 'pending');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (4, 11, 'double', '2015-06-21', '2015-07-01', '2015-07-14', 2, 'approved');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (4, NULL, 'double', '2015-06-21', '2015-07-01', '2015-07-14', 2, 'cancel');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (5, NULL, 'single', '2016-06-05', '2017-08-02', '2017-08-10', 1, 'pending');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (6, NULL, 'lux', '2016-06-05', '2017-06-02', '2017-06-10', 1, 'pending');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (6, 4, 'lux','2016-05-05', '2016-08-15', '2016-09-01', 1, 'approved');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (7, 3, 'twin', '2012-03-05', '2012-05-15', '2012-05-23', 2, 'approved');
INSERT INTO REQUESTS (client_id, room_id, room_type, req_date, start_date, end_date,  persons_count, status) VALUES
                      (7, 1, 'single', '2016-08-05', '2017-07-29', '2017-08-10', 1, 'pending');

