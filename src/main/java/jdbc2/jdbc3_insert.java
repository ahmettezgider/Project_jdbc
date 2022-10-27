package jdbc2;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class jdbc3_insert {
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static ResultSetMetaData rsmd;


    public static void main(String[] args) throws SQLException, IOException {
        connect();

        Scanner sc = new Scanner(System.in);
        System.out.print("Isim : ");
        String isim = sc.nextLine();
        System.out.print("Soyisim : ");
        String soyisim = sc.nextLine();
        System.out.print("Yas : ");
        int yas = sc.nextInt();
        sc.nextLine();
        System.out.print("Sehir : ");
        String sehir = sc.nextLine();

        String sql = "INSERT INTO kartal VALUES('" + isim + "', '" + soyisim + "'," + yas + " , '" + sehir + "')";
        int effectedRows = stmt.executeUpdate(sql);
        if (effectedRows<1)
            throw new RuntimeException("Kayit eklenemedi");

        sql = "SELECT * FROM kartal WHERE adi LIKE '%" + isim + "%'";
        rs = stmt.executeQuery(sql);
        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()){
            for (int i = 1; i <= cols; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }

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

        stmt = conn.createStatement();

    }


    public static void close() throws SQLException {
        conn.close();
        stmt.close();

    }
}
