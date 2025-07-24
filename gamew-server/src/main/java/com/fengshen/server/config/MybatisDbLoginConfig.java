package com.fengshen.server.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = {"com.fengshen.db.auth"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisDbLoginConfig {

	
	@Value("${spring.datasource.login.username}")
	private String username;
	@Value("${spring.datasource.login.password}")
	private String password;
	@Value("${spring.datasource.login.url}")
	private String url;
	@Value("${spring.datasource.login.driverClassName}")
	private String driverClassName;
	@Value("${spring.datasource.login.initialSize}")	
	private int initialSize;
	@Value("${spring.datasource.login.minIdle}")
	private int minIdle;
	@Value("${spring.datasource.login.maxActive}")
	private int maxActive;
	@Value("${spring.datasource.login.maxWait}")
	private int maxWait;
	@Value("${spring.datasource.login.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.login.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${spring.datasource.login.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.login.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.login.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${spring.datasource.login.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	@Value("${spring.datasource.login.filters}")
	private String filters;
	@Value("${spring.datasource.login.connectionProperties}")
	private String connectionProperties;

    @Bean(name = "loginDataSource")
    public DataSource loginDataSource() {
    	DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(this.url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);

		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
		}
		datasource.setConnectionProperties(connectionProperties);
        return datasource;
    }
	
	
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(loginDataSource());
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }
    
    @Bean(name="dgLoginTransaction")
	public PlatformTransactionManager primaryTransactionManager() {
	    return new DataSourceTransactionManager(loginDataSource());
	}
}