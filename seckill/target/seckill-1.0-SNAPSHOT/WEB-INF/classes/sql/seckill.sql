/*
 Navicat Premium Data Transfer

 Source Server         : 116.255.147.245
 Source Server Type    : MySQL
 Source Server Version : 50527
 Source Host           : 116.255.147.245:3306
 Source Schema         : dszt

 Target Server Type    : MySQL
 Target Server Version : 50527
 File Encoding         : 65001

 Date: 01/03/2019 15:46:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill`  (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品的id',
  `name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` datetime NOT NULL COMMENT '秒杀开始时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`) USING BTREE,
  INDEX `idx_start_time`(`start_time`) USING BTREE,
  INDEX `idx_end_time`(`end_time`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES (1, '100元秒杀红米note', 395, '2017-05-31 20:57:05', '2017-06-10 09:57:09', '2017-03-09 09:57:47');
INSERT INTO `seckill` VALUES (2, '200元秒杀小米4', 187, '2017-05-24 09:56:38', '2017-08-17 09:56:42', '2017-03-09 09:57:23');
INSERT INTO `seckill` VALUES (3, '300元秒杀ipad2', 300, '2017-03-08 09:56:07', '2017-03-13 09:56:12', '2017-03-09 09:56:50');
INSERT INTO `seckill` VALUES (4, '4000元秒杀苹果6', 95, '2017-03-23 09:55:32', '2017-04-01 09:55:47', '2017-03-09 09:56:30');
INSERT INTO `seckill` VALUES (5, '588秒杀化妆品', 999, '2017-03-29 11:01:14', '2017-04-01 11:01:18', '2017-03-20 11:02:17');

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed`  (
  `seckill_id` bigint(20) NOT NULL,
  `user_phone` bigint(20) NOT NULL COMMENT '无效0  成功1',
  `state` tinyint(4) NOT NULL DEFAULT -1,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`, `user_phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES (1, 18888888888, 0, '2017-03-27 16:11:45');
INSERT INTO `success_killed` VALUES (2, 12312312311, 0, '2017-05-28 23:45:53');
INSERT INTO `success_killed` VALUES (2, 12345678901, 0, '2017-05-27 16:23:32');
INSERT INTO `success_killed` VALUES (2, 13423454567, 0, '2017-05-27 15:42:24');
INSERT INTO `success_killed` VALUES (2, 13623865089, 0, '2017-05-27 14:24:41');
INSERT INTO `success_killed` VALUES (2, 89898989898, 0, '2017-05-28 23:56:03');
INSERT INTO `success_killed` VALUES (4, 18888888888, 0, '2017-03-27 16:11:32');
INSERT INTO `success_killed` VALUES (5, 12312341234, 0, '2017-03-31 16:43:17');

SET FOREIGN_KEY_CHECKS = 1;
