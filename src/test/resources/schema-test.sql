DROP TABLE IF EXISTS fuel_type CASCADE;
DROP TABLE IF EXISTS fuel_consumption CASCADE;

CREATE TABLE fuel_consumption
(
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  fuel_type_id BIGINT    NOT NULL,
  price        DOUBLE    NOT NULL,
  volume       DOUBLE    NOT NULL,
  date         DATE      NOT NULL,
  driver_id    BIGINT    NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE fuel_type
(
  id           BIGINT      NOT NULL,
  type         VARCHAR(5)  NOT NULL,
  PRIMARY KEY (id)
);