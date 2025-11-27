package murach.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // 1. Load Driver
        Class.forName("org.postgresql.Driver");

        // 2. Lấy thông tin từ biến môi trường
        String dbURL = System.getenv("DB_URL");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        // 3. Fallback: Nếu chạy Local (không có biến môi trường) thì dùng cấu hình hardcode
        if (dbURL == null) {
            dbURL = "jdbc:postgresql://dpg-d4jvcpali9vc73ddndkg-a.singapore-postgres.render.com:5432/ch12_ex1_sqlgateway_db";
        }
        if (username == null) {
            username = "ch12_ex1_sqlgateway_db_user";
        }
        if (password == null) {
            password = "nPN6IiLfaIuEEPXUxnyDpWRMCMV8DUMF";
        }

        // 4. Trả về kết nối
        return DriverManager.getConnection(dbURL, username, password);
    }
}