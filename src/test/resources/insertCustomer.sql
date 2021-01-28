insert into customers(name,streetAndNumber,city,state,postalCode,countryId)
values ('test','test1','test','test','1111',(select id from countries where name = 'test'));