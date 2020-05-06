/*
 Navicat Premium Data Transfer

 Source Server         : lulongjian
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 112.74.113.75:3306
 Source Schema         : fleamarket

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 06/05/2020 22:02:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名字',
  `balance` decimal(10, 2) NULL DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 1, '卢龙健', 0.00);
INSERT INTO `account` VALUES (2, 2, '测试用户1', 29.99);
INSERT INTO `account` VALUES (3, 3, '测试用户2', 0.00);
INSERT INTO `account` VALUES (4, 4, '测试用户3', 0.00);
INSERT INTO `account` VALUES (5, 5, '测试用户4', 0.00);
INSERT INTO `account` VALUES (6, 6, '测试用户5', 0.00);
INSERT INTO `account` VALUES (7, 7, '测试用户6', 0.00);
INSERT INTO `account` VALUES (8, 8, '测试用户7', 0.00);
INSERT INTO `account` VALUES (9, 9, '测试用户8', 0.00);
INSERT INTO `account` VALUES (10, 10, '测试用户9', 0.00);
INSERT INTO `account` VALUES (11, 11, '测试用户10', 0.00);
INSERT INTO `account` VALUES (17, 19, '张三', 0.00);
INSERT INTO `account` VALUES (18, 20, '张三', 9984.01);
INSERT INTO `account` VALUES (19, 21, '张三', 970.01);
INSERT INTO `account` VALUES (20, 22, '测试测', 0.00);

-- ----------------------------
-- Table structure for account_record
-- ----------------------------
DROP TABLE IF EXISTS `account_record`;
CREATE TABLE `account_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名字',
  `seller_id` int(0) NULL DEFAULT NULL COMMENT '卖家id（收款方）',
  `seller_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卖家姓名（收款人）',
  `category` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别（+-金额）0支出 1收入 3充 4提现',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `money_str` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account_record
-- ----------------------------
INSERT INTO `account_record` VALUES (88, 21, '张三', NULL, '系统', '3', 1000.00, '2020-05-05 16:07:51', NULL);
INSERT INTO `account_record` VALUES (89, 21, '张三', 2, '测试用户1', '0', 29.99, '2020-05-05 16:08:30', NULL);
INSERT INTO `account_record` VALUES (90, 2, '测试用户1', 21, '张三', '1', 29.99, '2020-05-05 16:08:57', NULL);

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收获人姓名',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人手机号码',
  `addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, 1, '卢龙健', '13978999127', '广西科技大学北区七栋303');
INSERT INTO `address` VALUES (13, 21, '卢龙健', '164657981', '成熟市场是');

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `product_id` int(0) NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (45, 21, 26);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `product_id` int(0) NULL DEFAULT NULL,
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 1, 1, '非常不错，值得购买', '2020-03-07 19:45:14');
INSERT INTO `message` VALUES (18, 21, 18, '这个产品不错', '2020-05-05 16:09:34');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `permission` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '商品状态 0已买出 1在售',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `sort` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类',
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `user_id` int(0) NOT NULL,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `old_price` decimal(10, 2) NULL DEFAULT NULL,
  `collect_num` int(0) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (17, '1', '0', 'ipone 5s', '手机数码', '旧款ipone，有需要可以拍下，谢谢', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w_AaAdnPuAABP_7KbnjA447.jpg', 1299.99, 1999.99, 1, '2020-05-05 13:12:26', '2020-05-05 15:17:44');
INSERT INTO `product` VALUES (18, '1', '1', '新款iPhone 11', '手机数码', '不错的手机', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w9cqAQVZXAABWauxM3WI300.jpg', 6999.99, 9999.99, 1, '2020-05-05 13:13:09', '2020-05-05 16:11:17');
INSERT INTO `product` VALUES (19, '1', '1', '小米11', '手机数码', '小米最新款，需要请拍下，谢谢', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w9gSADYhMAAByGdOIJfs903.jpg', 3799.99, 3999.99, 1, '2020-05-05 13:14:08', '2020-05-05 16:11:18');
INSERT INTO `product` VALUES (20, '1', '1', '情侣长袖T恤', '服装饰品', '没有穿过，码数不合适，有需求可以拍下', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w9iWAFZ11AABi3D6r8vA199.jpg', 109.99, 199.99, 0, '2020-05-05 13:15:27', '2020-05-05 16:11:20');
INSERT INTO `product` VALUES (21, '1', '1', '白色外套', '服装饰品', '刚入手一天，试穿了一下', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w9oSAf_I0AACby6Y2Fxs079.jpg', 149.99, 188.99, 0, '2020-05-05 13:16:20', '2020-05-05 13:18:58');
INSERT INTO `product` VALUES (22, '1', '1', '乌合之众', '学习用品', '很不错的一款书籍。适合阅读', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w9rWAQnn5AAB40-TYl0A251.jpg', 39.99, 59.99, 0, '2020-05-05 13:17:18', '2020-05-05 13:19:00');
INSERT INTO `product` VALUES (23, '1', '1', 'Java程序设计课本', '学习用品', '非常适合刚入门的小白', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w9uiAPV-nAACu0R88jEY410.jpg', 19.99, 59.99, 0, '2020-05-05 13:17:58', '2020-05-05 13:53:57');
INSERT INTO `product` VALUES (24, '1', '1', 'Java面向对象程序设计', '学习用品', '适合刚入行的同学阅读', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w9w-Aa3tpAAB_MgpzNZU598.jpg', 18.99, 49.99, 0, '2020-05-05 13:18:43', '2020-05-05 13:54:14');
INSERT INTO `product` VALUES (25, '1', '1', '山地自行车', '运动户外', '山地自行车，有点贵，价格可谈', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w916Adlu2AAC5KkSHUKw810.jpg', 6999.99, 9999.99, 0, '2020-05-05 13:20:02', '2020-05-05 13:22:23');
INSERT INTO `product` VALUES (26, '1', '0', '羽毛拍一对', '运动户外', '体育课上完了用不到了', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w95CAd93VAAC1LnkeiHY475.jpg', 29.99, 69.99, 1, '2020-05-05 13:20:47', '2020-05-05 16:08:30');
INSERT INTO `product` VALUES (27, '1', '1', '海飞丝', '生活用品', '多余的洗发水用不上', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w-b-AGXbkAAA6tYmaHt8493.jpg', 39.99, 99.99, 0, '2020-05-05 13:30:07', '2020-05-05 13:38:05');
INSERT INTO `product` VALUES (28, '1', '0', '便携式小风扇', '生活用品', '毕业出售，价格可谈', 2, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6w-fuAS3azAAA-JYr7GhA678.jpg', 15.99, 19.99, 1, '2020-05-05 13:30:59', '2020-05-05 15:58:15');
INSERT INTO `product` VALUES (29, '0', '1', '鞋子', '运动户外', '发布测试', 19, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6xGxOAAlkhAACGwddziWg528.jpg', 29.99, 45.99, 0, '2020-05-05 15:52:14', NULL);
INSERT INTO `product` VALUES (30, '1', '1', '鞋子', '运动户外', '不错得鞋子', 20, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6xHB-AOvPMAACGwddziWg442.jpg', 159.99, 199.99, 0, '2020-05-05 15:56:32', '2020-05-05 15:56:46');
INSERT INTO `product` VALUES (31, '1', '1', '小白鞋', '运动户外', '发布测试', 21, 'http://112.74.113.75/group1/M00/00/00/rBL4tV6xHoyANTO-AACGwddziWg848.jpg', 69.99, 99.99, 0, '2020-05-05 16:06:55', '2020-05-05 16:07:18');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `sno` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号',
  `role` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  `permission` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权（0代表未授权，1代表已授权）',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userName`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '1609601255', '管理员', '1', '卢龙健', 'rrdcvQPIgMnh+kJcWot9kvB5hHR4lklGTBVnyKmoWCB/GCxyxxMdwbTyjuncXi2Qg3gw7xx5zTnoZMj8oJtICOfapNRCZPCPeqCXnlNpGSGREQKOCcghCr8ZZgMtvM3QXAiOotixUQZYDxvITd7OQ3MUVawRhVCKRHC9mycy/7E=', '13978999127', '1219881823@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (2, '1609601200', '管理员', '1', '测试用户1', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567890@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (3, '1609601201', '管理员', '1', '测试用户2', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567891@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (4, '1609601202', '管理员', '1', '测试用户3', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567892@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (5, '1609601203', '管理员', '1', '测试用户4', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567893@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (6, '1609601204', '管理员', '1', '测试用户5', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567893@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (7, '1609601205', '管理员', '1', '测试用户6', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567894@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (8, '1609601206', '管理员', '1', '测试用户7', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567895@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (9, '1609601207', '管理员', '1', '测试用户8', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567896@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (10, '1609601208', '管理员', '1', '测试用户9', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567897@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (11, '1609601209', '管理员', '1', '测试用户10', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567898@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (12, '1609601210', '管理员', '1', '测试用户11', 'ePbg7Gmosh8JckYHnRghZAqmm+IrR/zkzubIpjMAS/ZjP9nzcgTSHaKDOs0W6sKamfFGZsxPK7iBxUUKHAlzU8Im3wK+nz0ETLIOFXUKg5O3RXVOfiTqDOgZ6hEKzqnXsSnMb1L5iZLUUjBdzJsQTuE780pt2r12KqZKwyVQb1k=', '13978999127', '1234567899@qq.com', '2020-02-08 22:06:42', '2020-03-27 12:04:29');
INSERT INTO `sys_user` VALUES (21, '123456789', '普通用户', '1', '张三', 'dZxOt+nCE/Tv/3Tn00AZ4Msdbj09nf7tmcoTiQHUzwDerPWD9+fz4MNziXEzhDw1aPfYvq1th2z8gwdTJac4N9OP0WltBmSMPc+U3xvMTVlDYBd1HseV7XZ1OBMIBFgZIKI9yU0UFqQzX6FzNrQG10q7YTmn5MdNJmR65sV2rTE=', '1234516511', '3082806752@qq.com', '2020-05-05 16:05:37', '2020-05-05 16:10:11');
INSERT INTO `sys_user` VALUES (22, '测试222', '普通用户', '1', '测试测', 'jQsYCnr6SJmWsDSczlH5x740y9dFvKsRQMpT1c8nCV9ng+eFwWJibe30gqs7fnb/ntVmtCggo7XMz4GR34CHC0MINJJDDEQAe4vUhiO8//6+R6GbWj5I/XFCSkWUb1RHNuPQ3Pm2Cemfem5sk/J4sU1rWSfIVfD5r480yi8Sd/I=', '132465489', '2131651@qq.com', '2020-05-05 16:10:37', '2020-05-05 16:10:46');

-- ----------------------------
-- Table structure for user_order
-- ----------------------------
DROP TABLE IF EXISTS `user_order`;
CREATE TABLE `user_order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` int(0) NULL DEFAULT NULL,
  `product_title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_price` decimal(10, 2) NULL DEFAULT NULL,
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `buyer_id` int(0) NULL DEFAULT NULL COMMENT '买家id',
  `buyer_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家名字',
  `seller_id` int(0) NULL DEFAULT NULL COMMENT '卖家id',
  `seller_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卖家名字',
  `status` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态, 1 已付款 0 已完成',
  `address_id` int(0) NULL DEFAULT NULL COMMENT '地址id',
  `account_record_id` int(0) NULL DEFAULT NULL COMMENT '付款记录id',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '添加时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_order
-- ----------------------------
INSERT INTO `user_order` VALUES (15, 26, '羽毛拍一对', 29.99, '20200505160830263021', 21, '张三', 2, '测试用户1', '3', 13, NULL, '2020-05-05 16:08:30', '2020-05-05 16:08:30');

SET FOREIGN_KEY_CHECKS = 1;
