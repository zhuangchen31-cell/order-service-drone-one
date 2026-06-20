-- 创建无人机信息表
CREATE TABLE IF NOT EXISTS drone (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    model VARCHAR(100) NOT NULL,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    manufacturer VARCHAR(100) NOT NULL,
    purchase_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    ai_properties TEXT,
    deleted TINYINT DEFAULT 0,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    create_by VARCHAR(50),
    update_by VARCHAR(50)
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_status ON drone(status);
CREATE INDEX IF NOT EXISTS idx_create_time ON drone(create_time);

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    salt VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    create_time DATETIME NOT NULL,
    last_login_time DATETIME NULL
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_username ON user(username);

-- 创建操作日志表
CREATE TABLE IF NOT EXISTS operation_log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id BIGINT NULL,
    username VARCHAR(50) NULL,
    operation VARCHAR(50) NOT NULL,
    method VARCHAR(200) NOT NULL,
    params TEXT NULL,
    ip VARCHAR(50) NOT NULL,
    create_time DATETIME NOT NULL,
    duration INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    error_msg TEXT NULL
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_user_id ON operation_log(user_id);
CREATE INDEX IF NOT EXISTS idx_create_time ON operation_log(create_time);