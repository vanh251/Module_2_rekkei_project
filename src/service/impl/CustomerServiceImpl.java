package service.impl;

import dao.ICustomerDao;
import dao.impl.CustomerDaoImpl;
import model.Customer;
import service.ICustomerService;

import java.util.ArrayList;

public class CustomerServiceImpl implements ICustomerService {
    private static final ICustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public ArrayList<Customer> displayAllCustomers() {
        return customerDao.displayAllCustomers();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDao.addCustomer(customer);
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerDao.findCustomerById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        customerDao.deleteCustomer(id);
    }
}
