package com.learn.sqlSession;

import com.learn.config.XmlConfigBuilder;
import com.learn.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactionaryBuilder {

    public SqlSessionFactionary build(InputStream in) throws DocumentException, PropertyVetoException {
        //dom4j解析配置文件
        XmlConfigBuilder xcb = new XmlConfigBuilder();
        Configuration configuration = xcb.parseConfig( in );
        //创建sqlSessionFactory对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory( configuration );
        return defaultSqlSessionFactory;
    }
}
