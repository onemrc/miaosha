# 秒杀系统 (MiaoSha) - Java高并发项目

## 项目概述

这是一个基于Spring Boot的秒杀系统项目，专注于高并发场景下的性能优化。项目实现了用户注册登录、商品展示、秒杀下单等核心功能，并通过多种技术手段解决高并发访问问题。

## 技术栈

### 后端技术
- **框架**: Spring Boot 2.0.6
- **数据库**: MySQL 5.7
- **ORM**: MyBatis 1.3.2
- **连接池**: Druid 1.1.11
- **缓存**: Redis (Jedis 2.9.0)
- **模板引擎**: Thymeleaf
- **工具库**: Lombok, FastJSON, Commons Codec

### 前端技术
- **UI框架**: Bootstrap 3.x
- **JavaScript**: jQuery + jQuery Validation
- **弹层组件**: Layer
- **模板引擎**: Thymeleaf

## 项目结构

```
src/main/java/com/example/demo/
├── aspect/           # AOP切面配置
├── controller/       # 控制器层
│   ├── HomeController.java      # 首页、登录注册
│   ├── GoodsController.java     # 商品相关
│   └── MiaoShaController.java   # 秒杀相关
├── dao/              # 数据访问层
├── domain/           # 实体类
│   ├── User.java              # 用户实体
│   ├── Goods.java             # 商品实体
│   ├── MiaoShaGoods.java      # 秒杀商品实体
│   ├── OrderInfo.java         # 订单信息实体
│   └── MiaoShaOrder.java      # 秒杀订单实体
├── service/          # 业务逻辑层
├── redis/            # Redis配置
├── util/             # 工具类
└── vo/               # 视图对象
```

## 核心功能模块

### 1. 用户管理
- **用户注册**: 手机号注册，密码加密存储
- **用户登录**: 基于Cookie的会话管理
- **参数解析**: 自定义UserArgumentResolver实现用户信息自动注入

### 2. 商品管理
- **商品列表**: 展示所有秒杀商品
- **商品详情**: 显示商品信息和秒杀状态
- **库存管理**: 实时库存显示和扣减

### 3. 秒杀功能
- **秒杀下单**: 高并发下的订单创建
- **重复购买检查**: 防止用户重复秒杀同一商品
- **库存扣减**: 原子性库存操作
- **订单状态查询**: 秒杀结果查询

## 数据库设计

### 核心表结构
- **user**: 用户信息表
- **goods**: 商品信息表  
- **miaosha_goods**: 秒杀商品表（包含秒杀价格、时间、库存）
- **order_info**: 订单信息表
- **miaosha_order**: 秒杀订单关联表

## 性能优化策略

### v1 - 基础版本
- 初始压测数据：
  - 获取商品列表: 1000并发 → 206 QPS
  - 获取商品详情: 1000并发 → 243 QPS
- **瓶颈分析**: 数据库成为主要瓶颈

### v2 - 缓存优化
1. **Redis缓存策略**
   - 页面级缓存: 整页HTML缓存到Redis
   - URL缓存: 基于URL的缓存键
   - 对象缓存: 细粒度数据对象缓存
   
2. **优化效果**
   - 获取商品列表: 1000并发 → 429 QPS (提升108%)
   - 获取商品详情: 1000并发 → 672 QPS (提升177%)

3. **前后端分离**
   - 静态化页面渲染
   - API接口返回JSON数据
   - 获取商品详情: 1000并发 → 373 QPS

## 技术亮点

### 1. 缓存策略
- **页面缓存**: 使用ThymeleafViewResolver手动渲染页面并缓存
- **缓存键管理**: 统一的Redis键管理策略
- **缓存过期**: 合理的缓存过期时间设置

### 2. 并发控制
- **事务管理**: 使用@Transactional确保数据一致性
- **重复购买检查**: 基于用户ID和商品ID的唯一性约束
- **库存扣减**: 数据库层面的原子性操作

### 3. 代码架构
- **分层架构**: Controller → Service → DAO 清晰分层
- **依赖注入**: 使用构造函数注入，提高代码可测试性
- **异常处理**: 统一的错误码和异常处理机制

## 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/miaosha
    username: root
    password: 123456
    type: com.alibaba.druid.pool.DruidDataSource
```

### Redis配置
```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    password: 123456
    jedis:
      pool:
        max-active: 10
        max-idle: 8
        min-idle: 0
        max-wait: 10000
```

## 运行说明

1. **环境要求**
   - JDK 1.8+
   - MySQL 5.7+
   - Redis 3.0+

2. **启动步骤**
   - 执行 `miaosha.sql` 创建数据库和表
   - 配置 `application.yml` 中的数据库和Redis连接信息
   - 运行 `MiaoshaApplication.java` 启动应用
   - 访问 `http://localhost:8080/miaosha`

3. **测试数据**
   - 系统已预置iPhone XR秒杀商品数据
   - 可注册新用户进行秒杀测试

## 后续优化方向

1. **消息队列**: 引入RabbitMQ处理异步订单
2. **分布式锁**: 使用Redis分布式锁防止超卖
3. **限流降级**: 实现接口限流和熔断机制
4. **CDN优化**: 静态资源CDN加速
5. **数据库优化**: 读写分离、分库分表
6. **监控告警**: 集成监控系统
