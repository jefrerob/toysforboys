insert into products(name, scale, description, inStock, inOrder, price, productlineId) VALUES ('test1','test1','test1',10,10,1,(select id from productlines where name ='test')),
                                                                                              ('test2','test2','test2', 10,10,1,(select id from productlines where name = 'test'));