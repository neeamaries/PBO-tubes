import src.config.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {
     public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            System.out.println("Koneksi ke Azure SQL berhasil!");

            conn.close();

        } catch (Exception e) {
            System.out.println("Koneksi gagal!");
            e.printStackTrace();
        }
    }
}
