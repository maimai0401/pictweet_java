CREATE TABLE IF NOT EXISTS users (
   id       INT             NOT NULL AUTO_INCREMENT,
   username VARCHAR(128)    NOT NULL UNIQUE,
   password VARCHAR(512)    NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tweets (
   id         INT       NOT NULL AUTO_INCREMENT,
   name      VARCHAR(256) NOT NULL,
   content    VARCHAR(256) NOT NULL,
   image     VARCHAR(256) NOT NULL,
PRIMARY KEY (id)
);