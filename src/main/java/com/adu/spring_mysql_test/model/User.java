package com.adu.spring_mysql_test.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return "User [id=" + id + ", name=" + name + ", age=" + age
				+ ", isMale=" + isMale + ", insertTime="
				+ simpleDateFormat.format(insertTime) + "]";
	}

}
