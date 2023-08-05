import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class Main{
    public static void InsertImage(String name, String path, Connection conn) throws SQLException, IOException {
        FileInputStream image = new FileInputStream(path);
        String sql = "INSERT INTO images (name, image) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setBlob(2, image);
        stmt.executeUpdate();
    }

    private static void getImagesFromDb(Connection connection,String path) throws SQLException, IOException {
        int i = 1;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from images");
        System.out.println("Contents of the table are: ");
        while(rs.next()) {
            System.out.println(rs.getString("name"));
            Blob blob = rs.getBlob("image");
            byte byteArray[] = blob.getBytes(1,(int)blob.length());
            FileOutputStream outPutStream = new
                    FileOutputStream(path+"images"+i+".jpg");
            outPutStream.write(byteArray);
            System.out.println(path+"images"+i+".jpg");
            System.out.println();
            i++;
        }
    }

    private static void getAllFromAuthorbyName(Connection connection, String name) throws SQLException {
//        String Query = "SELECT * FROM Authors WHERE Name = ? ";
        String Query = "SELECT * FROM Authors";
        PreparedStatement statement = connection.prepareStatement(Query);
//        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + ", " + resultSet.getString(2)+ ", " + resultSet.getString(3));
        }
    }

    private static void getMultipleResult(Connection connection) throws SQLException {
        Statement stat = connection.createStatement();
        String command = "SELECT * FROM Authors";
        boolean isResult = stat.execute(command);
        boolean done = false;

        while (!done) {
            if (isResult) {
                ResultSet result = stat.getResultSet();

                while (result.next()) {

                    System.out.println(result.getString(1) + ", " + result.getString(2)+ ", " + result.getString(3));
                }
            } else {
                int updateCount = stat.getUpdateCount();
                if (updateCount >= 0) {

                    System.out.println("Rows affected: " + updateCount);
                } else {
                    done = true;
                }
            }
            if (!done) {
                isResult = stat.getMoreResults();
//                System.out.println(isResult);
            }
        }

        if (stat != null) {
            stat.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://david.iceteag8m1.click:3306/corejava_sql", "root", "mysql12345"
            );

            getMultipleResult(connection);
//            InsertImage("images","nicc.jpg",connection);
//            getImagesFromDb(connection,"C:/Users/U-ser/Desktop/");
//            getAllFromAuthorbyName(connection,"Brooks");

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
