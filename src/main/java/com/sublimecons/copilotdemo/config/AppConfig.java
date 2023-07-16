package com.sublimecons.copilotdemo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jp.co.future.uroborosql.SqlAgentFactoryImpl;
import jp.co.future.uroborosql.UroboroSQL;
import jp.co.future.uroborosql.config.SqlConfig;
import jp.co.future.uroborosql.enums.InsertsType;
import jp.co.future.uroborosql.filter.DebugSqlFilter;
import jp.co.future.uroborosql.store.SqlManagerImpl;
import jp.co.future.uroborosql.utils.CaseFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {


    public static final int FETCH_SIZE = 3000;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource ds = new HikariDataSource(config);
//        ds.setUsername(username);
//        ds.setPassword(password);
//        ds.setDriverClassName(driverClassName);
        return ds;
    }
    @Bean
    public SqlConfig sqlConfig(DataSource dataSource) {
        SqlConfig sqlConfig = UroboroSQL.builder(dataSource)
                .setSqlAgentFactory(new SqlAgentFactoryImpl()
                        .setDefaultMapKeyCaseFormat(CaseFormat.CAMEL_CASE)
                        .setFetchSize(FETCH_SIZE)
                        .setDefaultInsertsType(InsertsType.BULK))
                .setSqlManager(new SqlManagerImpl(false))
                .build();
        sqlConfig.getSqlFilterManager().addSqlFilter(new DebugSqlFilter());
        sqlConfig.getSqlFilterManager().initialize();
        return sqlConfig;

    }

}
