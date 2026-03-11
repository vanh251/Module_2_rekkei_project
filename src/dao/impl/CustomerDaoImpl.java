package dao.impl;

import dao.ICustomerDao;
import model.Customer;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements ICustomerDao {
    @Override
    public ArrayList<Customer> displayAllCustomers() {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("select * from customer");
                ) {
            ResultSet rs = pre.executeQuery();
            ArrayList<Customer> customerList = new ArrayList<>();
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
            e.printStackTrace();
            return null;
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int id) {
        try(
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement("delete from customer where id = ?");
        ) {
            pre.setInt(1, id);
            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
