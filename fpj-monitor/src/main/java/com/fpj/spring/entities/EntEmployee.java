package com.fpj.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class EntEmployee implements IdentifiableEntity{

	@Id
	@SequenceGenerator(name="employee_seq", sequenceName="employee_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_seq")
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id")
	private EntCompany company;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "visa_expired_date")
	private Date visaExpiredDate;

	@Column(name = "work_permission_expiry_date")
	private Date workPermissionExpiryDate;
	
	@Column(name = "statement_expiry_date")
	private Date statementExpiryDate;
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;
	}

	public EntCompany getCompany() {
		return company;
	}

	public void setCompany(EntCompany company) {
		this.company = company;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getVisaExpiredDate() {
		return visaExpiredDate;
	}

	public void setVisaExpiredDate(Date visaExpiredDate) {
		this.visaExpiredDate = visaExpiredDate;
	}

	public Date getWorkPermissionExpiryDate() {
		return workPermissionExpiryDate;
	}

	public void setWorkPermissionExpiryDate(Date workPermissionExpiryDate) {
		this.workPermissionExpiryDate = workPermissionExpiryDate;
	}

	public Date getStatementExpiryDate() {
		return statementExpiryDate;
	}

	public void setStatementExpiryDate(Date statementExpiryDate) {
		this.statementExpiryDate = statementExpiryDate;
	}
	
	

}
