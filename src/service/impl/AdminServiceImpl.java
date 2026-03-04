package service.impl;

import dao.IAdminDao;
import dao.impl.AdminDaoImpl;
import model.Admin;
import org.mindrot.jbcrypt.BCrypt;
import service.IAdminService;

public class AdminServiceImpl implements IAdminService {
    private static final IAdminDao adminDao = new AdminDaoImpl();
    @Override
    public Admin login(String username, String password) {
        Admin admin = adminDao.findAdminByUserName(username);
        if (admin != null && password.equals(admin.getPassword())){
            return admin;
        }
        return null;
    }
}
