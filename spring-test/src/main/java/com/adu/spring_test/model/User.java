package com.adu.spring_test.model;

import java.io.Serializable;

public class User implements Serializable {

	/**  */
	private static final long serialVersionUID = 2146592501636315338L;

	private int id;
	private String name;
	private int age;
	private boolean isMale;
	private long insertTime;

	public User() {
		super();
	}

	public User(String name, int age, boolean isMale) {
		super();
		this.name = name;
		this.age = age;
		this.isMale = isMale;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public long getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age
				+ ", isMale=" + isMale + ", insertTime=" + insertTime + "]";
	}

}
