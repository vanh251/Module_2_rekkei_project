package dao;

import model.Customer;

import java.util.List;

public interface ICustomerDao {
    List<Customer> displayAllCustomers();
    void addCustomer(Customer customer);
    Customer findCustomerById(int id);
    void updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
    List<Customer> findCustomersByName(String name);
    Customer findCustomerByEmail(String email);
}
