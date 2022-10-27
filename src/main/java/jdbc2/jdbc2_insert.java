package jdbc2;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class jdbc2_insert {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    ResultSetMetaData rsmd;

    @BeforeTest
    public void beforeTest() throws SQLException, IOException {
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

        stmt = conn.createStatement();

    }

    @AfterTest
    public void afterTest() throws SQLException {
        conn.close();
        stmt.close();
    }

    @Test
    public void test1() throws SQLException {
        String sql = "INSERT INTO kartal VALUES('Ali', 'A', 12,'IST');";
        int insNum = stmt.executeUpdate(sql);
        System.out.println(insNum + " kayit eklendi");
    }

    @Test
    public void test2() throws SQLException {
        String sql = "SELECT * FROM kartal";
        rs = stmt.executeQuery(sql);
        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()){
            for (int i = 1; i <= cols; i++)
                System.out.printf("%-20s", rs.getString(i));
            System.out.println();
        }
    }



}
