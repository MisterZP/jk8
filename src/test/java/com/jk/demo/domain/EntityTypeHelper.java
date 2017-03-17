package com.jk.demo.domain;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class EntityTypeHelper{
	
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clz){
		try {
			return Introspector.getBeanInfo(clz).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
