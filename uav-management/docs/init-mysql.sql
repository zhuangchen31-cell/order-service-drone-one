-- 创建数据库
CREATE DATABASE IF NOT EXISTS uav_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE uav_db;

-- 创建无人机信息表
CREATE TABLE IF NOT EXISTS drone (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(100) NOT NULL,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    manufacturer VARCHAR(100) NOT NULL,
    purchase_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    ai_properties TEXT,
    deleted TINYINT DEFAULT 0,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by VARCHAR(50),
    update_by VARCHAR(50),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    salt VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login_time DATETIME NULL,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建操作日志表
CREATE TABLE IF NOT EXISTS operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NULL,
    username VARCHAR(50) NULL,
    operation VARCHAR(50) NOT NULL,
    method VARCHAR(200) NOT NULL,
    params TEXT NULL,
    ip VARCHAR(50) NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    duration INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    error_msg TEXT NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据
-- 插入管理员用户（密码：admin123）
INSERT INTO user (username, password, salt, role, status) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 'ADMIN', 'ACTIVE');

-- 插入测试无人机数据
INSERT INTO drone (model, serial_number, manufacturer, purchase_date, status, ai_properties) VALUES
('DJI Mavic Air 2', 'MAVIC-AIR-2-001', 'DJI', '2023-01-15', 'IN_USE', '{"weight":"2.5kg","maxFlightTime":"30min","maxSpeed":"60km/h","maxAltitude":"500m","cameraResolution":"4K","batteryCapacity":"5000mAh","gpsAccuracy":"±1.5m","generatedAt":"2023-01-15 10:30:00","confidence":0.85}'),
('DJI Phantom 4 Pro', 'PHANTOM-4-PRO-001', 'DJI', '2022-06-20', 'MAINTENANCE', '{"weight":"3.0kg","maxFlightTime":"28min","maxSpeed":"72km/h","maxAltitude":"600m","cameraResolution":"4K","batteryCapacity":"5870mAh","gpsAccuracy":"±1.0m","generatedAt":"2022-06-20 14:20:00","confidence":0.88}'),
('Parrot Anafi', 'ANAFI-001', 'Parrot', '2023-03-10', 'IN_USE', '{"weight":"0.7kg","maxFlightTime":"25min","maxSpeed":"55km/h","maxAltitude":"400m","cameraResolution":"4K","batteryCapacity":"2700mAh","gpsAccuracy":"±2.0m","generatedAt":"2023-03-10 09:15:00","confidence":0.82}'),
('Autel Evo II', 'EVO-II-001', 'Autel', '2023-05-05', 'IN_USE', '{"weight":"1.1kg","maxFlightTime":"40min","maxSpeed":"72km/h","maxAltitude":"700m","cameraResolution":"8K","batteryCapacity":"7100mAh","gpsAccuracy":"±1.0m","generatedAt":"2023-05-05 11:45:00","confidence":0.90}'),
('Yuneec Typhoon H', 'TYPHOON-H-001', 'Yuneec', '2022-11-18', 'RETIRED', '{"weight":"2.8kg","maxFlightTime":"25min","maxSpeed":"50km/h","maxAltitude":"500m","cameraResolution":"4K","batteryCapacity":"5400mAh","gpsAccuracy":"±1.5m","generatedAt":"2022-11-18 16:30:00","confidence":0.80}');
