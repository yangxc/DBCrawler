

DROP DATABASE IF EXISTS `mysql`;
CREATE DATABASE `mysql` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mysql`;



DROP TABLE IF EXISTS `crawler`;
CREATE TABLE `crawler` (
  `crawlerId` varchar(50) NOT NULL DEFAULT '' COMMENT 'ID',
  `crawlerName` varchar(500) NOT NULL DEFAULT '' COMMENT '名称',
  `groupId` varchar(500) NOT NULL DEFAULT '' COMMENT '组ID',
  PRIMARY KEY (`crawlerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据库存储表';



INSERT INTO `crawler` VALUES ('1001','info','101');
