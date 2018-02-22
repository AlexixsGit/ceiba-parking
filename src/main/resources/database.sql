create database ceibaparking;

use ceibaparking;

create table price
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    vehicle_type bigint(20) not null comment 'tipo de vehiculo',
    hour_price double not null comment 'precio hora',
    day_price double not null comment 'precio del dia',
    high_engine_price double default 0.00 comment 'precio moto alto cilindraje'
);

create table vehicle_type
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    description varchar(50) not null comment 'descripcion del vehiculo(moto,carro)'
);

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

create table sale
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    entry_date datetime not null comment 'Fecha de entrada',
    departure_date datetime not null comment 'Fecha de salida',
    total_time varchar(10) not null comment 'Tiempo total de parqueo',
    invoice bigint(20) not null comment 'Factura de venta'
);

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

create table admin
(
	id bigint(20) primary key auto_increment comment 'Id auto incrementable',
    hours_for_a_day int comment 'Cuantas horas es un dia',
    capacity int comment 'Capacidad de parqueaderos de vehiculos',
    vehicle_type bigint(20) not null comment 'Tipo de vehiculo',
    engine_capacity int comment 'cilindraje',
    restrict_plaque_letter varchar(1) comment 'Letra de la placa restringida'
);


alter table price ADD CONSTRAINT fk_price_vehicle_type FOREIGN KEY (vehicle_type) references vehicle_type(id);
alter table parking ADD CONSTRAINT fk_parking_vehicle_type FOREIGN KEY (vehicle_type) references vehicle_type(id);
alter table sale ADD CONSTRAINT fk_sale_invoice FOREIGN KEY (invoice) references invoice(id);
alter table admin ADD CONSTRAINT fk_admin_vehicle_type FOREIGN KEY (vehicle_type) references vehicle_type(id);

insert into vehicle_type(description) values('Moto');
insert into vehicle_type(description) values('Carro');

insert into price(vehicle_type,hour_price,day_price,high_engine_price) values(1,500,4000,2000);
insert into price(vehicle_type,hour_price,day_price,high_engine_price) values(2,1000,8000,0);

insert into admin(hours_for_a_day,capacity,vehicle_type,engine_capacity,restrict_plaque_letter) values(9,20,1,500,'A');
insert into admin(hours_for_a_day,capacity,vehicle_type,engine_capacity,restrict_plaque_letter) values(9,20,2,0,'A');


select * from parking;
select * from admin;
delete from admin where id  = 3;

select count(*) from parking;

delete from parking where id>0;

update admin set capacity = 10 where id = 1;
update admin set capacity = 20 where id = 2;

commit;

