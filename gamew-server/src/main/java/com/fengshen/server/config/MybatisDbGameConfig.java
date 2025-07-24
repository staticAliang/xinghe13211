package com.fengshen.server.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.WebStatFilter;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = {"com.fengshen.db.dao"}, sqlSessionFactoryRef = "sqlSessionFactoryGame")
public class MybatisDbGameConfig {

	
	@Value("${spring.datasource.game.username}")
	private String username;
	@Value("${spring.datasource.game.password}")
	private String password;
	@Value("${spring.datasource.game.url}")
	private String url;
	@Value("${spring.datasource.game.driverClassName}")
	private String driverClassName;
	@Value("${spring.datasource.game.initialSize}")
	private int initialSize;
	@Value("${spring.datasource.game.minIdle}")
	private int minIdle;
	@Value("${spring.datasource.game.maxActive}")
	private int maxActive;
	@Value("${spring.datasource.game.maxWait}")
	private int maxWait;
	@Value("${spring.datasource.game.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.game.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${spring.datasource.game.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.game.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.game.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${spring.datasource.game.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	@Value("${spring.datasource.game.filters}")
	private String filters;
	@Value("${spring.datasource.game.connectionProperties}")
	private String connectionProperties;

    @Bean(name = "gameDataSource")
    public DataSource gameDataSource() {
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
    
//    @Bean
//	public ServletRegistrationBean<StatViewServlet> druidServlet() {
//		ServletRegistrationBean<StatViewServlet> reg = new ServletRegistrationBean<StatViewServlet>();
//		reg.setServlet(new StatViewServlet());
//		reg.addUrlMappings("/druid/*");
//		//reg.addInitParameter("allow", "127.0.0.1,localhost,192.168.0.254,"); // 白名单
//		reg.addInitParameter("deny", ""); 
//		reg.addInitParameter("loginUsername", "penglianwei");// 查看监控的用户名
//		reg.addInitParameter("loginPassword", "pengwei168");// 密码
//		return reg;
//	}
    
    @Bean
	public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}
    @Bean
    public SqlSessionFactory sqlSessionFactoryGame() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(gameDataSource());
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateGame() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryGame());
        return template;
    }
    
    @Bean(name="dgGameTransaction")
	public PlatformTransactionManager primaryTransactionManager() {
	    return new DataSourceTransactionManager(gameDataSource());
	}
 
}
