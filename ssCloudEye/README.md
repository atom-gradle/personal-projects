# Lionhill CloudVision Backend (狮山云瞳后端系统)

> Backend Services for Portable Corn Phenotyping Measurement System

[![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7.0-DC382D?logo=redis)](https://redis.io/)
[![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.0-000000)](https://baomidou.com/)

## 📋 项目概述

狮山云瞳后端系统是基于Spring Boot的现代化农业表型数据采集平台，为玉米育种研究提供高效、可靠的数据处理和服务支撑。

## 🏗️ 系统架构

### 后端技术栈


## 🛠️ 技术栈详情

### 核心框架
- **Spring Boot 3.0** - 主应用框架
- **Spring Security** - 安全认证框架
- **Spring Data Redis** - Redis集成
- **MyBatis-Plus 3.5.0** - 数据持久层
- **Sa-Token** - 权限认证框架

### 数据存储
- **MySQL 8.0** - 主数据库
- **Redis 7.0** - 缓存和会话存储
- **阿里云OSS** - 图像文件存储

### 其他组件
- **HikariCP** - 数据库连接池
- **Jackson** - JSON序列化
- **Lombok** - 代码简化
- **Hibernate Validator** - 参数校验

## 🎯 我的贡献

作为核心后端开发成员，在老师和学长指导下负责：

### 🔧 架构优化与代码质量
- **设计并实现统一异常处理机制**，减少控制器代码冗余约**30%**，统一API错误响应格式
- **主导数据库性能优化**，通过分析和优化SQL查询及索引，关键接口响应时间降低**约15%**
- **推动代码现代化改造**，使用Java 8 Streams API和Optional重构传统代码，代码量减少**约20%**，提升可读性和空安全
