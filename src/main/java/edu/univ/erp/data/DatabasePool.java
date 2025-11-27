package edu.univ.erp.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.lang.module.Configuration;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabasePool {
    private static HikariDataSource authdbpool;
    private static HikariDataSource erpdbpool;

    static{
        HikariConfig config1=new HikariConfig();
        config1.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/authdb");
        config1.setUsername("root");
        config1.setPassword("1234");
        config1.setMaximumPoolSize(10);
        config1.setMinimumIdle(2);
        authdbpool=new HikariDataSource(config1);

        HikariConfig config2=new HikariConfig();
        config2.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/erpdb");
        config2.setUsername("root");
        config2.setPassword("1234");
        config2.setMaximumPoolSize(10);
        config2.setMinimumIdle(2);
        erpdbpool=new HikariDataSource(config2);
    }

    public static Connection getauthdbConnection() throws SQLException {
        return authdbpool.getConnection();
    }

    public static Connection geterpdbConnection() throws SQLException{
        return erpdbpool.getConnection();
    }
}
