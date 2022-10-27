package jdbc1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Mysqlconn1 {

    public static void main(String[] args) throws SQLException, IOException {
        // jdbc connection icin kullanilacak
        FileInputStream fis = new FileInputStream("src/main/resources/DB.properties");
        Properties properties = new Properties();
        properties.load(fis);

        Connection conn;
        Statement statement;
        ResultSet resultSet;

        // url = "jdbc:mysql://[ ip | localhost | 127.0.0.1 ]:[port]/[database]",
        // jdbc:mysql://[ip]:3306/sakila
        // https://localhost/sakila
        String hostname = properties.getProperty("gs.hostname");
        String user = properties.getProperty("gs.user");
        String password = properties.getProperty("gs.pass");
        conn = DriverManager.getConnection(
                "jdbc:mysql://" + hostname + ":3306/sakila",
                user,
                password);

        statement = conn.createStatement();

        resultSet = statement.executeQuery("SELECT * FROM actor LIMIT 5;");

        while (resultSet.next()){
            int actor_id =  resultSet.getInt(1);
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString(3);
            System.out.printf("%-5d%-20s%-20s\n", actor_id, first_name, last_name);
        }

        statement.close();
        conn.close();
        fis.close();

    }




}
