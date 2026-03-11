package dao;

import model.Customer;

import java.util.ArrayList;

public interface ICustomerDao {
    ArrayList<Customer> displayAllCustomers();
    void addCustomer(Customer customer);
    Customer findCustomerById(int id);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
}
