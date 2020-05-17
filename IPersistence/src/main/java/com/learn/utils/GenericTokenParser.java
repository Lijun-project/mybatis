package com.learn.utils;

public class GenericTokenParser {

    private final String openToken;//开始标记

    private final String closeToken;//结束标记

    private final TokenHandler handler;//标记处理器

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    /**
     * 解析#{} 和 ${}
     * @param text
     * @return
     */
    public String parse(String text){
        if(text ==null || text.isEmpty()){
            return "";
        }
        int start = text.indexOf( openToken,0 );
        if(start ==-1){
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        final  StringBuilder stringBuilder = new StringBuilder(  );
        StringBuilder sb = null;
        while (start > -1){
            //判断如果开始标记前有转义字符，就不作为openToken进行处理，否则继续处理
            if(start >0 && src[start-1] =='\\'){
                stringBuilder.append( src,offset,start-offset-1 ).append( openToken );
                offset = start + openToken.length();
            }else{
                //重置sb变量，避免空指针或者老数据干扰
                if(sb == null){
                    sb = new StringBuilder(  );
                }else{
                    sb.setLength( 0 );
                }
                stringBuilder.append( src,offset,start-offset );
                offset = start + openToken.length();
                int end = text.indexOf( closeToken,offset );
                while (end > -1){//存在结束标记
                    if(end>offset && src[end - 1] == '\\'){
                        sb.append( src,offset,end-offset-1 ).append( closeToken );
                        offset = end + closeToken.length();
                        end = text.indexOf( closeToken,offset );
                    }else{
                        sb.append( src,offset,end - offset );
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if(end == -1){
                    stringBuilder.append( src,start,src.length - start );
                    offset = src.length;
                }else{
                    stringBuilder.append( handler.handlerToken(sb.toString()) );
                    offset = end + closeToken.length();
                }
            }
            start = text.indexOf( openToken,offset );

        }
        if(offset < src.length){
            stringBuilder.append( src,offset,src.length - offset );
        }
        return stringBuilder.toString();
    }

}
