-- 插入管理员用户（密码：admin123，MD5加密后：e10adc3949ba59abbe56e057f20f883e）
INSERT OR IGNORE INTO user (username, password, salt, role, status, create_time) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 'ADMIN', 'ACTIVE', datetime('now'));

-- 插入测试无人机数据（使用 INSERT OR IGNORE 避免重复）
INSERT OR IGNORE INTO drone (model, serial_number, manufacturer, purchase_date, status, ai_properties, deleted, create_time, update_time) VALUES
('DJI Mavic Air 2', 'MAVIC-AIR-2-001', 'DJI', '2023-01-15', 'IN_USE', '{"weight":"2.5kg","maxFlightTime":"30min","maxSpeed":"60km/h","maxAltitude":"500m","cameraResolution":"4K","batteryCapacity":"5000mAh","gpsAccuracy":"±1.5m"}', 0, datetime('now'), datetime('now')),
('DJI Phantom 4 Pro', 'PHANTOM-4-PRO-001', 'DJI', '2022-06-20', 'MAINTENANCE', '{"weight":"3.0kg","maxFlightTime":"28min","maxSpeed":"72km/h","maxAltitude":"600m","cameraResolution":"4K","batteryCapacity":"5870mAh","gpsAccuracy":"±1.0m"}', 0, datetime('now'), datetime('now')),
('Parrot Anafi', 'ANAFI-001', 'Parrot', '2023-03-10', 'IN_USE', '{"weight":"0.7kg","maxFlightTime":"25min","maxSpeed":"55km/h","maxAltitude":"400m","cameraResolution":"4K","batteryCapacity":"2700mAh","gpsAccuracy":"±2.0m"}', 0, datetime('now'), datetime('now')),
('Autel Evo II', 'EVO-II-001', 'Autel', '2023-05-05', 'IN_USE', '{"weight":"1.1kg","maxFlightTime":"40min","maxSpeed":"72km/h","maxAltitude":"700m","cameraResolution":"8K","batteryCapacity":"7100mAh","gpsAccuracy":"±1.0m"}', 0, datetime('now'), datetime('now')),
('Yuneec Typhoon H', 'TYPHOON-H-001', 'Yuneec', '2022-11-18', 'RETIRED', '{"weight":"2.8kg","maxFlightTime":"25min","maxSpeed":"50km/h","maxAltitude":"500m","cameraResolution":"4K","batteryCapacity":"5400mAh","gpsAccuracy":"±1.5m"}', 0, datetime('now'), datetime('now'));