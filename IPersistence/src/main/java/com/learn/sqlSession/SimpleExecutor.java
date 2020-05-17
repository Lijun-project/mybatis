package com.learn.sqlSession;

import com.learn.config.BoundSql;
import com.learn.pojo.Configuration;
import com.learn.pojo.MappedStatement;
import com.learn.utils.GenericTokenParser;
import com.learn.utils.ParameterMapping;
import com.learn.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params)
            throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException,
            IntrospectionException, InstantiationException, InvocationTargetException {
        //注册驱动，获取链接
        Connection connection  = configuration.getDateSource().getConnection();

        //获取sql 解析#{} 或者 ${}
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement( boundSql.getSqlText() );


        //参数设置
        String paramterType = mappedStatement.getParamterType();
        String resultType = mappedStatement.getResultType();
        //获取参数的全路径
        Class<?> paramterTypeClass = getClass(paramterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //反射，根据参数名称获取实体对象重的属性值
            Field declaredField = paramterTypeClass.getDeclaredField( content );
            // 暴力访问
            declaredField.setAccessible( true );
            Object o = declaredField.get( params[0] );
            preparedStatement.setObject( i+1,o );
        }
        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        //封装返回结果集
        ArrayList<Object> objects = new ArrayList<Object>();
        Class<?> resultTypeClass = getClass(resultType);

        while(resultSet.next()){
            Object resultTypeObject = resultTypeClass.newInstance();
            //元数据
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                //获取字段名
                String columnName = resultSetMetaData.getColumnName( i );
                //获取字段值
                Object object = resultSet.getObject( columnName );
                //使用反射，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor( columnName, resultTypeClass );
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(resultTypeObject,object);

            }
            objects.add(resultTypeObject);
        }

        return (List<E>) objects;
    }

    private Class<?> getClass(String paramterType) throws ClassNotFoundException {
        if(paramterType != null){
            Class<?> aClass = Class.forName( paramterType );
            return aClass;
        }
        return null;

    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser( "#{", "}", parameterMappingTokenHandler );

        String parse = genericTokenParser.parse( sql );
        List<ParameterMapping> parameterMapping = parameterMappingTokenHandler.getParameterMapping();
        BoundSql boundSql = new BoundSql(parse,parameterMapping);
        boundSql.setSqlText( parse );
        return boundSql;
    }
}
