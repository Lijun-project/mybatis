package com.learn.pojo;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**  
 * @MethodName:
 * @Description: 创建两个javaBean：（容器对象）：存放的就是对配置文件解析出来的内容
 * configuration：核心配置类：存放sqlMapConfig.xml解析出来的内容
 * MappedStatement : 映射配置类：存放Mapper.xml解析出来的内容
 * @Param: 
 * @Return: 
 * @Author: lijun
 * @Date: 2020/3/28
**/
public class Configuration {

    private DataSource dateSource;

    /**
     * key:statementid  value:封装好的mappedStated对象
     */
    Map<String,MappedStatement> mappedStatementMap = new HashMap<String, MappedStatement>(  );

    public DataSource getDateSource(){
        return dateSource;
    }

    public void setDateSource(DataSource dateSource){
        this.dateSource = dateSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
