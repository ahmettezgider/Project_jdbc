package jdbc2;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class jdbc5_insertDeleteUpdate {
    /*
        1. Before, After Test
        2. DataProvider ile 10 kayit ekleyin
        3. Update ile 2 kayit güncelleyin
        4. Delete ile 5 kayit silin.
     */

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


    @Test(dataProvider = "getData")
    public void insertData(Object[] arr) throws SQLException {
       String sql = "INSERT INTO kartal " +
               "VALUES('"+ arr[0] + "', '"+ arr[1] + "', " + arr[2] +", '"+ arr[3] + "');";

       if (stmt.executeUpdate(sql)<1)
           throw new RuntimeException("Kayit eklenemedi\n" + sql);

    }

    @Test(dataProvider = "getDataForUpdate")
    public void updateData(Object[] arr) throws SQLException {
        String sql = "UPDATE kartal " +
                "SET adi = '"+ arr[1] + "', " +
                "soyadi = '"+ arr[2] + "', " +
                "yas = "+ arr[3] + ", " +
                "sehir = '"+ arr[4] + "' " +
                "WHERE adi = '"+ arr[0] + "';";

        if (stmt.executeUpdate(sql)<1)
            throw new RuntimeException("Kayit güncellenemedi\n" + sql);

    }

    @Test
    public void deleteAllData() throws SQLException {
        String sql = "DELETE FROM kartal;";

        if (stmt.execute(sql))
            throw new RuntimeException("Kayit Silinemedi\n" + sql);

    }

    @Test
    public void writeResults() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM kartal");
        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()){
            for (int i = 1; i <= cols; i++) {
                System.out.printf("%-20s", rs.getString(i));
            }
            System.out.println();
        }
    }



    @DataProvider
    public Object[][] getData(){
        return new Object[][]{
                {"A1","A1",11, "A1"},
                {"A2","A2",12, "A2"},
                {"A3","A3",13, "A3"},
                {"A4","A4",14, "A4"},
                {"A5","A5",15, "A5"},
                {"A6","A6",16, "A6"},
                {"A7","A7",17, "A7"},
                {"A8","A8",18, "A8"},
                {"A9","A9",19, "A9"},
                {"A10","A10",20, "A10"}
        };
    }


    @DataProvider
    public Object[][] getDataForUpdate(){
        return new Object[][]{
                {"A1", "A11","A11",21,"A11"},
                {"A2", "A22","A22",22, "A22"},
                {"A3", "A33","A33",23, "A33"},
                {"A4", "A44","A44",24, "A44"},
                {"A5", "A55","A55",25, "A55"},
        };
    }



}
