/*
 Navicat Premium Data Transfer

 Source Server         : TGW_CloudDB
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 120.79.74.174:3306
 Source Schema         : TGW_DB

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 13/05/2019 14:16:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tgw_businessman
-- ----------------------------
DROP TABLE IF EXISTS `tgw_businessman`;
CREATE TABLE `tgw_businessman`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0指不正常不可登录，1表示正常可登录',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_businessman_detail
-- ----------------------------
DROP TABLE IF EXISTS `tgw_businessman_detail`;
CREATE TABLE `tgw_businessman_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tgw_businessman_id` int(11) NOT NULL,
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商店名称',
  `shop_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺位置',
  `shop_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商店描述',
  `shop_time_open` time(6) NULL DEFAULT NULL COMMENT '开店时间',
  `shop_time_close` time(6) NULL DEFAULT NULL COMMENT '关店时间',
  `phone_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺电话，用户给商家打电话的那种电话',
  `contact_phone_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系店铺代表人的电话',
  `tgw_user_id` int(11) NULL DEFAULT NULL,
  `shop_notice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商店公告',
  `shop_settle_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '商家申请入驻的状态，0为申请未审核，1表示审核已通过可以入驻，2表示申请不通过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_first_category
-- ----------------------------
DROP TABLE IF EXISTS `tgw_first_category`;
CREATE TABLE `tgw_first_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级商品类别名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_goods
-- ----------------------------
DROP TABLE IF EXISTS `tgw_goods`;
CREATE TABLE `tgw_goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `oringinal_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '原价',
  `discount_price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '折后价',
  `tgw_businessman_id` int(11) NULL DEFAULT NULL COMMENT '商家id',
  `goods_category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品二级分类',
  `is_online` int(11) NULL DEFAULT NULL COMMENT '是否上架，0-否，1-上架',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2086125261 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_goods_comment
-- ----------------------------
DROP TABLE IF EXISTS `tgw_goods_comment`;
CREATE TABLE `tgw_goods_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tgw_goods_id` int(11) NOT NULL,
  `tgw_user_id` int(11) NOT NULL,
  `comment_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comment_time` datetime(0) NULL DEFAULT NULL,
  `comment_stars` tinyint(4) NULL DEFAULT NULL,
  `tgw_order_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `tgw_goods_detail`;
CREATE TABLE `tgw_goods_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tgw_goods_id` int(11) NULL DEFAULT NULL,
  `goods_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `term_of_validity` int(11) NULL DEFAULT NULL COMMENT '有效期单位为天',
  `creat_goods_time` datetime(0) NULL DEFAULT NULL COMMENT '商品创建时间',
  `goods_repertory` int(11) NULL DEFAULT NULL COMMENT '库存',
  `sales_volumn` int(11) NULL DEFAULT NULL COMMENT '销量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goodsid`(`tgw_goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_goods_image
-- ----------------------------
DROP TABLE IF EXISTS `tgw_goods_image`;
CREATE TABLE `tgw_goods_image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tgw_goods_id` int(11) NULL DEFAULT NULL,
  `is_main` int(11) NULL DEFAULT NULL COMMENT '是否封面',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goodsid`(`tgw_goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_manager
-- ----------------------------
DROP TABLE IF EXISTS `tgw_manager`;
CREATE TABLE `tgw_manager`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员自增id',
  `tgw_manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员名称',
  `manager_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登录密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_order
-- ----------------------------
DROP TABLE IF EXISTS `tgw_order`;
CREATE TABLE `tgw_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tgw_goods_id` int(11) NOT NULL COMMENT '虚拟外键，商品id',
  `tgw_user_id` int(11) NOT NULL COMMENT '虚拟外键，用户id',
  `order_create_time` datetime(0) NULL DEFAULT NULL,
  `sell_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0未付款，1已付款，2退款，3已使用，4已评论，5已过期',
  `count` int(11) NOT NULL DEFAULT 1 COMMENT '商品购买数量',
  `total` decimal(10, 2) NULL DEFAULT NULL,
  `order_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '订单状态，1正常，0不正常（比如用户已经删除）',
  `pay_serials_number` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付流水号',
  `tgw_businessman_id` int(11) NOT NULL COMMENT '虚拟外键，商家id',
  `unique_order_number` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '整个系统内唯一的订单编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 132 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_seckill
-- ----------------------------
DROP TABLE IF EXISTS `tgw_seckill`;
CREATE TABLE `tgw_seckill`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tgw_goods_id` int(11) NOT NULL COMMENT '外键，对应商品id',
  `seckill_repertory` int(11) NOT NULL COMMENT '库存量',
  `seckill_creattime` datetime(0) NOT NULL COMMENT '秒杀开始时间',
  `seckill_end` datetime(0) NOT NULL COMMENT '秒杀结束时间',
  `seckill_price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_second_category
-- ----------------------------
DROP TABLE IF EXISTS `tgw_second_category`;
CREATE TABLE `tgw_second_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二级级商品类别名',
  `first_category_id` int(11) NULL DEFAULT NULL COMMENT '一级商品类别id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_sms_verify
-- ----------------------------
DROP TABLE IF EXISTS `tgw_sms_verify`;
CREATE TABLE `tgw_sms_verify`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` tinyint(8) NOT NULL DEFAULT 0,
  `send_time` datetime(0) NOT NULL,
  `times` tinyint(8) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sms_veirfy_unique_key`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_user
-- ----------------------------
DROP TABLE IF EXISTS `tgw_user`;
CREATE TABLE `tgw_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0指不正常不可登录，1表示正常可登录',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tgw_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `tgw_user_detail`;
CREATE TABLE `tgw_user_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `tgw_user_id` int(11) NOT NULL COMMENT '逻辑外键，用户id',
  `sex` tinyint(255) NOT NULL DEFAULT 2 COMMENT '性别，0为女，1为男，2为保密',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `email` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `reg_time` datetime(0) NOT NULL COMMENT '注册时间',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '上次修改信息的时间',
  `user_image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像url',
  `nick_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
