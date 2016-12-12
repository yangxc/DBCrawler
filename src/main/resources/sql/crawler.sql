-- 数据库采集表
DROP TABLE IF EXISTS `Crawler`;
CREATE TABLE `Crawler` (
  `crawlerId` varchar(50) NOT NULL DEFAULT '' COMMENT 'ID',
  `crawlerName` varchar(500) NOT NULL DEFAULT '' COMMENT '名称',
  `groupId` varchar(500) NOT NULL DEFAULT '' COMMENT '组ID',
  `groupName` varchar(500) NOT NULL DEFAULT '' COMMENT '组名称', 
  `express` varchar(500) NULL DEFAULT '' COMMENT '表达式',
  `state` varchar(500) NULL DEFAULT '' COMMENT '状态',  
  `createTime` datetime NULL COMMENT '创建时间',
  `updateTime` datetime NULL COMMENT '更新时间',
  PRIMARY KEY (`crawlerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据库存储表';