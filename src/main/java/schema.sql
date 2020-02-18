/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


create table Product(
    ProductID integer,
    Name varchar(50) not null,
    Description varchar(100),
    Category varchar(50) not null,
    ListPrice decimal(6,2) not null,
    QuantityInStock decimal(6,2) not null,

    constraint ProductID_PK primary key (ProductID)
);


create table Customer(
    CustomerID integer auto_increment,
    Username varchar(50) not null UNIQUE,
    FirstName varchar(50) not null,
    Surname varchar(50) not null,
    Password varchar(50) not null,
    Email varchar(100) not null,
    ShippingAddress varchar(150) not null,
    CreditCard varchar(200) not null,

    constraint Customer_PK primary key (CustomerID),

);

create table Sale(
    CustomerID integer,
    SaleID integer auto_increment (1000),
    Date date,
    Status varchar(50),

    constraint Sale_PK primary key (SaleID),
    constraint Sale_Customer foreign key (CustomerID) references Customer,

);

create table SaleItem(
    SaleID integer, 
    ProductID integer,
    QuantityPurchased decimal(6,2) not null,
    SalePrice decimal(6,2) not null,

    constraint SaleItem_PK primary key (SaleID, ProductID),
    constraint SaleItem_Sale foreign key (SaleID) references Sale,
    constraint SaleItem_Product foreign key (ProductID) references Product,

);

