package jdbc2;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class jdbc4_prepareStatement {
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static ResultSetMetaData rsmd;
    static PreparedStatement pstmt;


    public static void main(String[] args) throws SQLException, IOException {
        connect();

        pstmt.setString(1, "first_name");
        pstmt.setString(2, "last_name");
        pstmt.setInt(3, 55);
        pstmt.setString(4, "city");

        pstmt.executeUpdate();
        close();
    }

    public static void connect() throws SQLException, IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/DB.properties");
        Properties properties = new Properties();
        properties.load(fis);
        String hostname = properties.getProperty("gs.hostname");
        String user = properties.getProperty("gs.user");
        String password = properties.getProperty("gs.pass");
        conn = DriverManager.getConnection(
                "jdbc:mysql://" + hostname + ":3306/tempdb",
                user,
                password
        );
        fis.close();

        pstmt = conn.prepareStatement("INSERT INTO kartal VALUES(?, ?, ?, ?);");

    }


    public static void close() throws SQLException {
        conn.close();
        pstmt.close();

    }
}

//SELECT * FROM table WHERE eMail = '' OR 1=1 OR '' AND password = 'XXXXX';