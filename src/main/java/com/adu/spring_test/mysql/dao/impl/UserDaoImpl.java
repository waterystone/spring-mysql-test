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
			StringBuffer idsBuffer = new StringBuffer();
			index = getIdsFrom(ids, index, LIMIT, idsBuffer);

			final String querySQL = String.format(formatSQL,
					idsBuffer.toString());

			List<Map<String, Object>> rows = this.jdbcTemplate
					.queryForList(querySQL);

			// 对查询到的每条记录进行解析
			for (Map<String, Object> row : rows) {
				int id = (Integer) row.get("id");
				String name = (String) row.get("name");
				res.put(id, name);
			}

			logger.debug("select count:" + index + "/" + ids.size());
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
			StringBuffer idsBuffer = new StringBuffer();
			index = getIdsFrom(ids, index, LIMIT, idsBuffer);

			final String querySQL = String.format(formatSQL,
					idsBuffer.toString());

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

			logger.debug("select count:" + index + "/" + ids.size());
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
			StringBuffer idsBuffer = new StringBuffer();
			index = getIdsFrom(ids, index, LIMIT, idsBuffer);

			final String querySQL = String.format(formatSQL,
					idsBuffer.toString());

			List<User> users = this.jdbcTemplate.query(querySQL,
					new UserRowMapper());
			for (User user : users) {
				res.put(user.getId(), user);
			}

			logger.debug("select count:" + index + "/" + ids.size());
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
		int ret = 0;
		final String sql = "INSERT INTO user (name,age,sex) VALUES (?,?,?)";
		try {
			final Object[] args = new Object[] { user.getName(), user.getAge(),
					user.isMale() };
			ret = this.jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			logger.error("[ERROR-addUser]user=" + user, e);
		}
		return ret;
	}

	public void addUsers(final List<User> users) {
		if (users == null || users.size() == 0) {
			return;
		}

		try {
			// SQL语句模板
			String format = "INSERT IGNORE INTO user (name, age, sex) VALUES %s";

			int index = 0;
			final int MAX_LIMIT = 1000;

			// 分批次写
			while (index < users.size()) {
				StringBuffer values = new StringBuffer();
				for (int i = index; i < index + MAX_LIMIT && i < users.size(); i++) {
					User user = users.get(i);
					// 转换成字符串批量插入
					values.append(String.format("('%s', %d, %d),",
							user.getName(), user.getAge(), user.isMale()));
				}

				// 去掉末尾的逗号
				values.setLength(values.length() - 1);

				String updateSql = String.format(format, values);

				this.jdbcTemplate.update(updateSql);
				index += MAX_LIMIT;
			}
		} catch (Exception e) {
			logger.error("[ERROR-addUsers]");
		}
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

	/**
	 * 截取子集，以便批量查询而又不至于超出MYSQL的限制。
	 * 
	 * @param list
	 *            全集
	 * @param index
	 *            开始下标
	 * @param limit
	 *            个数
	 * @param idsBuffer
	 *            结果子集
	 * @return 下一个id的下标，子集写入buffer里。
	 */
	private <T> int getIdsFrom(List<T> list, int index, int limit,
			StringBuffer idsBuffer) {
		int res = index;
		if (index >= list.size()) {
			return res;
		}
		// 将所有id拼接在一起，方便数据库批量查询
		for (int i = index; i < index + limit && i < list.size(); i++) {
			idsBuffer.append(list.get(i) + ",");
			res++;
		}
		// 去掉最后的那个逗号
		idsBuffer.setLength(idsBuffer.length() - 1);
		return res;
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
