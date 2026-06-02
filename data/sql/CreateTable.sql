DROP TABLE TABLERESERVATION;
DROP TABLE TABLES;
DROP TABLE RESERVATION;
DROP TABLE RESTAURANT;


CREATE TABLE Restaurant (
                            Id INTEGER PRIMARY KEY NOT NULL,
                            Nom VARCHAR2(30),
                            Adresse VARCHAR2(100),
                            Lat NUMBER(12,8),
                            Lon NUMBER(12,8)
);

CREATE TABLE Reservation (
                             IdRes INTEGER PRIMARY KEY NOT NULL,
                             NomCli VARCHAR2(30),
                             PrenomCli VARCHAR2(30),
                             NumTel VARCHAR2(30),
                             IdRestaurant INTEGER,
                             DateRes DATE,
                             NbConvives INTEGER,

                             CONSTRAINT fk_RestoReserv FOREIGN KEY (IdRestaurant) REFERENCES RESTAURANT(Id)
);

CREATE TABLE Tables (
                        IdTable INTEGER NOT NULL PRIMARY KEY,
                        NbPlaces INTEGER
);

CREATE TABLE TableReservation (
                                  IdRes INTEGER NOT NULL,
                                  IdTable INTEGER NOT NULL,

                                  CONSTRAINT pk_TableReservation PRIMARY KEY (IdRes, IdTable),

                                  CONSTRAINT fk_TableRes1 FOREIGN KEY (IdRes) REFERENCES Reservation (IdRes),
                                  CONSTRAINT fk_TableRes2 FOREIGN KEY (IdTable) REFERENCES Tables (IdTable)

);

commit;


GRANT ALL PRIVILEGES ON restaurant TO e67005u;
GRANT ALL PRIVILEGES ON reservation TO e67005u;
GRANT ALL PRIVILEGES ON tables TO e67005u;
GRANT ALL PRIVILEGES ON tablereservation TO e67005u;

GRANT ALL PRIVILEGES ON restaurant TO e82916u;
GRANT ALL PRIVILEGES ON reservation TO e82916u;
GRANT ALL PRIVILEGES ON tables TO e82916u;
GRANT ALL PRIVILEGES ON tablereservation TO e82916u;

GRANT ALL PRIVILEGES ON restaurant TO e24838u;
GRANT ALL PRIVILEGES ON reservation TO e24838u;
GRANT ALL PRIVILEGES ON tables TO e24838u;
GRANT ALL PRIVILEGES ON tablereservation TO e24838u;