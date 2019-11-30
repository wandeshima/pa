/*
Navicat MySQL Data Transfer

Source Server         : local_3306_mysql-5.6.10
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : pa

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2019-11-30 19:19:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_poem
-- ----------------------------
DROP TABLE IF EXISTS `data_poem`;
CREATE TABLE `data_poem` (
  `po_id` varchar(32) NOT NULL,
  `po_title` varchar(50) NOT NULL,
  `po_subtitle` varchar(50) DEFAULT NULL,
  `po_preface` varchar(255) DEFAULT NULL,
  `po_author` varchar(50) NOT NULL,
  `po_content` text NOT NULL,
  `po_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`po_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_url
-- ----------------------------
DROP TABLE IF EXISTS `data_url`;
CREATE TABLE `data_url` (
  `id` varchar(32) NOT NULL,
  `func` varchar(255) NOT NULL,
  `url` varchar(1000) NOT NULL,
  `done` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
