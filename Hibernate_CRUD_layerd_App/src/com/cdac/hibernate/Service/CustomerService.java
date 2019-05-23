package com.cdac.hibernate.Service;

import java.util.List;

import com.cdac.hibernate.dao.CustomerDao;
import com.cdac.hibernate.dao.ICustomerDao;
import com.cdac.hibernate.exception.CustomerException;
import com.cdac.hibernate.model.Customer;

public class CustomerService implements ICustomerService {

	private ICustomerDao customerDao;
	
	public CustomerService() {
		customerDao = new CustomerDao();
	}
	public CustomerService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Customer addCustomer(Customer customer) throws CustomerException {
		customerDao.beginTx();
		Customer addedCustomer=customerDao.addCustomer(customer);
		customerDao.commitTx();
		return addedCustomer;
	}

	@Override
	public Customer getCustomerById(Integer custId) throws CustomerException {
		
		return customerDao.getCustomerById(custId);
	}

	@Override
	public Customer UpdateCustomerById(Customer customer) throws CustomerException {
	Customer added
		return null;
	}

	@Override
	public Customer deleteCustomer(Customer customer) throws CustomerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> listAllcustomer() throws CustomerException {
		// TODO Auto-generated method stub
		return null;
	}

}
