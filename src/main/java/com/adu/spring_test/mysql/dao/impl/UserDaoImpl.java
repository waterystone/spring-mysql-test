package com.adu.spring_test.mysql.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adu.spring_test.mysql.dao.UserDao;
import com.adu.spring_test.mysql.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Log logger = LogFactory.getLog(this.getClass());

	public User getUserById(int id) {
		User user = null;

		final String querySQL = "SELECT id, name, age, sex, insert_time FROM user WHERE id = ?";
		final Object[] args = new Object[] { id };

		// 如果相应记录不存在，则会抛出异常异常"org.springframework.dao.EmptyResultDataAccessException: Incorrect result size: expected 1, actual 0"
		try {
			user = this.jdbcTemplate.queryForObject(querySQL, args,
					new UserRowMapper());
		} catch (DataAccessException e) {
			logger.debug("[NOT-EXISTS-USER]id=" + id);
		}

		return user;
	}

	public String getNameById(int id) {
		String res = null;
		final String querySQL = "SELECT name FROM user WHERE id = ?";
		final Object[] args = new Object[] { id };

		try {
			res = this.jdbcTemplate.queryForObject(querySQL, args,
					java.lang.String.class);
		} catch (DataAccessException e) {
			logger.debug("[NOT-EXISTS-USER]id=" + id);
		}
		return res;
	}

	public int getAgeById(int id) {
		int res = -1;
		final String querySQL = "SELECT age FROM user WHERE id = ?";
		final Object[] args = new Object[] { id };

		try {
			res = this.jdbcTemplate.queryForInt(querySQL, args);
		} catch (DataAccessException e) {
			logger.debug("[NOT-EXISTS-USER]id=" + id);
		}
		return res;
	}

	public List<String> getFieldsById(int id) {
		List<String> res = null;
		final String querySQL = "SELECT name, age, sex FROM user WHERE id = ?";
		final Object[] args = new Object[] { id };

		try {
			Map<String, Object> row = this.jdbcTemplate.queryForMap(querySQL,
					args);
			res = new ArrayList<String>();

			String name = row.get("name").toString();
			String age = row.get("age").toString();
			String sex = row.get("sex").toString();

			res.add(name);
			res.add(age);
			res.add(sex);
		} catch (DataAccessException e) {
			logger.debug("[NOT-EXISTS-USER]id=" + id);
		}
		return res;
	}

	public Map<Integer, String> getNamesByIds(List<Integer> ids) {
		Map<Integer, String> res = new HashMap<Integer, String>();
		if (ids == null) {
			return res;
		}

		// SQL语句模板
		final String formatSQL = "SELECT id, name FROM user WHERE id in (%s)";
		final int LIMIT = 1000;// oracle每次最多只能查询1000个,所以需要分批去查询

		int index = 0;// index表示每批次的起始索引
		while (index < ids.size()) {
			// 将所有id拼接在一起，方便数据库批量查询
			StringBuffer idsSQL = new StringBuffer();
			for (int i = index; i < index + LIMIT && i < ids.size(); i++) {
				idsSQL.append(ids.get(i) + ",");
			}
			// 去掉最后的那个逗号
			idsSQL.setLength(idsSQL.length() - 1);
			final String querySQL = String.format(formatSQL, idsSQL.toString());

			List<Map<String, Object>> rows = this.jdbcTemplate
					.queryForList(querySQL);

			// 对查询到的每条记录进行解析
			for (Map<String, Object> row : rows) {
				int id = (Integer) row.get("id");
				String name = (String) row.get("name");
				res.put(id, name);
			}

			logger.debug("select count:"
					+ (index + LIMIT > ids.size() ? index + ids.size() : index
							+ LIMIT) + "/" + ids.size());
			index += LIMIT;
		}
		return res;
	}

	public Map<Integer, ArrayList<String>> getFieldsByIds(List<Integer> ids) {
		Map<Integer, ArrayList<String>> res = new HashMap<Integer, ArrayList<String>>();
		if (ids == null) {
			return res;
		}

		// SQL语句模板
		final String formatSQL = "SELECT id, name, age, sex FROM user WHERE id in (%s)";
		final int LIMIT = 1000;// oracle每次最多只能查询1000个,所以需要分批去查询

		int index = 0;// index表示每批次的起始索引
		while (index < ids.size()) {
			// 将所有id拼接在一起，方便数据库批量查询
			StringBuffer idsSQL = new StringBuffer();
			for (int i = index; i < index + LIMIT && i < ids.size(); i++) {
				idsSQL.append(ids.get(i) + ",");
			}
			// 去掉最后的那个逗号
			idsSQL.setLength(idsSQL.length() - 1);
			final String querySQL = String.format(formatSQL, idsSQL.toString());

			List<Map<String, Object>> rows = this.jdbcTemplate
					.queryForList(querySQL);

			// 对查询到的每条记录进行解析
			for (Map<String, Object> row : rows) {
				ArrayList<String> fields = new ArrayList<String>();

				int id = (Integer) row.get("id");
				String name = row.get("name").toString();
				String age = row.get("age").toString();
				String sex = row.get("sex").toString();

				fields.add(name);
				fields.add(age);
				fields.add(sex);
				res.put(id, fields);
			}

			logger.debug("select count:"
					+ (index + LIMIT > ids.size() ? index + ids.size() : index
							+ LIMIT) + "/" + ids.size());
			index += LIMIT;
		}
		return res;
	}

	public Map<Integer, User> getUsersByIds(List<Integer> ids) {
		Map<Integer, User> res = new HashMap<Integer, User>();
		if (ids == null) {
			return res;
		}

		// SQL语句模板
		final String formatSQL = "SELECT id, name, age, sex, insert_time FROM user WHERE id in (%s)";
		final int LIMIT = 1000;// oracle每次最多只能查询1000个,所以需要分批去查询

		int index = 0;// index表示每批次的起始索引
		while (index < ids.size()) {
			// 将所有id拼接在一起，方便数据库批量查询
			StringBuffer idsSQL = new StringBuffer();
			for (int i = index; i < index + LIMIT && i < ids.size(); i++) {
				idsSQL.append(ids.get(i) + ",");
			}
			// 去掉最后的那个逗号
			idsSQL.setLength(idsSQL.length() - 1);
			final String querySQL = String.format(formatSQL, idsSQL.toString());

			List<User> users = this.jdbcTemplate.query(querySQL,
					new UserRowMapper());
			for (User user : users) {
				res.put(user.getId(), user);
			}

			logger.debug("select count:"
					+ (index + LIMIT > ids.size() ? index + ids.size() : index
							+ LIMIT) + "/" + ids.size());
			index += LIMIT;
		}
		return res;
	}

	public List<User> getUsersBetweenTime(long start, long end) {
		final String querySQL = "SELECT id, name, age, sex, insert_time FROM user WHERE insert_time between ? and ?";
		final Object[] args = new Object[] { new Timestamp(start),
				new Timestamp(end) };

		return this.jdbcTemplate.query(querySQL, args, new UserRowMapper());
	}

	public List<Integer> getIdsBetweenTime(long start, long end) {
		List<Integer> res = new ArrayList<Integer>();
		final String querySQL = "SELECT id FROM user WHERE insert_time between ? and ?";
		final Object[] args = new Object[] { new Timestamp(start),
				new Timestamp(end) };

		List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(
				querySQL, args);

		// 对查询到的每条记录进行解析
		for (Map<String, Object> row : rows) {
			int id = (Integer) row.get("id");
			res.add(id);
		}
		return res;
	}

	public int addUser(User user) {
		final String sql = "INSERT INTO user (name,age,sex) VALUES (?,?,?)";
		final Object[] args = new Object[] { user.getName(), user.getAge(),
				user.isMale() };
		return this.jdbcTemplate.update(sql, args);
	}

	public int updateUser(User user) {
		final String sql = "UPDATE user SET name = ?, age=?, sex=? WHERE id = ?";
		final Object[] args = new Object[] { user.getName(), user.getAge(),
				user.isMale(), user.getId() };
		return this.jdbcTemplate.update(sql, args);
	}

	public int deleteById(int id) {
		final String sql = "DELETE FROM user WHERE id = ?";
		final Object[] args = new Object[] { id };
		return this.jdbcTemplate.update(sql, args);
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
