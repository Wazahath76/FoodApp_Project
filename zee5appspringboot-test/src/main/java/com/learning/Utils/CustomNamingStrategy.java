package com.learning.Utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {
	
	private final static String POSTFIX = "_table";
	//by default all tables should be ended with name_table
	//we dont want to apply this_table for each and every entity spec
	//we want to set it as thumb rule
	
	@Override
	public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment context) {
		// TODO Auto-generated method stub
		if(identifier == null) {
			return null;
		}
		final String newName = identifier.getText()+POSTFIX;
		//this retrieves table name
		//1. if @table annotation is available then it will use that name
		//2. if @table is not there it will take entity name, if entity name is not available then by default it will take class name as entity name
	    return identifier.toIdentifier(newName);
	}
	
	@Override
	public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment context) {
		// TODO Auto-generated method stub
		if(identifier == null) {
			return null;
		}
		
		return identifier.toIdentifier(identifier.getText().toLowerCase());
	}

}
