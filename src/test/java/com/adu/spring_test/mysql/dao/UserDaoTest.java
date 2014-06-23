package com.adu.spring_test.mysql.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adu.spring_test.mysql.dao.UserDao;
import com.adu.spring_test.mysql.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest {
	@Autowired
	private UserDao userDao;

	private Log logger = LogFactory.getLog(this.getClass());

	@Test
	public void getUserById() {
		int id = 1;
		User user = userDao.getUserById(id);
		logger.debug("user=" + user);
	}

	@Test
	public void getNameById() {
		int id = 1;
		String name = userDao.getNameById(id);
		logger.debug("name=" + name);
	}

	@Test
	public void getAgeById() {
		int id = 0;
		int age = userDao.getAgeById(id);
		logger.debug("age=" + age);
	}

	@Test
	public void getFieldsById() {
		int id = 1;
		List<String> res = userDao.getFieldsById(id);
		logger.debug("res=" + res);
	}

	@Test
	public void getNamesByIds() {
		List<Integer> ids = Arrays.asList(0, 1, 2, 3);
		Map<Integer, String> namesMap = userDao.getNamesByIds(ids);
		for (int id : ids) {
			String name = namesMap.get(id);
			logger.debug("id=" + id + ",name=" + name);
		}
	}

	@Test
	public void getFieldsByIds() {
		List<Integer> ids = Arrays.asList(0, 1, 2, 3);
		Map<Integer, ArrayList<String>> fieldsMap = userDao.getFieldsByIds(ids);
		for (int id : ids) {
			List<String> fields = fieldsMap.get(id);
			logger.debug("id=" + id + ",fields=" + fields);
		}
	}

	@Test
	public void getUsersByIds() {
		List<Integer> ids = Arrays.asList(1, 2, 3);
		Map<Integer, User> userMap = userDao.getUsersByIds(ids);
		for (int id : ids) {
			User user = userMap.get(id);
			logger.debug("id=" + id + ",user=" + user);
		}
	}

	@Test
	public void getUsersBetweenTime() {
		long period = 60 * 60 * 1000;
		long now = System.currentTimeMillis();
		long start = now - period;
		List<User> users = userDao.getUsersBetweenTime(start, now);
		for (User user : users) {
			logger.debug(user);
		}
	}

	@Test
	public void getIdsBetweenTime() {
		long period = 5 * 60 * 60 * 1000;
		long now = System.currentTimeMillis();
		long start = now - period;
		List<Integer> res = userDao.getIdsBetweenTime(start, now);
		logger.debug("res=" + res);
	}

	@Test
	public void addUser() {
		User user = new User("test", 20, true);
		int res = userDao.addUser(user);
		logger.debug("res=" + res);

	}

	@Test
	public void updateUser() {
		int id = 9;
		User user = userDao.getUserById(id);
		if (user == null) {
			return;
		}
		user.setName("updateTest");
		user.setAge(77);
		int res = userDao.updateUser(user);
		logger.debug("res=" + res);
	}

	@Test
	public void deleteById() {
		int id = 6;
		int res = userDao.deleteById(id);
		logger.debug("res=" + res);

	}
}
