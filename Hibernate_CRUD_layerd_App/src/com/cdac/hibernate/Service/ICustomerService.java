package com.cdac.hibernate.Service;

import java.util.List;

import com.cdac.hibernate.exception.CustomerException;
import com.cdac.hibernate.model.Customer;

public interface ICustomerService {
	public Customer addCustomer(Customer customer)throws CustomerException;
	public Customer getCustomerById(Integer custId)throws CustomerException;
	public Customer UpdateCustomerById(Customer customer)throws CustomerException;
	public Customer deleteCustomer(Customer customer)throws CustomerException;
	public List<Customer> listAllcustomer()throws CustomerException;

}
