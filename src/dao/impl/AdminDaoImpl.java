package dao.impl;

import dao.IAdminDao;
import model.Admin;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements IAdminDao {
    @Override
    public Admin findAdminByUserName(String username) {
        String sql = "select * from admin where username = ?";
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre =conn.prepareStatement(sql);
                ){
            pre.setString(1, username);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                return new Admin(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
