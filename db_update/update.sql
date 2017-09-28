ALTER TABLE `pams`.`bgr` 
CHANGE COLUMN `password` `password` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL COMMENT '密码' ;

ALTER TABLE `pams`.`lzxx` 
ADD COLUMN `operate_id` VARCHAR(50) NULL COMMENT '操作ID - 对于一次操作(选中多个资产实施流转), 该ID相同' AFTER `uuid`;

ALTER TABLE `pams`.`lzxx` 
CHANGE COLUMN `fk_zhaopian_pzzpurl` `fk_zhaopian_pzzpurl` VARCHAR(100) NULL DEFAULT NULL COMMENT '凭证照片附件' ;

ALTER TABLE `pams`.`ryjsgx` 
ADD COLUMN `uuid` VARCHAR(32) NOT NULL FIRST,
ADD PRIMARY KEY (`uuid`);