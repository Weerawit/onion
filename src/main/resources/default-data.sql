
INSERT INTO `role` VALUES(1, 'Administrator role (can edit Users)', 'ROLE_ADMIN');
INSERT INTO `role` VALUES(2, 'Default role for all Users', 'ROLE_USER');
INSERT INTO `role` VALUES(3, 'Manager', 'ROLE_MANAGER');
INSERT INTO `role` VALUES(4, 'Sale team', 'ROLE_SALE');
INSERT INTO `role` VALUES(5, 'Production team', 'ROLE_PRODUCTION');
INSERT INTO `role` VALUES(6, 'Catalog team', 'ROLE_CATALOG');
INSERT INTO `role` VALUES(7, 'Inventory team', 'ROLE_INVENTORY');
INSERT INTO `role` VALUES(8, 'Report viewer', 'ROLE_REPORT');


INSERT INTO `app_user` VALUES(1, '\0', '\0', '', 'Denver', 'US', '80210', 'CO', '\0', 'user@test.com', '', 'User', 'User', 'b6b1f4781776979c0775c71ebdd8bdc084aac5fe', 'The same as your username.', '', 'user', 1, 'http://test.com');
INSERT INTO `app_user` VALUES(2, '\0', '\0', '', 'Denver', 'US', '80210', 'CO', '\0', 'admin@test.com', '', 'Admin', 'Admin', 'a40546cc4fd6a12572828bb803380888ad1bfdab', 'The same as your username.', '', 'admin', 1, 'http://test.com');

INSERT INTO `user_role` VALUES(1, 2);
INSERT INTO `user_role` VALUES(2, 1);
INSERT INTO `user_role` VALUES(2, 3);
INSERT INTO `user_role` VALUES(2, 4);
INSERT INTO `user_role` VALUES(2, 5);
INSERT INTO `user_role` VALUES(2, 6);
INSERT INTO `user_role` VALUES(2, 7);
INSERT INTO `user_role` VALUES(2, 8);

INSERT INTO `document_seq` VALUES(1, 'InvGoodsReceipt', 0, '2013-09-09 16:26:06');
INSERT INTO `document_seq` VALUES(2, 'InvGoodsMovement', 0, '2013-09-09 16:25:46');
INSERT INTO `document_seq` VALUES(3, 'SaleOrder', 0, '2013-09-07 19:15:42');
INSERT INTO `document_seq` VALUES(4, 'SaleReceipt', 0, '2013-09-06 14:43:33');
INSERT INTO `document_seq` VALUES(5, 'JobOrder', 0, '2013-09-07 23:58:07');

INSERT INTO `inv_item_group` VALUES(1, 'CAT', 'Catalog', '2013-08-07 14:43:24', 'admin', NULL, NULL);
