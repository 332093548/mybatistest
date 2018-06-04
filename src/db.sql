
drop table product;

create table product
(
   id                  int not null auto_increment,
   name                varchar(20),
   price               decimal(8,2),
   remark              longtext,
   date                timestamp default CURRENT_TIMESTAMP,
   primary key (id)
);
/* 数据入库测试*/
insert into product (name,price,remark) values ('computer',3000.00,'test....');
insert into product (name,price,remark) values ('Iphone6',6000.00,'test.....');
-- (page-1)*size =5
select * from product where name like '%xyz%' limit 5,5;