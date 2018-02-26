
use ceibaparking;

drop table if exists admin;
create table admin
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    hours_for_a_day int comment 'Cuantas horas es un dia',
    capacity int comment 'Capacidad de parqueaderos de vehiculos',
    vehicle_type bigint(20) not null comment 'Tipo de vehiculo',
    engine_capacity int comment 'cilindraje',
    restrict_plaque_letter varchar(1) comment 'Letra de la placa restringida'
);

drop table if exists parking;
create table parking
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    vehicle_type bigint(20) not null comment 'Tipo de vehiculo',
    plaque varchar(10) not null comment 'placa del vehiculo',
    entry_date datetime not null comment 'fecha de ingreso del vehiculo',
    entry_hour varchar(10) not null comment 'hora de entrada',
    departure_date datetime comment 'fecha de salida',
    departure_hour varchar(10) comment 'hora de salida',
    engine_capacity int comment 'cilindraje'
);

drop table if exists price;
create table price
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    vehicle_type bigint(20) not null comment 'tipo de vehiculo',
    hour_price double not null comment 'precio hora',
    day_price double not null comment 'precio del dia',
    high_engine_price double default 0.00 comment 'precio moto alto cilindraje'
);

drop table if exists vehicle_type;
create table vehicle_type
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    description varchar(50) not null comment 'descripcion del vehiculo(moto,carro)'
);

drop table if exists sale;
create table sale
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    entry_date datetime not null comment 'Fecha de entrada',
    departure_date datetime not null comment 'Fecha de salida',
    total_time varchar(10) not null comment 'Tiempo total de parqueo',
    invoice bigint(20) not null comment 'Factura de venta'
);

drop table if exists invoice;
create table invoice
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    plaque varchar(10) not null comment 'placa del vehiculo',
    desc_vehicle_type varchar(50) not null comment 'descripcion del vehiculo(moto,carro)',
    engine_capacity int comment 'cilindraje',
    employee varchar(100) not null comment 'Empleado que registra el vehiculo',
    iva double null comment 'Iva',
    additional_cost double comment 'Costo adicional',
    subtotal double not null comment 'Subtotal',
    total double not null comment 'Valor total de la factura'
);











