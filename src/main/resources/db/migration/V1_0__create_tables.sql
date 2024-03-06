--CATEGORY
DROP TABLE IF EXISTS category CASCADE;
	CREATE TABLE category (
        id serial NOT NULL,
        name varchar(255),
        primary key (id)
    );
--IMAGE
DROP TABLE IF EXISTS image CASCADE;
    CREATE TABLE image (
        id serial NOT NULL,
        file_name varchar(255),
        product_id integer NOT NULL,
        primary key (id)
    );
--ORDERS
DROP TABLE IF EXISTS orders CASCADE;
    CREATE TABLE orders (
        id serial NOT NULL,
        count integer NOT NULL,
        date_time timestamp(6),
        number varchar(255),
        price float4 NOT NULL,
        status smallint CHECK (status between 0 and 3),
        person_id integer NOT NULL,
        product_id integer NOT NULL,
        primary key (id)
    );
--PERSON
DROP TABLE IF EXISTS person CASCADE;
    CREATE TABLE person (
        id serial NOT NULL,
        login varchar(255),
        password varchar(255),
        role varchar(255) CHECK (role in ('ROLE_ADMIN','ROLE_USER')),
        primary key (id)
    );
--PRODUCT
DROP TABLE IF EXISTS product CASCADE;
    CREATE TABLE product (
        id serial NOT NULL,
        date_time timestamp(6),
        description text NOT NULL,
        price float4 NOT NULL,
        seller varchar(255) NOT NULL,
        title text NOT NULL,
        warehouse varchar(255) NOT NULL,
        category_id integer NOT NULL,
        primary key (id)
    );
--PRODUCT_CARD
DROP TABLE IF EXISTS product_cart CASCADE;
    CREATE TABLE product_cart (
        id serial NOT NULL,
        person_id integer,
        product_id integer,
        primary key (id)
    );

    ALTER TABLE if exists product 
       drop constraint if exists UK_qka6vxqdy1dprtqnx9trdd47c;

    ALTER TABLE if exists product 
       add constraint UK_qka6vxqdy1dprtqnx9trdd47c unique (title);

    ALTER TABLE if exists image 
       add constraint FKgpextbyee3uk9u6o2381m7ft1 
       foreign key (product_id) 
       references product;

    ALTER TABLE if exists orders 
       add constraint FK1b0m4muwx1t377w9if3w6wwqn 
       foreign key (person_id) 
       references person;

    ALTER TABLE if exists orders 
       add constraint FK787ibr3guwp6xobrpbofnv7le 
       foreign key (product_id) 
       references product;

    ALTER TABLE if exists product 
       add constraint FK1mtsbur82frn64de7balymq9s 
       foreign key (category_id) 
       references category;

    ALTER TABLE if exists product_cart 
       add constraint FKsgnkc1ko2i1o9yr2p63ysq3rn 
       foreign key (person_id) 
       references person;

    ALTER TABLE if exists product_cart 
       add constraint FKhpnrxdy3jhujameyod08ilvvw 
       foreign key (product_id) 
       references product;