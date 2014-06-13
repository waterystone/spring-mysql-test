package com.adu.spring_test.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adu.spring_test.model.User;

public interface UserDao {
	/**
	 * 根据id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(int id);

	/**
	 * 获取用户的姓名
	 * 
	 * @param id
	 * @return
	 */
	public String getNameById(int id);

	/**
	 * 获取用户的年龄
	 * 
	 * @param id
	 * @return
	 */
	public int getAgeById(int id);

	/**
	 * 获取用户的某些字段信息
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getFieldsById(int id);

	/**
	 * 获取一批用户的姓名
	 * 
	 * @param ids
	 * @return
	 */
	public Map<Integer, String> getNamesByIds(List<Integer> ids);

	/**
	 * 获取一批id对应的若干字段信息
	 * 
	 * @param ids
	 * @return
	 */
	public Map<Integer, ArrayList<String>> getFieldsByIds(List<Integer> ids);

	/**
	 * 获取一批用户的信息
	 * 
	 * @param ids
	 * @return key为id，value为对应的User信息
	 */
	public Map<Integer, User> getUsersByIds(List<Integer> ids);

	/**
	 * 获取某段时间插入的用户
	 * 
	 * @param start
	 *            开始时间戳
	 * @param end
	 *            结束时间戳
	 * @return
	 */
	public List<User> getUsersBetweenTime(long start, long end);

	/**
	 * 获取某段时间插入的用户ID
	 * 
	 * @param start
	 *            开始时间戳
	 * @param end
	 *            结束时间戳
	 * @return
	 */
	public List<Integer> getIdsBetweenTime(long start, long end);

	/**
	 * 添加用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int addUser(User user);

	/**
	 * 更新用户信息(根据user的id)
	 * 
	 * @param user
	 * @return
	 */
	public int updateUser(User user);

	/**
	 * 根据id删除用户
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(int id);
}
