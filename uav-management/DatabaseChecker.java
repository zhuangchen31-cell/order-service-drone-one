import java.sql.*;

public class DatabaseChecker {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:data/uav.db";
        
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            
            try (Connection conn = DriverManager.getConnection(url)) {
                System.out.println("Connected to the database.");
                
                // Check drone table schema
                System.out.println("\nDrone table schema:");
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet rs = metaData.getColumns(null, null, "drone", null);
                while (rs.next()) {
                    System.out.println(rs.getString("COLUMN_NAME") + " " + rs.getString("TYPE_NAME"));
                }
                
                // Check drone table data
                System.out.println("\nDrone table data:");
                Statement stmt = conn.createStatement();
                ResultSet dataRs = stmt.executeQuery("SELECT * FROM drone WHERE deleted = 0");
                while (dataRs.next()) {
                    System.out.println("ID: " + dataRs.getLong("id") + ", Model: " + dataRs.getString("model") + ", Serial: " + dataRs.getString("serial_number"));
                }
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver not found: " + e.getMessage());
        }
    }
}