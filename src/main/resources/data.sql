DELETE FROM fuel_type;
DELETE FROM fuel_consumption;

INSERT INTO fuel_type VALUES (1, '95');
INSERT INTO fuel_type VALUES (2, '98');
INSERT INTO fuel_type VALUES (3, 'D');

INSERT INTO fuel_consumption VALUES(1, 1, 10.1, 2.5, '2019-08-22', 1111);
INSERT INTO fuel_consumption VALUES(2, 2, 11.1, 3.5, '2019-09-22', 1111);
INSERT INTO fuel_consumption VALUES(3, 2, 12.1, 4.5, '2019-08-21', 2222);
INSERT INTO fuel_consumption VALUES(4, 1, 13.1, 5.5, '2019-09-20', 2222);
INSERT INTO fuel_consumption VALUES(5, 1, 14.1, 6.5, '2019-09-19', 2222);