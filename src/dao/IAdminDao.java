package dao;

import model.Admin;

public interface IAdminDao {
    Admin findAdminByUserName(String username);
}
