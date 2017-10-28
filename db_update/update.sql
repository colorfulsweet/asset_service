ALTER TABLE `pams`.`bgr` 
CHANGE COLUMN `password` `password` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL COMMENT '密码' ;

ALTER TABLE `pams`.`lzxx` 
ADD COLUMN `operate_id` VARCHAR(50) NULL COMMENT '操作ID - 对于一次操作(选中多个资产实施流转), 该ID相同' AFTER `uuid`;

ALTER TABLE `pams`.`lzxx` 
CHANGE COLUMN `fk_zhaopian_pzzpurl` `fk_zhaopian_pzzpurl` VARCHAR(100) NULL DEFAULT NULL COMMENT '凭证照片附件' ;

ALTER TABLE `pams`.`ryjsgx` 
ADD COLUMN `uuid` VARCHAR(32) NOT NULL FIRST,
ADD PRIMARY KEY (`uuid`);

ALTER TABLE `pams`.`lzxx` 
ADD COLUMN `status` INT NULL COMMENT '流转执行状态(0 未完成  1 已完成)' AFTER `lzbz`;

ALTER TABLE `zichan`
	ADD INDEX `Index 2` (`lbie`),
	ADD INDEX `Index 3` (`mingch`);

ALTER TABLE `lzxx`
ADD COLUMN `fk_zichan_uuid` VARCHAR(32) NULL DEFAULT NULL COMMENT '资产表uuid' AFTER `fk_zichan_zcid`;

ALTER TABLE `lzxx`
ADD COLUMN `lzsl` DECIMAL(10,0) NULL DEFAULT NULL COMMENT '流转数量' AFTER `fk_zhaopian_sbzpid`;

ALTER TABLE `zichan`
	ADD COLUMN `status` VARCHAR(32) NULL DEFAULT NULL COMMENT '状态(正常 损坏 丢失 其他)' AFTER `zczt`,
	ADD COLUMN `pdzt` VARCHAR(32) NULL DEFAULT NULL COMMENT '盘点状态' AFTER `status`;
	
ALTER TABLE `pdxx`
	ADD COLUMN `fk_zichan_uuid` VARCHAR(32) NULL DEFAULT NULL COMMENT '资产uuid' AFTER `uuid`;