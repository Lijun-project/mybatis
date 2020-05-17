package com.learn.utils;

import java.util.ArrayList;
import java.util.List;

public class ParameterMappingTokenHandler implements TokenHandler {

    private List<ParameterMapping> parameterMapping = new ArrayList<ParameterMapping>(  );


    private ParameterMapping buildParameterMapping(String content){
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return  parameterMapping;
    }

    public List<ParameterMapping> getParameterMapping() {
        return parameterMapping;
    }

    public void setParameterMapping(List<ParameterMapping> parameterMapping) {
        this.parameterMapping = parameterMapping;
    }

    @Override
    public String handlerToken(String content) {
        parameterMapping.add(buildParameterMapping(content));
        return "?";
    }
}
