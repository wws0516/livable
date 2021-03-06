--liquibase formatted sql
--changeset zhang:change house

/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : livable

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 02/07/2019 21:09:35
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                       `user_id` varchar(255) NOT NULL  COMMENT '用户ID',
                       `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
                       `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
                       `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
                       `password` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
                       PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for user_opinion
-- ----------------------------
DROP TABLE IF EXISTS `user_opinion`;
CREATE TABLE `user_opinion`  (
                               `opinion_id` int(11) NOT NULL COMMENT '意见ID',
                               `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
                               `opinion` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '意见',
                               `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
                               `star` int(11) NULL DEFAULT NULL COMMENT '星数',
                               PRIMARY KEY (`opinion_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for personal_information
-- ----------------------------
DROP TABLE IF EXISTS `personal_information`;
CREATE TABLE `personal_information`  (
                                       `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                                       `head_portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像路径',
                                       `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
                                       `job` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
                                       PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for opinion_count
-- ----------------------------
DROP TABLE IF EXISTS `opinion_count`;
CREATE TABLE `opinion_count`  (
                                `opinion_id` int(11) NOT NULL COMMENT '意见ID',
                                `count` int(11) NULL DEFAULT NULL COMMENT '点赞次数',
                                PRIMARY KEY (`opinion_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for looking
-- ----------------------------
DROP TABLE IF EXISTS `looking`;
CREATE TABLE `looking`  (
                          `user_id` int(11) NOT NULL COMMENT '用户ID',
                          `house_id` int(11) NOT NULL COMMENT '房源ID',
                          PRIMARY KEY (`user_id`, `house_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for looked
-- ----------------------------
DROP TABLE IF EXISTS `looked`;
CREATE TABLE `looked`  (
                         `user_id` int(11) NOT NULL COMMENT '用户ID',
                         `house_id` int(11) NOT NULL COMMENT '房源ID',
                         PRIMARY KEY (`user_id`, `house_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for landlord_information
-- ----------------------------
DROP TABLE IF EXISTS `landlord_information`;
CREATE TABLE `landlord_information`  (
                                       `user_id` int(11) NOT NULL COMMENT '房东ID',
                                       `id_number` int(18) NULL DEFAULT NULL COMMENT '身份证号',
                                       `id_card_picture_f` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证正面',
                                       `id_card_picture_r` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证反面',
                                       `alipay_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝账户名',
                                       `alipay_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝账号',
                                       `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
                                       PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for landlord_hourse_relation
-- ----------------------------
DROP TABLE IF EXISTS `landlord_house_relation`;
CREATE TABLE `landlord_house_relation`  (
                                          `user_id` int(11) NOT NULL COMMENT '房东ID',
                                          `house_id` int(11) NOT NULL COMMENT '房子ID',
                                          PRIMARY KEY (`user_id`, `house_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
                        `house_id` varchar(255) NOT NULL COMMENT '房子ID',
                        `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
                        `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
                        `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
                        `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
                        `house_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房型',
                        `rent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租金',
                        `rent_way` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方式',
                        `elevator` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电梯有无',
                        `toward` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '朝向',
                        `carport` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车位有无',
                        `energy_charge` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电费',
                        `water_charge` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '水费',
                        `feature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特色',
                        `acreage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '面积',
                        `layout` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '布局',
                        `allocation` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '房屋配置',
                        `introduction` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '介绍',
                        `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
                        `house_proprietary_certificate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房产证',
                        `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
                        PRIMARY KEY (`house_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  Table structure for `address`
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
                                 `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                 `belong_to` varchar(32) NOT NULL DEFAULT '0' COMMENT '上一级行政单位名',
                                 `name` varchar(32) NOT NULL COMMENT '行政单位中文名',
                                 `shortname` varchar(32) NOT NULL COMMENT '简称',
                                 `level` varchar(1) NOT NULL COMMENT '行政级别 市-city 地区-region',
                                 `baidu_map_lng` VARCHAR(20) NOT NULL COMMENT '百度地图经度',
                                 `baidu_map_lat` VARCHAR(20) NOT NULL COMMENT '百度地图纬度',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;





