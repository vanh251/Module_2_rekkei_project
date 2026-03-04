package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String Driver ="org.postgresql.Driver";
    private static final String URL ="jdbc:postgresql://localhost:5432/module_2_rekkei_project";
    private static final String USERNAME ="postgres";
    private static final String PASSWORD ="vietanh2k5";

    public static Connection getConnection(){
        try {
            Class.forName(Driver);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection conn){
        if(conn != null){
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
