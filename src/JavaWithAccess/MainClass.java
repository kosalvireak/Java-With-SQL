package JavaWithAccess;

import java.sql.*;

public class MainClass {
    public static void main(String[] args){
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String msAccDB= "C:\\Users\\U-ser\\Documents\\Database1.accdb";
            String dbUrl = "jdbc:ucanaccess://" + msAccDB;

            Connection conn = DriverManager.getConnection(dbUrl);
            Statement st = conn.createStatement();
            String sql = "SELECT * from ITEM1";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                System.out.println("\n"
                        + rs.getString(1)
                        + "\t" + rs.getString(2)
                        + "\t" + rs.getString(3));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}




