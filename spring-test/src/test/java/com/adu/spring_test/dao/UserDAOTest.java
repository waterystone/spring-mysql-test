package com.adu.spring_test.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adu.spring_test.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDAOTest {
	@Autowired
	private UserDAO userDAO;

	private Log logger = LogFactory.getLog(this.getClass());

	@Test
	public void getUserById() {
		int id = 0;
		User user = userDAO.getUserById(id);
		logger.debug("user=" + user);

	}
}
