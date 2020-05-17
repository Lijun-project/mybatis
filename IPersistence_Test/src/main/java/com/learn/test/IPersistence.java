package com.learn.test;

import com.learn.entity.User;
import com.learn.io.Resources;
import com.learn.sqlSession.SqlSession;
import com.learn.sqlSession.SqlSessionFactionary;
import com.learn.sqlSession.SqlSessionFactionaryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPersistence {
//    1 加载配置文件的路径，将配置文件加载成字节输入流，存储在内存中
    @Test
    public void test() throws DocumentException, PropertyVetoException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException {
        InputStream resourceAsSteam = Resources.getResourceAsStream( "sqlMapConfig.xml" );
        SqlSessionFactionary sqlSessionFactionary = new SqlSessionFactionaryBuilder().build( resourceAsSteam );
        SqlSession sqlSession = sqlSessionFactionary.openSession();

//        List<Object> list = sqlSession.selectList( "user.selectUserList", null );

        User user = new User();
        user.setName( "张三" );
        User userInfo = sqlSession.selectOne( "user.selectUserInfo", user );
        System.out.println(userInfo.getId());
        System.out.println(userInfo.getName());
        System.out.println(userInfo.getAddress());

    }

}
