insert into products(name, scale, description, inStock, inOrder, price, productlineId) VALUES ('testUnshipped','test','test',10,10,1,(select id from productlines where name ='test')),
                                                                                              ('testShipped','test','test', 10,10,1,(select id from productlines where name = 'test'));