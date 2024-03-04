--Учетка админа
INSERT INTO Person (login, password, role)
VALUES ('admin', '$2a$10$IShKtJaTQ0mqx.YzDqIv6eWVOBgZ3Gspc7/tEcjl7PZd8aKNJ4V8C', 'ROLE_ADMIN');

--Категории товаров
INSERT INTO category("name")
VALUES ('baths'),('sanitary ware'),('mixers'),('shower stalls'),('kitchen sinks'),('fittings');

--Демо товары
INSERT INTO product(date_time,description,price,seller,title,warehouse,category_id)
VALUES
('2024-03-08 19:22:31.351287','Ванна отдельностоящая Abber FRANKFURT AM9941B 170х75 черная',152460,'Ванны от Васи','Ванна Abber FRANKFURT','Н98',1),
('2024-03-08 19:22:31.351288','Laufen Alessi One Биде подвесное 58.5x39x35.5см, с 1 отв. под смеситель, с покр. LLC, цвет: белый',79892,'Санфаянс ООО','Laufen Alessi One Биде подвесное','Кукушкино',2),
('2024-03-08 19:22:31.351289','Офигенный смеситель',30400,'ОАО Смесстрой','Смеситель для раковины Black&White U7620GM','Р132',3),
('2024-03-08 19:22:31.351290','Душевая кабина Jacuzzi Omega (Омега)',2714900,'ИП Иванов','Душевая кабина Jacuzzi Omega','Н456',4),
('2024-03-08 19:22:31.351291','Create a sleek, minimalist look for your kitchen with the K700 undermount series. Mounted below the worktop for a clean, seamless look ideal for modern, architectural kitchens. These single bowl sinks come with clips to make mounting easy and an optional automatic waste-fitting with push-to-open technology.',66666,'GROHE','GROHE K700 UNDERMOUNT SERIES COMPOSITE SINKS','15676Р',5);

--Демо картинки
INSERT INTO image(file_name,product_id)
VALUES
('demo1.jpg',1),
('demo2.jpg',2),
('demo3.jpg',3),
('demo4.jpg',4),
('demo5.jpg',5);