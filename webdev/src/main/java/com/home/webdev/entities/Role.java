package com.home.webdev.entities;

import javax.persistence.*;

@Entity
@Table (name="role")
public class Role {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
	private Integer id;
	
	@Column(name = "role_name", nullable = false)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Role() {
		super();
	}
	
	
}
