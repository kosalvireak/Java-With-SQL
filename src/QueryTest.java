import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class QueryTest{
    public static void InsertImage(String name, String path, Connection conn) throws SQLException, IOException {
        FileInputStream image = new FileInputStream(path);
        String sql = "INSERT INTO images (name, image) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setBlob(2, image);
        stmt.executeUpdate();
    }

    private static void getImagesFromDb(Connection connection) throws SQLException, IOException {
        int i = 1;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from images");
        System.out.println("Contents of the table are: ");
        while(rs.next()) {
            System.out.println(rs.getString("name"));
            Blob blob = rs.getBlob("image");
            byte byteArray[] = blob.getBytes(1,(int)blob.length());
            FileOutputStream outPutStream = new
                    FileOutputStream("/home/kosalvireak/Desktop/images"+i+".jpg");
            outPutStream.write(byteArray);
            System.out.println("/home/kosalvireak/Desktop/images"+i+".jpg");
            System.out.println();
            i++;
        }
    }
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://david.iceteag8m1.click:3306/corejava_sql", "root", "mysql12345"
            );

//            InsertImage("third image","image2.jpg",connection);
//            getImagesFromDb(connection);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

//            statement.setString(1, "Brooks");
//            ResultSet resultSet = statement.executeQuery();

//            System.out.println(resultSet.getString(1) + ", " + resultSet.getString(2));