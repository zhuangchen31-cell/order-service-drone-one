# 无人机信息管理系统

## 项目简介

无人机信息管理系统是一个基于Spring Boot的企业级Web应用，用于管理无人机的完整生命周期信息。系统支持SQLite和MySQL数据库，并提供完整的CRUD操作功能。

## 技术栈

- **系统环境**：Java EE 8、Servlet 3.0、Apache Maven 3
- **主框架**：Spring Boot 2.2.x、Spring Framework 5.2.x、Apache Shiro 1.7
- **持久层**：Apache MyBatis 3.5.x、Hibernate Validation 6.0.x、Alibaba Druid 1.2.x
- **视图层**：Bootstrap 3.3.7、Thymeleaf 3.0.x

## 功能特性

1. **多数据库支持**：支持SQLite和MySQL数据库，可自由切换
2. **无人机信息管理**：包括录入、查询、修改、删除功能
3. **AI属性生成**：自动为无人机生成AI属性信息
4. **安全认证**：基于Apache Shiro的安全认证和授权
5. **请求拦截**：记录所有HTTP请求的详细信息
6. **响应式界面**：基于Bootstrap的响应式设计，支持多设备访问

## 项目结构

```
src/main/java/com/uav/management/
├── controller          # 控制器层
├── service            # 服务层接口
├── service/impl       # 服务层实现
├── mapper             # 数据访问层接口
├── entity             # 实体类
├── dto                # 数据传输对象
├── config             # 配置类
├── interceptor        # 拦截器
├── exception          # 异常类
├── util               # 工具类
├── constant           # 常量类
├── realm              # Shiro Realm实现
└── UavManagementApplication.java # 主应用类

src/main/resources/
├── mapper             # MyBatis XML映射文件
├── templates          # Thymeleaf模板文件
├── static             # 静态资源文件
└── application.yml    # 应用配置文件

docs/
├── init-mysql.sql     # MySQL数据库初始化脚本
└── init-sqlite.sql    # SQLite数据库初始化脚本
```

## 安装与运行

### 1. 克隆项目

```bash
git clone <项目地址>
cd uav-management
```

### 2. 配置数据库

#### SQLite配置（默认）
- 系统会自动在`data/`目录下创建SQLite数据库文件

#### MySQL配置
1. 修改`application.yml`文件，注释掉SQLite配置，取消注释MySQL配置
2. 执行`docs/init-mysql.sql`脚本初始化数据库

### 3. 构建项目

```bash
mvn clean package
```

### 4. 运行项目

```bash
mvn spring-boot:run
```

### 5. 访问系统

浏览器访问：http://localhost:8080

## 测试账号

| 账号  | 密码      | 角色  |
|-------|----------|------|
| admin | admin123 | ADMIN |

> **注意**：测试账号仅在数据库无用户数据时生效。若数据库中已有用户表，则以数据库中的用户数据为准。

## 主要功能

1. **安全登录**：Shiro 认证与授权，支持 MD5 哈希密码
2. **无人机列表**：查看所有无人机信息，支持分页、搜索和排序
3. **添加无人机**：录入新的无人机信息（含基本技术参数）
4. **编辑无人机**：修改已有的无人机信息
5. **删除无人机**：软删除无人机（POST 操作，防 CSRF）
6. **生成AI属性**：为无人机自动生成模拟 AI 属性信息

## 安全说明

- 所有数据操作（增删改）需要登录认证
- 删除和 AI 生成操作使用 POST 方法，防止 CSRF 攻击
- 密码使用 MD5 哈希存储（生产环境建议升级为 BCrypt）
- 登录页面不再显示明文测试账号信息

## 注意事项

1. 系统默认使用 SQLite 数据库，适合开发和测试环境
2. 生产环境建议使用 MySQL 数据库，并配置连接池参数
3. 生产环境建议将密码哈希算法升级为 BCrypt/PBKDF2
4. AI 属性生成功能为模拟实现，实际项目中可集成真实的 AI 服务
5. 系统启动时自动执行 `schema.sql` 和 `data.sql` 初始化数据库
