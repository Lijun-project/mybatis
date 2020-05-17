package com.learn.sqlSession;

import com.learn.pojo.Configuration;
import com.learn.pojo.MappedStatement;

import java.util.Map;

public class DefaultSqlSessionFactory implements SqlSessionFactionary {

    private Configuration  configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {


        return new DefaultSqlSession(configuration);
    }
}
