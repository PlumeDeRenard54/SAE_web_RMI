/* Restaurant (Id, Nom, Adresse, Lat, Lon) */

CREATE TABLE Restaurant (
    Id INTEGER PRIMARY KEY,
    Nom VARCHAR2(30),
    Adresse VARCHAR2(30),
    Lat FLOAT,
    Lon FLOAT
);

/* Reservation (IdRes, NomCli, IdRestaurant, DateRes)*/

CREATE TABLE Reservation (
    IdRes INTEGER PRIMARY KEY,
    NomCli VARCHAR2(30),
    IdRestaurant INTEGER,
    DateRes DATE,
    NbTables INTEGER,

    CONSTRAINT fk_RestoReserv FOREIGN KEY (IdRestaurant) REFERENCES RESTAURANT(Id)
);