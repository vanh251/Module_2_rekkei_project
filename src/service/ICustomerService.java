package service;

import model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> displayAllCustomers();
    void addCustomer(Customer customer);
    Customer findCustomerById(int id);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
    List<Customer> findCustomersByName(String name);
}
