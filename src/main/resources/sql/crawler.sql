-- 数据库采集表
DROP TABLE IF EXISTS `crawler`;
CREATE TABLE `Crawler` (
  `crawlerId` varchar(500) NOT NULL DEFAULT '' COMMENT 'ID',
  `crawlerName` varchar(500) NOT NULL DEFAULT '' COMMENT '名称',
  `groupId` varchar(500) NOT NULL DEFAULT '' COMMENT '组ID',
  `groupName` varchar(500) NOT NULL DEFAULT '' COMMENT '组名称', 
  `express` varchar(500) NULL DEFAULT '' COMMENT '表达式',
  `state` varchar(500) NULL DEFAULT '' COMMENT '状态',  
  `createTime` datetime NULL COMMENT '创建时间',
  `updateTime` datetime NULL COMMENT '更新时间',
  PRIMARY KEY (`crawlerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据库存储表';


-- 附件库
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `Attachment` (
  attachmentId  VARCHAR(50) NOT NULL COMMENT '主键',
  crawlerId     VARCHAR(50) NOT NULL COMMENT '爬虫 ID',
  datumId       VARCHAR(50) NOT NULL COMMENT '附件ID',
  name          VARCHAR(2000) NULL COMMENT '名称',
  isfail        CHAR(10) NULL COMMENT '是否失败',
  path          VARCHAR(2000) NULL COMMENT '路径',
  type          VARCHAR(20) NULL COMMENT '类型',
  txt           VARCHAR(100) NULL COMMENT 'txt',
  filesize      VARCHAR(500) NULL COMMENT '文件大小',
  createTime	datetime NULL COMMENT '创建时间',
  updateTime	datetime NULL COMMENT '更新时间',
PRIMARY KEY (`attachmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';


-- 元数据
DROP TABLE IF EXISTS `metadata`;
CREATE TABLE `metadata` (
  metadataId  VARCHAR(50) NOT NULL COMMENT '主键',  
  crawlerId  VARCHAR(50) NULL COMMENT '爬虫 ID',
  metadata     VARCHAR(50) NOT NULL COMMENT '爬虫数据',
  md      VARCHAR(2000) NULL COMMENT 'md5码',
  createTime	datetime NULL COMMENT '创建时间',
  updateTime	datetime NULL COMMENT '更新时间',
PRIMARY KEY (`metadataId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据表';

