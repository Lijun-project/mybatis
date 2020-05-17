package com.learn.config;

import com.learn.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

public class BoundSql {



    private List<ParameterMapping> parameterMappingList = new ArrayList<ParameterMapping>(  );

    private  String sqlText;


    public BoundSql( String sqlText,List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }



}
