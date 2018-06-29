package com.home.webdev.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name ="user")
public class User {
	@Id
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Column( name ="password", nullable = false)
	private String password;
	
	@Column (name = "fullname",nullable = false)
	private String fullname;
	
	@Column(name = "email", nullable = true)
	private String email;
	
	@Column(name ="address", nullable = true)
	private String address;
	
	@Column(name = "phone",nullable = true)
	private String phone;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dayofbirth", nullable = true)
	private Date dayofbirth;
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name="role_id", nullable= true)
	private Role role;

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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDayofbirth() {
		return dayofbirth;
	}

	public void setDayofbirth(Date dayofbirth) {
		this.dayofbirth = dayofbirth;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User(String username, String password, String fullname, String email, String address, String phone,
			Date dayofbirth, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.dayofbirth = dayofbirth;
		this.role = role;
	}

	public User() {
		super();
	}
	
	
}
