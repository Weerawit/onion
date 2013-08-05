SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `onion` DEFAULT CHARACTER SET utf8 ;
USE `onion` ;

-- -----------------------------------------------------
-- Table `onion`.`document_number`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`document_number` ;

CREATE  TABLE IF NOT EXISTS `onion`.`document_number` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `type` VARCHAR(50) NOT NULL ,
  `current_val` BIGINT NULL DEFAULT 1 ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `type_UNIQUE` (`type` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`app_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`app_user` ;

CREATE  TABLE IF NOT EXISTS `onion`.`app_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `account_expired` BIT(1) NOT NULL ,
  `account_locked` BIT(1) NOT NULL ,
  `address` VARCHAR(150) NULL DEFAULT NULL ,
  `city` VARCHAR(50) NULL DEFAULT NULL ,
  `country` VARCHAR(100) NULL DEFAULT NULL ,
  `postal_code` VARCHAR(15) NULL DEFAULT NULL ,
  `province` VARCHAR(100) NULL DEFAULT NULL ,
  `credentials_expired` BIT(1) NOT NULL ,
  `email` VARCHAR(255) NOT NULL ,
  `account_enabled` BIT(1) NULL DEFAULT NULL ,
  `first_name` VARCHAR(50) NOT NULL ,
  `last_name` VARCHAR(50) NOT NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `password_hint` VARCHAR(255) NULL DEFAULT NULL ,
  `phone_number` VARCHAR(255) NULL DEFAULT NULL ,
  `username` VARCHAR(50) NOT NULL ,
  `version` INT(11) NULL DEFAULT NULL ,
  `website` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `email` (`email` ASC) ,
  UNIQUE INDEX `username` (`username` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `onion`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`role` ;

CREATE  TABLE IF NOT EXISTS `onion`.`role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `description` VARCHAR(64) NULL DEFAULT NULL ,
  `name` VARCHAR(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `onion`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`user_role` ;

CREATE  TABLE IF NOT EXISTS `onion`.`user_role` (
  `user_id` BIGINT(20) NOT NULL ,
  `role_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`user_id`, `role_id`) ,
  INDEX `FK143BF46A48626E7A` (`user_id` ASC) ,
  CONSTRAINT `fk_user_role_app_user1`
    FOREIGN KEY (`user_id` )
    REFERENCES `onion`.`app_user` (`id` ),
  CONSTRAINT `fk_user_role_role1`
    FOREIGN KEY (`role_id` )
    REFERENCES `onion`.`role` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `onion`.`inv_item_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`inv_item_group` ;

CREATE  TABLE IF NOT EXISTS `onion`.`inv_item_group` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ชนิดของสินค้า\n' ,
  `code` VARCHAR(10) NOT NULL ,
  `name` VARCHAR(50) NOT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`inv_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`inv_item` ;

CREATE  TABLE IF NOT EXISTS `onion`.`inv_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'สินค้า' ,
  `code` VARCHAR(10) NOT NULL ,
  `name` VARCHAR(50) NULL DEFAULT NULL ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  `inv_item_group_id` BIGINT NOT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_inv_item_inv_item_group1`
    FOREIGN KEY (`inv_item_group_id` )
    REFERENCES `onion`.`inv_item_group` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`supplier`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`supplier` ;

CREATE  TABLE IF NOT EXISTS `onion`.`supplier` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `code` VARCHAR(10) NOT NULL ,
  `name` VARCHAR(50) NOT NULL ,
  `address` VARCHAR(255) NULL DEFAULT NULL ,
  `contact_name` VARCHAR(50) NULL DEFAULT NULL ,
  `contact_tel` VARCHAR(50) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`inv_goods_receipt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`inv_goods_receipt` ;

CREATE  TABLE IF NOT EXISTS `onion`.`inv_goods_receipt` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ใบรับของสินค้า\n' ,
  `running_no` VARCHAR(20) NULL ,
  `receipt_date` DATETIME NULL DEFAULT NULL ,
  `total_cost` DECIMAL(10,2) NULL DEFAULT NULL COMMENT 'ราคารวม ทั้งหมด\n' ,
  `memo` VARCHAR(255) NULL DEFAULT NULL ,
  `supplier_id` BIGINT NOT NULL COMMENT 'ชื่อบ.ที่ขาย สินค้าให้เรา' ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_inv_good_receipt_supplier1`
    FOREIGN KEY (`supplier_id` )
    REFERENCES `onion`.`supplier` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`inv_goods_receipt_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`inv_goods_receipt_item` ;

CREATE  TABLE IF NOT EXISTS `onion`.`inv_goods_receipt_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'รายการสินค้า ในใบรับของ\n' ,
  `qty` DECIMAL(10,2) NULL DEFAULT NULL ,
  `unit_price` DECIMAL(10,2) NULL DEFAULT NULL ,
  `unit_type` VARCHAR(3) NULL DEFAULT NULL ,
  `memo` VARCHAR(255) NULL DEFAULT NULL ,
  `inv_goods_receipt_id` BIGINT NOT NULL ,
  `inv_item_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_inv_good_receipt_item_inv_good_receipt1`
    FOREIGN KEY (`inv_goods_receipt_id` )
    REFERENCES `onion`.`inv_goods_receipt` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inv_good_receipt_item_inv_item1`
    FOREIGN KEY (`inv_item_id` )
    REFERENCES `onion`.`inv_item` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`inv_goods_movement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`inv_goods_movement` ;

CREATE  TABLE IF NOT EXISTS `onion`.`inv_goods_movement` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `running_no` VARCHAR(20) NULL DEFAULT NULL ,
  `movement_date` DATETIME NULL DEFAULT NULL ,
  `movement_type` VARCHAR(3) NULL DEFAULT NULL COMMENT '100 เบิกของ' ,
  `owner` VARCHAR(50) NULL DEFAULT NULL COMMENT 'ผู้เบิก' ,
  `memo` VARCHAR(255) NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`inv_goods_movement_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`inv_goods_movement_item` ;

CREATE  TABLE IF NOT EXISTS `onion`.`inv_goods_movement_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'การเบิกสินค้า ออกจาก stock\n' ,
  `inv_item_id` BIGINT NOT NULL ,
  `qty` DECIMAL(10,2) NULL DEFAULT NULL ,
  `memo` VARCHAR(50) NULL DEFAULT NULL COMMENT 'เอกสารไว้อ้างอิง' ,
  `inv_goods_movement_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_inv_good_movement_inv_item1`
    FOREIGN KEY (`inv_item_id` )
    REFERENCES `onion`.`inv_item` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inv_good_movement_item_inv_good_movement1`
    FOREIGN KEY (`inv_goods_movement_id` )
    REFERENCES `onion`.`inv_goods_movement` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`employee` ;

CREATE  TABLE IF NOT EXISTS `onion`.`employee` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `first_name` VARCHAR(50) NULL DEFAULT NULL ,
  `last_name` VARCHAR(50) NULL DEFAULT NULL ,
  `nick_name` VARCHAR(50) NULL DEFAULT NULL ,
  `age` INT NULL DEFAULT NULL ,
  `id_card_no` VARCHAR(50) NULL DEFAULT NULL ,
  `address` VARCHAR(255) NULL DEFAULT NULL ,
  `wage` DECIMAL(10,2) NULL DEFAULT NULL ,
  `memo` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`catalog_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`catalog_type` ;

CREATE  TABLE IF NOT EXISTS `onion`.`catalog_type` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `code` VARCHAR(10) NOT NULL ,
  `name` VARCHAR(50) NOT NULL ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`catalog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`catalog` ;

CREATE  TABLE IF NOT EXISTS `onion`.`catalog` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `catalog_type_id` BIGINT NOT NULL ,
  `code` VARCHAR(10) NULL DEFAULT NULL ,
  `name` VARCHAR(50) NULL DEFAULT NULL ,
  `final_price` DECIMAL(10,2) NULL DEFAULT NULL COMMENT 'ราคาขายจริง' ,
  `est_price` DECIMAL(10,2) NULL DEFAULT NULL COMMENT 'ราคาประเมิณต้นทุน' ,
  `img` LONGBLOB NULL DEFAULT NULL COMMENT 'images' ,
  `inv_item_id` BIGINT NOT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_catalog_catalog_type1`
    FOREIGN KEY (`catalog_type_id` )
    REFERENCES `onion`.`catalog_type` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_catalog_inv_item1`
    FOREIGN KEY (`inv_item_id` )
    REFERENCES `onion`.`inv_item` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`catalog_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`catalog_item` ;

CREATE  TABLE IF NOT EXISTS `onion`.`catalog_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `catalog_id` BIGINT NOT NULL ,
  `inv_item_id` BIGINT NOT NULL COMMENT 'ถ้ามี สินค้าใน ระบบก็ link ให้ด้วย \nเพื่อจะได้ บอกได้ว่า \nวัตถุดิบนั้น มีในระบบหรือเปล่า' ,
  `name` VARCHAR(50) NULL DEFAULT NULL ,
  `qty` DECIMAL(10,2) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_catalog_item_catalog1`
    FOREIGN KEY (`catalog_id` )
    REFERENCES `onion`.`catalog` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_catalog_item_inv_item1`
    FOREIGN KEY (`inv_item_id` )
    REFERENCES `onion`.`inv_item` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`customer` ;

CREATE  TABLE IF NOT EXISTS `onion`.`customer` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '	' ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `customer_type` VARCHAR(3) NULL DEFAULT NULL COMMENT '1-persernal\n2-company\n' ,
  `shiping_address` VARCHAR(255) NULL DEFAULT NULL ,
  `billing_address` VARCHAR(255) NULL DEFAULT NULL ,
  `contact_name` VARCHAR(50) NULL DEFAULT NULL ,
  `contact_tel` VARCHAR(50) NULL DEFAULT NULL ,
  `memo` VARCHAR(255) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`sale_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`sale_order` ;

CREATE  TABLE IF NOT EXISTS `onion`.`sale_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `sale_order_no` VARCHAR(45) NULL DEFAULT NULL COMMENT 'running no' ,
  `payment_type` VARCHAR(3) NULL DEFAULT NULL COMMENT '1-credit\n2-cash' ,
  `delivery_date` DATETIME NULL DEFAULT NULL ,
  `total_price` DECIMAL(10,2) NULL DEFAULT NULL ,
  `customer_id` BIGINT NOT NULL ,
  `status` VARCHAR(3) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_sale_order_customer1`
    FOREIGN KEY (`customer_id` )
    REFERENCES `onion`.`customer` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`sale_order_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`sale_order_item` ;

CREATE  TABLE IF NOT EXISTS `onion`.`sale_order_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `sale_order_id` BIGINT NOT NULL ,
  `catalog_id` BIGINT NOT NULL ,
  `qty` DECIMAL(10,2) NULL DEFAULT NULL ,
  `price_per_unit` DECIMAL(10,2) NULL DEFAULT NULL ,
  `price` DECIMAL(10,2) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_sale_order_item_sale_order1`
    FOREIGN KEY (`sale_order_id` )
    REFERENCES `onion`.`sale_order` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sale_order_item_catalog1`
    FOREIGN KEY (`catalog_id` )
    REFERENCES `onion`.`catalog` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`sale_receipt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`sale_receipt` ;

CREATE  TABLE IF NOT EXISTS `onion`.`sale_receipt` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `receipt_no` VARCHAR(20) NULL DEFAULT NULL COMMENT 'running no' ,
  `sale_order_id` BIGINT NOT NULL ,
  `receipt_date` DATETIME NULL DEFAULT NULL ,
  `receipt_type` VARCHAR(3) NULL DEFAULT NULL COMMENT '1- cash\n2- cheque' ,
  `receipt_amount` DECIMAL(10,2) NULL DEFAULT NULL ,
  `cheque_no` VARCHAR(50) NULL DEFAULT NULL COMMENT 'for receipt_type = 2 (cheque)' ,
  `cheque_bank` VARCHAR(50) NULL DEFAULT NULL COMMENT 'for receipt_type = 2 (cheque)' ,
  `cheque_date` DATETIME NULL DEFAULT NULL COMMENT 'for receipt_type = 2 (cheque)' ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `create_user` VARCHAR(50) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `update_user` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_sale_receipt_sale_order1`
    FOREIGN KEY (`sale_order_id` )
    REFERENCES `onion`.`sale_order` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`job_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`job_order` ;

CREATE  TABLE IF NOT EXISTS `onion`.`job_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `sale_order_item_id` BIGINT NOT NULL ,
  `status` VARCHAR(3) NULL DEFAULT NULL COMMENT '- มาใหม่\r- มอบหมาย\nและกำลังผลิต\r- ผลิตเสร็จแล้ว\r- ส่งมอบ' ,
  `start_date` DATETIME NULL DEFAULT NULL ,
  `end_date` DATETIME NULL DEFAULT NULL ,
  `actual_end_date` DATETIME NULL DEFAULT NULL ,
  `employee_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_job_order_sale_order_item1`
    FOREIGN KEY (`sale_order_item_id` )
    REFERENCES `onion`.`sale_order_item` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_job_order_employee1`
    FOREIGN KEY (`employee_id` )
    REFERENCES `onion`.`employee` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`inv_stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`inv_stock` ;

CREATE  TABLE IF NOT EXISTS `onion`.`inv_stock` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `inv_item_id` BIGINT NOT NULL ,
  `qty` DECIMAL(10,2) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_inv_stock_inv_item1`
    FOREIGN KEY (`inv_item_id` )
    REFERENCES `onion`.`inv_item` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onion`.`inv_item_level`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onion`.`inv_item_level` ;

CREATE  TABLE IF NOT EXISTS `onion`.`inv_item_level` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `transaction_date` DATETIME NOT NULL ,
  `qty_in_stock` DECIMAL(10,2) NULL DEFAULT NULL COMMENT 'มากกว่า0 เป็นการนำเข้า, น้อยกว่า 0 เป็นการเอาออก' ,
  `ref_document` VARCHAR(50) NULL DEFAULT NULL COMMENT 'key ที่อ้างอิง จาก 2 table\n inv_good_receipt_item, inv_good_movement_item' ,
  `ref_type` VARCHAR(3) NULL DEFAULT NULL COMMENT '1=goodReceipt(in)\n2=movement (out)' ,
  `inv_item_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_inv_item_level_inv_item1`
    FOREIGN KEY (`inv_item_id` )
    REFERENCES `onion`.`inv_item` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'การเคลื่อนไหว สินค้าในคลัง';

USE `onion` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
