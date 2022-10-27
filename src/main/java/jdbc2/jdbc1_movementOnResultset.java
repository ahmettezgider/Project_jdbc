package jdbc2;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class jdbc1_movementOnResultset {

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
                "jdbc:mysql://" + hostname + ":3306/sakila",
                user,
                password
        );
        fis.close();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    @AfterTest
    public void afterTest() throws SQLException {
        conn.close();
        stmt.close();
    }


    @Test
    public void getResultSet() throws SQLException {
        String sql = "SELECT * FROM meslekler1";
        rs = stmt.executeQuery(sql);

    }

    @Test(description = "rs.absolute", dependsOnMethods = "getResultSet")
    public void test1() throws SQLException {
        rs.next();
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));

        rs.absolute(10);
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));

    }

    @Test(description = "rs.previous", dependsOnMethods = "getResultSet", invocationCount = 5)
    public void test2() throws SQLException {
        rs.absolute(10);
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));

        rs.previous();
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));


    }

    @Test(description = "rs.relative", dependsOnMethods = "getResultSet")
    public void test3() throws SQLException {
        rs.absolute(10);
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));

        rs.relative(2);
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));

        rs.relative(-4);
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));

    }

    @Test(description = "rs.first, rs.last", dependsOnMethods = "getResultSet")
    public void test4() throws SQLException {
        rs.last();
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));
        rs.first();
        System.out.println(
                rs.getString(1) + ", " +
                        rs.getString(2) + ", " +
                        rs.getString(3));
    }

}
