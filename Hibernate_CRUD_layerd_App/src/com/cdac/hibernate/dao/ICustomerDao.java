package com.cdac.hibernate.dao;

import java.util.List;

import com.cdac.hibernate.exception.CustomerException;
import com.cdac.hibernate.model.Customer;

public interface ICustomerDao {
	public Customer addCustomer(Customer customer)throws CustomerException;
	public Customer getCustomerById(Integer custId)throws CustomerException;
	public Customer updateCustomer(Customer customer)throws CustomerException;
	public Customer deleteCustomer(Customer customer)throws CustomerException;
	public List<Customer> listAllcustomer()throws CustomerException;
	public Transaction beginTx()throws CustomerException;
	public void commitTx()throws CustomerException;

}
