package com.learning.config;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.learning.Utils.PasswordUtils;



@Configuration //it is used to mark on config class/classes.
//here we will hold all commonly required objects for our application
//@ComponentScan("com.zee.zee5app") //base package is the scope
//@PropertySource("classpath:application.properties") //it is responsible to read the property file
public class Config {
	
	@Autowired //it will bring the already created objects based on the name(reference name)/type
	Environment environment; //this obj is prepared by spring
	//we need to inject that already created object - dependency injection - IoC
	
	
	// config: db related, reading properties file, commonly required beans(passwordEncoder)
	
//	@Bean(name = "ds") //it will provide for singleton obj // it is a method level annotation
//	@Scope("prototype") //if we call getbean method n number of times, we get n objects
//	//if we will not specify the bean name then it will take/consider the method name as bean name
//	//@Scope("singleton") - to get singleton object - that is one instance
//	public DataSource dataSource() {
//		
//		BasicDataSource basicDataSource = new BasicDataSource();
//		basicDataSource.setUsername(environment.getProperty("jdbc.username"));
//		basicDataSource.setPassword(environment.getProperty("jdbc.password"));
//		basicDataSource.setUrl(environment.getProperty("jdbc.url"));
//		basicDataSource.setDefaultAutoCommit(false);
//		return basicDataSource;
//		
//	}
	
	//this object can be initialized as per the requirement and we can customize it as required
	@Bean
	public PasswordUtils passwordUtils() {
		return new PasswordUtils();
	}

}
