CREATE DATABASE IF NOT EXISTS empresa;

use empresa;

drop TABLE if EXISTS historics;
drop TABLE if EXISTS tasques;
drop TABLE if EXISTS gerents;
drop TABLE if EXISTS empleats;
drop TABLE if EXISTS equips;
drop TABLE if EXISTS departaments;
drop TABLE if EXISTS empresas;

Create or Replace table empresas (
        id int primary key auto_increment,
        nom varchar(30) not null unique,
        fecha_creacio date not null
);



 
Create or Replace table departaments (
        id int primary key auto_increment,
        nom varchar(30) not null,
        empresa_id int,
        CONSTRAINT fk_departaments_empresas FOREIGN KEY (empresa_id) REFERENCES empresas (id)        
);




Create or Replace table equips (
        id int primary key auto_increment,
        nom varchar(30) not null
);

Create or Replace table empleats (
        id int primary key auto_increment,
        nom varchar(30) not null,
        primer_cognom varchar(30) not null,
        segon_cognom varchar(30),
        dni varchar(9) not null unique,
        telefon varchar(9),
        sou double,
        departament_id int not null,
        equip_id int not null,
        CONSTRAINT fk_empleats_departaments FOREIGN KEY (departament_id) REFERENCES departaments (id),
        CONSTRAINT fk_empleats_equips FOREIGN KEY (equip_id) REFERENCES equips (id)
);








Create or Replace table gerents (
        id int primary key,
        CONSTRAINT fk_gerents_empleats FOREIGN KEY (id) REFERENCES empleats (id)
);




Create or Replace table tasques (
        id int primary key auto_increment,
        nom varchar(100) not null,
        descripcio varchar(100),
        fecha_limit date not null,
        prioritat varchar(20) not null,
        id_equip int not null,
        CONSTRAINT fk_tasques_equips FOREIGN KEY (id_equip) REFERENCES equips (id)
);








Create or Replace table historics (
        id int primary key auto_increment,
	    data_inici date not null,
	    data_fi date not null,
	    comentaris varchar (500),
        id_tasca int not null,
        id_equip int not null,
        CONSTRAINT fk_asignacions_tasques FOREIGN KEY (id_tasca) REFERENCES tasques (id),
        CONSTRAINT fk_asignacions_empleats FOREIGN KEY (id_equip) REFERENCES equips (id)
);


INSERT INTO empresas (nom, fecha_creacio)
VALUES ('Mi Empresa', '2024-12-13');

INSERT INTO departaments (nom, empresa_id)
VALUES ('Recursos Humanos', 1);

INSERT INTO equips (nom)
VALUES ('Equipo A');

INSERT INTO empleats (nom, primer_cognom, segon_cognom, dni, telefon, sou, departament_id, equip_id)
VALUES ('Juan', 'Perez', 'Alvarez', '12345678A', '612345678', 2500.00, 1, 1);

INSERT INTO gerents (id)
VALUES (1);

INSERT INTO tasques (nom, descripcio, fecha_limit, prioritat, id_equip)
VALUES ('Desarrollar módulo de autenticación', 'Desarrollar un módulo para la autenticación de usuarios en la aplicación web', '2024-12-31', 'Alta', 1);

INSERT INTO historics (data_inici, data_fi, comentaris, id_tasca, id_equip)
VALUES ('2024-12-01', '2024-12-10', 'Se completó la primera fase del módulo de autenticación', 1, 1);