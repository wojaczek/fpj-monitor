package com.fpj.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class EntCompany implements IdentifiableEntity{

	@Id
	@SequenceGenerator(name="company_seq", sequenceName="company_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="company_seq")
	private Integer id;
	@Column(length=128, name="companyname")
	private String companyName;
	@Override
	public Integer getId() {
		return id;
	}
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
