package jdbc1;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.sql.*;

public class MysqlProcessBase {
    Connection conn;
    Statement statement;
    ResultSet rs;


    @BeforeTest
    public void beforeTest() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:mysql://[ip]:3306/sakila",
                "user",
                "pass"
        );
        statement = conn.createStatement();
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
