CREATE TABLE ORDERS(
        id bigint(20) NOT NULL AUTO_INCREMENT,
        productId varchar(50) NOT NULL,
        quantity varchar(50) NOT NULL,
        createdDate date,
        PRIMARY KEY (id)
);