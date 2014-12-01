INSERT INTO `integradb`.`clientes` (`id`, `apellidos`,`direccion`,`dni`,`nombre`,`password`) VALUES (1, 'Sabariego','Calle Palencia 10, 28030, Madrid','51458103H','Sofia','fsdfsdf');
INSERT INTO `integradb`.`clientes` (`id`, `apellidos`,`direccion`,`dni`,`nombre`,`password`) VALUES (2, 'Lopez','Calle Orense 29, 20020, Madrid','65387641J','Jesus','ewtrtret');
INSERT INTO `integradb`.`clientes` (`id`, `apellidos`,`direccion`,`dni`,`nombre`,`password`) VALUES (3, 'Cobos','Calle Prat 55, 28032, Madrid','34563458M','Amelia','bgfh446');
INSERT INTO `integradb`.`clientes` (`id`, `apellidos`,`direccion`,`dni`,`nombre`,`password`) VALUES (4, 'Gomez','Calle Albasanz 4, 28016, Madrid','54376528M','Pedro','sgsg545');

INSERT INTO `integradb`.`proveedor` (`id`,`cif`,`direccion`,`nombre`)VALUES(1,'12345678A','Calle Campezo, Poligono La Mercedes, 28031','PC Hardware');
INSERT INTO `integradb`.`proveedor` (`id`,`cif`,`direccion`,`nombre`)VALUES(2,'453125678A','Calle Raimundo Fdz Villaverde, 28020, Madrid','Conectrol');
INSERT INTO `integradb`.`proveedor` (`id`,`cif`,`direccion`,`nombre`)VALUES(3,'54354317A','Plaza de Republica Argentina, 28016','Alternate');
INSERT INTO `integradb`.`proveedor` (`id`,`cif`,`direccion`,`nombre`)VALUES(4,'12531678A','Av. del Mediterrano, 28022, Madrid','TodoSoftware');

INSERT INTO `integradb`.`producto` (`id`,`descripcion`,`nombre`,`precioBase`,`proveedor_id`) VALUES(1,'iMac 27 pulgadas','Mac',2029, 3);
INSERT INTO `integradb`.`producto` (`id`,`descripcion`,`nombre`,`precioBase`,`proveedor_id`) VALUES(2,'Microsoft Office 2013','Office',250.50, 4);
INSERT INTO `integradb`.`producto` (`id`,`descripcion`,`nombre`,`precioBase`,`proveedor_id`) VALUES(3,'Portatil i7, 8 GB','Portatil Samsung',1200, 1);
INSERT INTO `integradb`.`producto` (`id`,`descripcion`,`nombre`,`precioBase`,`proveedor_id`) VALUES(4,'Teclado inalambrico','Teclado Logitech',20.50, 3);
INSERT INTO `integradb`.`producto` (`id`,`descripcion`,`nombre`,`precioBase`,`proveedor_id`) VALUES (5,'Paquete de antivirus Panda','Antivirus Panda', 79.99, 4);
INSERT INTO `integradb`.`producto` (`id`,`descripcion`,`nombre`,`precioBase`,`proveedor_id`) VALUES (6,'Edición estudiante Autocad','Autocad 10', 580, 4);
INSERT INTO `integradb`.`producto` (`id`,`descripcion`,`nombre`,`precioBase`,`proveedor_id`) VALUES (7,'Adobe Photoshop edicion Ligthroom','Photoshop', 990.30, 4);
INSERT INTO `integradb`.`producto` (`id`,`descripcion`,`nombre`,`precioBase`,`proveedor_id`) VALUES (8,'Pantalla LCD con Full-HD, 1920 x 1080','Pantalla 22 pulgadas', 210.40, 2);

INSERT INTO `integradb`.`pedido` (`id`,`fechaPedido`,`cliente_id`) VALUES (1,'2014-10-01 12:23:00',1);
INSERT INTO `integradb`.`pedido` (`id`,`fechaPedido`,`cliente_id`) VALUES (2,'2014-11-06 17:45:00',3);
INSERT INTO `integradb`.`pedido` (`id`,`fechaPedido`,`cliente_id`) VALUES (3,'2014-11-28 09:13:00',4);

INSERT INTO `integradb`.`pedido_producto` (`Pedido_id`, `productos_id`) VALUES (1, 1);
INSERT INTO `integradb`.`pedido_producto` (`Pedido_id`, `productos_id`) VALUES (2, 1);
INSERT INTO `integradb`.`pedido_producto` (`Pedido_id`, `productos_id`) VALUES (2, 7);
INSERT INTO `integradb`.`pedido_producto` (`Pedido_id`, `productos_id`) VALUES (3, 2);
INSERT INTO `integradb`.`pedido_producto` (`Pedido_id`, `productos_id`) VALUES (3, 3);






