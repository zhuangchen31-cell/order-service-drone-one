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

-- 插入测试数据
-- 插入管理员用户（密码：admin123）
INSERT INTO user (username, password, salt, role, status, create_time) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 'ADMIN', 'ACTIVE', datetime('now'));

-- 插入测试无人机数据
INSERT INTO drone (model, serial_number, manufacturer, purchase_date, status, ai_properties, create_time, update_time) VALUES
('DJI Mavic Air 2', 'MAVIC-AIR-2-001', 'DJI', '2023-01-15', 'IN_USE', '{"weight":"2.5kg","maxFlightTime":"30min","maxSpeed":"60km/h","maxAltitude":"500m","cameraResolution":"4K","batteryCapacity":"5000mAh","gpsAccuracy":"±1.5m","generatedAt":"2023-01-15 10:30:00","confidence":0.85}', datetime('now'), datetime('now')),
('DJI Phantom 4 Pro', 'PHANTOM-4-PRO-001', 'DJI', '2022-06-20', 'MAINTENANCE', '{"weight":"3.0kg","maxFlightTime":"28min","maxSpeed":"72km/h","maxAltitude":"600m","cameraResolution":"4K","batteryCapacity":"5870mAh","gpsAccuracy":"±1.0m","generatedAt":"2022-06-20 14:20:00","confidence":0.88}', datetime('now'), datetime('now')),
('Parrot Anafi', 'ANAFI-001', 'Parrot', '2023-03-10', 'IN_USE', '{"weight":"0.7kg","maxFlightTime":"25min","maxSpeed":"55km/h","maxAltitude":"400m","cameraResolution":"4K","batteryCapacity":"2700mAh","gpsAccuracy":"±2.0m","generatedAt":"2023-03-10 09:15:00","confidence":0.82}', datetime('now'), datetime('now')),
('Autel Evo II', 'EVO-II-001', 'Autel', '2023-05-05', 'IN_USE', '{"weight":"1.1kg","maxFlightTime":"40min","maxSpeed":"72km/h","maxAltitude":"700m","cameraResolution":"8K","batteryCapacity":"7100mAh","gpsAccuracy":"±1.0m","generatedAt":"2023-05-05 11:45:00","confidence":0.90}', datetime('now'), datetime('now')),
('Yuneec Typhoon H', 'TYPHOON-H-001', 'Yuneec', '2022-11-18', 'RETIRED', '{"weight":"2.8kg","maxFlightTime":"25min","maxSpeed":"50km/h","maxAltitude":"500m","cameraResolution":"4K","batteryCapacity":"5400mAh","gpsAccuracy":"±1.5m","generatedAt":"2022-11-18 16:30:00","confidence":0.80}', datetime('now'), datetime('now'));
