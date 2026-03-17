package dao.impl;

import dao.ICustomerDao;
import model.Customer;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements ICustomerDao {
    @Override
    public List<Customer> displayAllCustomers() {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from customer order by id desc ");
                ) {
            ResultSet rs = pre.executeQuery();
            List<Customer> customerList = new ArrayList<>();
            while (rs.next())
            {
                customerList.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }
            return customerList;
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn khách hàng: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("insert into customer(name, phone, email, address) values(?, ?, ?, ?)");
                ) {
            pre.setString(1, customer.getName());
            pre.setString(2, customer.getPhone());
            pre.setString(3, customer.getEmail());
            pre.setString(4, customer.getAddress());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }

    @Override
    public Customer findCustomerById(int id) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from customer where id = ?");
                ) {
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()){
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm khách hàng: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("update customer set name = ?, phone = ?, email = ?, address = ? where id = ?");
                ) {
            pre.setString(1, customer.getName());
            pre.setString(2, customer.getPhone());
            pre.setString(3, customer.getEmail());
            pre.setString(4, customer.getAddress());
            pre.setInt(5, customer.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật khách hàng: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteCustomer(int id) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("delete from customer where id = ?");
                ) {
            pre.setInt(1, id);
            pre.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Lỗi khi xoá khách hàng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from customer where name ilike ?");
                ) {
            pre.setString(1, "%" + name + "%");
            ResultSet rs = pre.executeQuery();
            List<Customer> customerList = new ArrayList<>();
            while (rs.next()){
                customerList.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }
            return customerList;
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm khách hàng theo tên: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from customer where email = ?");
                ) {
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            if (rs.next()){
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm khách hàng theo email: " + e.getMessage());
            return null;
        }
    }
}
