package com.fpj.spring.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class EntUser implements IdentifiableEntity{
	
	
	public enum EntUserRole {
		ROLE_USER, ROLE_ADMIN
	}

	@Id
	@SequenceGenerator(name="user_seq", sequenceName="user_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_seq")
	Integer id;
	
	@Column(name = "username")
	String username;

	@Column(name = "password")
	String password;

	@Column(name = "email")
	String email;

	@ElementCollection
	@CollectionTable(name="user_roles", 
		joinColumns=@JoinColumn(name="user_id"))
	@Column(name="role")
	List<EntUserRole> roles = new ArrayList<EntUserRole>();

	private Boolean enabled;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<EntUserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<EntUserRole> roles) {
		this.roles = roles;
	}

	public Boolean getEnabled(){
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled  = enabled;
	}

	
}
