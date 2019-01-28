create database miaosha;

-- 记录用户个人信息
CREATE TABLE  user
(
	user_id				int(18)			NOT NULL	AUTO_INCREMENT,
	name			varchar(30)		NOT NULL 	COMMENT'用户名',
	password			varchar(128)	NOT NULL	COMMENT'用户密码',
	salt			varchar(30)		NOT NULL	COMMENT'加密用',
	sex			int(2)		 	 	COMMENT'性别：未知0，男1，女2',
	head_url			varchar(128)			 COMMENT'头像路径',

	phone  varchar(13)   COMMENT'手机号',

	email varchar(30)   COMMENT'邮箱',

  create_date timestamp NOT NULL,

	PRIMARY KEY (user_id)
)ENGINE=InnoDB;

-- 记录商品信息
CREATE TABLE  goods
(
	goods_id				int(18)			NOT NULL	AUTO_INCREMENT,
	goods_name			varchar(64)		NOT NULL 	COMMENT'商品名称',
	goods_title			varchar(128)	NOT NULL	COMMENT'商品标题',
	goods_img        varchar(128)	NOT NULL	COMMENT'商品图片路径',
	goods_detail        varchar(128)	NOT NULL	COMMENT'商品详情介绍',

  goods_price        decimal (10,2)	NOT NULL	COMMENT'价格',
  goods_stock				int(18)			NOT NULL	COMMENT'库存',

	PRIMARY KEY (goods_id)
)ENGINE=InnoDB;

insert into goods(goods_name,goods_title,goods_img,goods_detail,goods_price,goods_stock) value ('Apple iPhone XR ','Apple iPhone XR (A2108) 64GB 黑色 移动联通电信4G手机 双卡双待','http://47.106.215.221/images/miaosha/ipx.jpg','分辨率：1792*828、
后置摄像头：1200万像素、前置摄像头：700万像素',5699.00,50);

-- 记录参与秒杀的商品信息
CREATE TABLE  miaosha_goods
(
  miaosha_id              int(18)			NOT NULL	AUTO_INCREMENT,
	goods_id				int(18)			NOT NULL,
	miaosha_price			decimal (10,2)	NOT NULL 	COMMENT'价格',

  start_date      timestamp NOT NULL 	COMMENT'开始时间',
  end_date      timestamp NOT NULL 	COMMENT'结束时间',
  stock_count				int(18)			NOT NULL	COMMENT'库存',

	PRIMARY KEY (miaosha_id)
)ENGINE=InnoDB;

-- 记录订单信息
CREATE TABLE  order_info
(
  order_id              int(18)			NOT NULL	AUTO_INCREMENT,
	goods_id				int(18)			NOT NULL,
	user_id				int(18)			NOT NULL,
	goods_name			varchar(64)		NOT NULL 	COMMENT'商品名称',
	goods_price			decimal (10,2)	NOT NULL 	COMMENT'价格',
  address   varchar(124)		NOT NULL 	COMMENT'收货地址',
  goods_count int (19) NOT NULL 	COMMENT'商品数量',
  status int(3) default '0' COMMENT'订单状态，0新建未支付，1已支付，2已发货，3已退款，4已收货，5已完成',
  create_date timestamp NOT NULL 	COMMENT'创建时间',
  pay_date timestamp  	COMMENT'支付时间',
	PRIMARY KEY (order_id)
)ENGINE=InnoDB;

-- 记录秒杀订单信息
CREATE TABLE  miaosha_order
(
  id              int(18)			NOT NULL	AUTO_INCREMENT,
	goods_id				int(18)			NOT NULL,
	miaosha_id				int(18)			NOT NULL,
	order_id        int(18)			NOT NULL,
	user_id        int(18)			NOT NULL,

	PRIMARY KEY (id)
)ENGINE=InnoDB;


