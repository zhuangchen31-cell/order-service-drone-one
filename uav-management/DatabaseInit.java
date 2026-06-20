import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInit {
    public static void main(String[] args) {
        try {
            // Load SQLite driver
            Class.forName(JDBC.class.getName());
            
            // Connect to database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/uav.db");
            Statement stmt = conn.createStatement();
            
            // Create drone table
            String createDroneTable = "CREATE TABLE IF NOT EXISTS drone (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "model VARCHAR(100) NOT NULL, " +
                    "serial_number VARCHAR(100) NOT NULL UNIQUE, " +
                    "manufacturer VARCHAR(100) NOT NULL, " +
                    "purchase_date DATE NOT NULL, " +
                    "status VARCHAR(20) NOT NULL, " +
                    "ai_properties TEXT, " +
                    "deleted TINYINT DEFAULT 0, " +
                    "create_time DATETIME NOT NULL, " +
                    "update_time DATETIME NOT NULL, " +
                    "create_by VARCHAR(50), " +
                    "update_by VARCHAR(50) " +
                    ")";
            stmt.execute(createDroneTable);
            System.out.println("Created drone table successfully");
            
            // Create user table
            String createUserTable = "CREATE TABLE IF NOT EXISTS user (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username VARCHAR(50) NOT NULL UNIQUE, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "salt VARCHAR(50) NOT NULL, " +
                    "role VARCHAR(20) NOT NULL, " +
                    "status VARCHAR(20) NOT NULL, " +
                    "create_time DATETIME NOT NULL, " +
                    "last_login_time DATETIME NULL " +
                    ")";
            stmt.execute(createUserTable);
            System.out.println("Created user table successfully");
            
            // Create operation_log table
            String createOperationLogTable = "CREATE TABLE IF NOT EXISTS operation_log (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id BIGINT NULL, " +
                    "username VARCHAR(50) NULL, " +
                    "operation VARCHAR(50) NOT NULL, " +
                    "method VARCHAR(200) NOT NULL, " +
                    "params TEXT NULL, " +
                    "ip VARCHAR(50) NOT NULL, " +
                    "create_time DATETIME NOT NULL, " +
                    "duration INT NOT NULL, " +
                    "status VARCHAR(20) NOT NULL, " +
                    "error_msg TEXT NULL " +
                    ")";
            stmt.execute(createOperationLogTable);
            System.out.println("Created operation_log table successfully");
            
            // Insert admin user
            String insertAdminUser = "INSERT OR IGNORE INTO user (username, password, salt, role, status, create_time) VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', '123456', 'admin', 'active', datetime('now'))";
            stmt.execute(insertAdminUser);
            System.out.println("Inserted admin user successfully");
            
            // Insert test drone data
            String insertDrone1 = "INSERT OR IGNORE INTO drone (model, serial_number, manufacturer, purchase_date, status, deleted, create_time, update_time) VALUES ('DJI Mavic Air 2', 'DJI2021001', 'DJI', '2021-01-01', '在用', 0, datetime('now'), datetime('now'))";
            stmt.execute(insertDrone1);
            
            String insertDrone2 = "INSERT OR IGNORE INTO drone (model, serial_number, manufacturer, purchase_date, status, deleted, create_time, update_time) VALUES ('DJI Phantom 4 Pro', 'DJI2021002', 'DJI', '2021-02-01', '在用', 0, datetime('now'), datetime('now'))";
            stmt.execute(insertDrone2);
            
            String insertDrone3 = "INSERT OR IGNORE INTO drone (model, serial_number, manufacturer, purchase_date, status, deleted, create_time, update_time) VALUES ('DJI Mini 2', 'DJI2021003', 'DJI', '2021-03-01', '停用', 0, datetime('now'), datetime('now'))";
            stmt.execute(insertDrone3);
            System.out.println("Inserted test drone data successfully");
            
            // Close connection
            stmt.close();
            conn.close();
            
            System.out.println("Database initialization completed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}