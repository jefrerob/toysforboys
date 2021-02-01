insert into orderdetails (orderId, productId, ordered, priceEach)
values ((select id from orders where comments ='testUnshipped'),(select id from products where name ='test1'), 10, 1),
       ((select id from orders where comments ='testShipped'),(select id from products where name ='test2'), 10, 1);