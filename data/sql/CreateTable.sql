DROP TABLE RESERVATION;
DROP TABLE RESTAURANT;


CREATE TABLE Restaurant (
                            Id INTEGER PRIMARY KEY NOT NULL,
                            Nom VARCHAR2(30),
                            Adresse VARCHAR2(100),
                            Lat NUMBER(12,8),
                            Lon NUMBER(12,8),
                            NbTables INTEGER
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

commit;


GRANT ALL PRIVILEGES ON restaurant TO e67005u;
GRANT ALL PRIVILEGES ON reservation TO e67005u;

GRANT ALL PRIVILEGES ON restaurant TO e82916u;
GRANT ALL PRIVILEGES ON reservation TO e82916u;

GRANT ALL PRIVILEGES ON restaurant TO e24838u;
GRANT ALL PRIVILEGES ON reservation TO e24838u;