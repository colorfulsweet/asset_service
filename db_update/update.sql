ALTER TABLE `assert`.`bgr` 
CHANGE COLUMN `password` `password` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL COMMENT '密码' ;

ALTER TABLE `assert`.`lzxx` 
ADD COLUMN `operate_id` VARCHAR(50) NULL COMMENT '操作ID - 对于一次操作(选中多个资产实施流转), 该ID相同' AFTER `UUID`;
