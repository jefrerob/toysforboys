insert into orders(ordered, required, comments, customerId, status)
values ('1111-01-01','1111-01-01', 'testUnshipped',(select id from customers where name = 'test'),'WAITING');
insert into orders(ordered, required, shipped, comments, customerId, status)
values ('1111-01-01','1111-01-01','1111-01-01', 'testShipped',(select id from customers where name = 'test'),'SHIPPED')