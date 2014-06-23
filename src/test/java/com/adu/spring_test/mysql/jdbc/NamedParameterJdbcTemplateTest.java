package com.adu.spring_test.mysql.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class NamedParameterJdbcTemplateTest {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private Log logger = LogFactory.getLog(this.getClass());

	@Test
	public void test() {
		final String sql = "select * from user where id = :id and age=:age";
		final int id = 1;
		final int age = 26;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("age", age);
		Map<String, Object> res = namedParameterJdbcTemplate.queryForMap(sql,
				paramMap);
		logger.debug("res=" + res);

	}

	@Test
	public void test1() {
		final String sql = "select * from user where id = :id and age=:age";
		final int id = 1;
		final int age = 26;

		MapSqlParameterSource paramSource = new MapSqlParameterSource("id", id)
				.addValue("age", age);
		Map<String, Object> res = namedParameterJdbcTemplate.queryForMap(sql,
				paramSource);
		logger.debug("res=" + res);

	}

}
