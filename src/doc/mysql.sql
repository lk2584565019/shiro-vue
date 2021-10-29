/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : vueblog

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2021-10-22 10:38:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_code` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码账号',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `user_type` int(1) DEFAULT NULL COMMENT '用户类型',
  `salt` varchar(255) DEFAULT NULL COMMENT '随机盐',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('15', '13429921191', '566360fb2aa6b688e3887a84967b5f33', '吕康', '13429921191', '1', 'NF2#QvB&', null, '2021-10-21 16:44:04', '15', '2021-10-22 08:45:31', '0');
INSERT INTO `user` VALUES ('16', 'admin', '693e5ebdad950fd3acce5a59d4398fd5', 'admin', null, '0', 'IPw!k@dF', null, '2021-10-22 08:43:39', null, '2021-10-22 08:43:39', '0');
