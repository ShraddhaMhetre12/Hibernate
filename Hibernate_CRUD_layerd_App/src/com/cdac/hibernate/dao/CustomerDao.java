package com.cdac.hibernate.dao;

import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.cdac.hibernate.Service.ICustomerService;
import com.cdac.hibernate.exception.CustomerException;
import com.cdac.hibernate.model.Customer;

public class CustomerDao implements ICustomerService {

	private sessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	private Query query;
	public CustomerDao() {
		 sessionFactory = new Configuration()
				 .configure().buildSessionFactory();
		 session.sessionFactory.open();
	}
		 
			
			@Override
			public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public Customer addCustomer(Customer customer) throws CustomerException {
		//DMl - so begin tx before save() method and after commit tx 
		
		
		return (Customer) session.save(customer);
	}

	@Override
	public Customer getCustomerById(Integer custId) throws CustomerException {
		// TODO Auto-generated method stub
		return (Customer) session.get(Customer.class,custId);
	}

	@Override
	public Customer UpdateCustomerById(Customer customer) throws CustomerException {
		// TODO Auto-generated method stub
		session.update(customer);
		return customer;
	}

	@Override
	public Customer deleteCustomer(Customer customer) throws CustomerException {
		// TODO Auto-generated method stub
		Customer customer=getCustomerById(custId);
		if(customer!=null)
			session.delete(customer);
		else
			customer=null;
		return customer;
	}

	@Override
	public List<Customer> listAllcustomer() throws CustomerException {
		// TODO Auto-generated method stub
		query = session.createQuery("from Customer");
		return query/list();
		
		return session.createQuery("from Customer").list();
		return null;
	}

}
