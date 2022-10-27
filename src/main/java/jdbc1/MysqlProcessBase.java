package jdbc1;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class MysqlProcessBase {
    Connection conn;
    Statement statement;
    ResultSet rs;


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
        statement = conn.createStatement();
        fis.close();
    }

    @AfterTest
    public void AfterTest(){
        try {
            conn.close();
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
