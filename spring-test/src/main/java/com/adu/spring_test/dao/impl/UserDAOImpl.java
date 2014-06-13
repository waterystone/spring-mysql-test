package com.adu.spring_test.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.adu.spring_test.dao.UserDAO;
import com.adu.spring_test.model.User;

public class UserDAOImpl implements UserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Log logger = LogFactory.getLog(this.getClass());

	public User getUserById(int id) {
		final String sql = "SELECT id, name, age, sex, insert_time FROM user WHERE id = ?";
		final Object[] args = new Object[] { id };

		User user = null;
		// 如果相应记录不存在，则会抛出异常异常"org.springframework.dao.EmptyResultDataAccessException: Incorrect result size: expected 1, actual 0"
		try {
			user = this.jdbcTemplate.queryForObject(sql, args,
					new UserRowMapper());
		} catch (DataAccessException e) {
			logger.debug("[NOT-EXISTS-USER]id=" + id, e);
		}

		return user;
	}

	public boolean addUser(User user) {
		return false;
	}

	public boolean deleteById(int id) {
		return false;
	}

	private final class UserRowMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setAge(rs.getInt("age"));
			user.setMale(rs.getBoolean("sex"));
			user.setInsertTime(rs.getTimestamp("insert_time").getTime());
			return user;
		}

	}
}
