package com.lbb.rabbitmq.server.bean;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8214189390966058048L;
	private Integer age;
	private String name;
	private Date birth;
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	
}
