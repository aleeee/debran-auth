package et.debran.debranauth.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DebranDataSourceConfig {
	
	@Value("${debran.datasource.url}")
	private String dbUrl;
	@Value("${debran.datasource.username}")
	private String dbUserName;
	@Value("${debran.datasource.password}")
	private String dbPass;
	@Value("${debran.datasource.driver}")
	private String dbDriver;
	
	@Bean
	public DataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setMaximumPoolSize(100);
		ds.setDataSourceClassName(dbDriver);
		ds.addDataSourceProperty("url", dbUrl);
		ds.addDataSourceProperty("user", dbUserName);
		ds.addDataSourceProperty("password", dbPass);
//		ds.addDataSourceProperty("cachePrepStmts", true);
//		ds.addDataSourceProperty("prepStmtCacheSize", 250);
//		ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
//		ds.addDataSourceProperty("useServerPrepStmts", true);
		return ds;
	}
}
