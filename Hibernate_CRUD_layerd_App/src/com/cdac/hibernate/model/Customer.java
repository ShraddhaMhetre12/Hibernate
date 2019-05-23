/**
 * 
 */
package com.cdac.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author DELL
 *
 */
@Entity 
@Table(name="CDAC_CUSTOMER")
public class Customer {
	@Id
	private Integer custId;
	private String custName;
	private String email;
	private String phone;
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(Integer custId, String custName, String email, String phone) {
		super();
		this.custId = custId;
		this.custName = custName;
		this.email = email;
		this.phone = phone;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", custName=" + custName + ", email=" + email + ", phone=" + phone + "]";
	}

}
