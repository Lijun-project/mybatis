package com.learn.config;

import com.learn.io.Resources;
import com.learn.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XmlConfigBuilder {

    private Configuration configuration;
    public XmlConfigBuilder(){
        this.configuration = new Configuration();
    }

    /**
     * @MethodName: parseConfig
     * @Description: 使用dom4j对配置文件解析
     * @Param: [in]
     * @Return: com.learn.pojo.Configuration
     * @Author: lijun
     * @Date: 2020/3/30
    **/
    public Configuration parseConfig(InputStream in) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read( in );//对sqlMapper.xml进行解析
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes( "//property" );
        Properties properties = new Properties(  );
        for (Element element : list) {
            String name = element.attributeValue( "name" );
            String value = element.attributeValue( "value" );
            properties.setProperty( name,value );
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass( properties.getProperty( "driverClass" ) );
        comboPooledDataSource.setJdbcUrl( properties.getProperty( "jdbcUrl" ));
        comboPooledDataSource.setUser( properties.getProperty( "username" ) );
        comboPooledDataSource.setPassword( properties.getProperty( "password" ) );


        configuration.setDateSource( comboPooledDataSource);

        //xml配置文件解析
        List<Element> mapperList = rootElement.selectNodes( "//mapper" );
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue( "resource" );
            InputStream resourceAsSteam = Resources.getResourceAsStream( mapperPath );
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse( resourceAsSteam );

        }


        return configuration;
    }
}
