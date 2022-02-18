package com.learning.dto;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.learning.Utils.CustomNamingStrategy;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
 @Getter
@NoArgsConstructor


@EqualsAndHashCode
@ToString
@Entity
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = "username"),@UniqueConstraint(columnNames = "email")})
public class User implements Comparable<User>{

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Size(max=20)
	@NotBlank
	private String username;
	
	@Size(max=50)
	@Email
	private String email;
	
	@Size(max=50)
	@NotBlank
	private String name;
	
	@Size(max=100)
	@NotBlank
	private String password;
	
	@Size(max=100)
	@NotBlank
	private String address;
	
	@Override
	public int compareTo(User o) {
		 //TODO Auto-generated method stub
		return this.id.compareTo(o.getId());

	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"), 
	inverseJoinColumns = @JoinColumn(name = "roleId") )
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Login login;
	
	public User(String username,String email, String name,String password,String address) {
		this.username=username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.address=address;
	}

}