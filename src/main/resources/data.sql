insert into vehicle_type(description) values('MOTO');
insert into vehicle_type(description) values('CARRO');

insert into price(vehicle_type,hour_price,day_price,high_engine_price) values((select t.id from vehicle_type t where t.description = 'MOTO'),500,4000,2000);
insert into price(vehicle_type,hour_price,day_price,high_engine_price) values((select t.id from vehicle_type t where t.description = 'CARRO'),1000,8000,0);

insert into admin(hours_for_a_day,capacity,vehicle_type,engine_capacity,restrict_plaque_letter) values(9,20,(select t.id from vehicle_type t where t.description = 'MOTO'),500,'A');
insert into admin(hours_for_a_day,capacity,vehicle_type,engine_capacity,restrict_plaque_letter) values(9,20,(select t.id from vehicle_type t where t.description = 'CARRO'),0,'A');
