package com.learn.sqlSession;

import com.learn.pojo.Configuration;
import com.learn.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;


    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }


    public <E> List<E> selectList(String statementId, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException {
        //完成simpleExecutor的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        MappedStatement mappedStatement = mappedStatementMap.get( statementId );
        List<Object> query = simpleExecutor.query( configuration, mappedStatement, params );
        return (List<E>) query;
    }

    public <T> T selectOne(String statementId, Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        List<Object> objects = selectList( statementId, params );
        if(objects.size() == 1){
            return (T)objects.get( 0 );
        }else{
            throw new RuntimeException( "查询结果为空或者返回结果过多！" );
        }
    }
}
