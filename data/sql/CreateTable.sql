DROP TABLE RESERVATION;
DROP TABLE RESTAURANT;

/* Restaurant (Id, Nom, Adresse, Lat, Lon) */

CREATE TABLE Restaurant (
                            Id INTEGER PRIMARY KEY NOT NULL,
                            Nom VARCHAR2(30),
                            Adresse VARCHAR2(30),
                            Lat FLOAT,
                            Lon FLOAT
);

/* Reservation (IdRes, NomCli, IdRestaurant, DateRes)*/

CREATE TABLE Reservation (
                             IdRes INTEGER PRIMARY KEY NOT NULL,
                             NomCli VARCHAR2(30),
                             IdRestaurant INTEGER,
                             DateRes DATE,
                             NbTables INTEGER,

                             CONSTRAINT fk_RestoReserv FOREIGN KEY (IdRestaurant) REFERENCES RESTAURANT(Id)
);
commit;

/*
 * Privilèges
 */

GRANT ALL PRIVILEGES ON restaurant TO e67005u;
GRANT ALL PRIVILEGES ON reservation TO e67005u;
GRANT ALL PRIVILEGES ON restaurant TO e82916u;
GRANT ALL PRIVILEGES ON reservation TO e82916u;
GRANT ALL PRIVILEGES ON restaurant TO e24838u;
GRANT ALL PRIVILEGES ON reservation TO e24838u;