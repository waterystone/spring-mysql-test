package com.adu.spring_test.dao;

import com.adu.spring_test.model.User;

public interface UserDAO {
	/**
	 * 根据id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(int id);

	/**
	 * 添加用户信息
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user);

	/**
	 * 根据id删除用户
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteById(int id);
}
