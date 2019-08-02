package com.example.api.util;

import com.example.api.Exception.MyException;

/**
 * CreateTime: 2019-08-01 18:14
 * ClassName: MyExceptionUtil
 * Package: com.example.api.utils
 * Describe:异常工具类
 *
 * @author XieZhiXin
 */
public class MyExceptionUtils {

    public MyExceptionUtils() {
    }

    public static MyException mxe(String msg, Throwable t, Object... params){
        return new MyException(StringUtils.format(msg, params),t);
    }

    public static MyException mxe(String msg, Object... params){
        return new MyException(StringUtils.format(msg, params));
    }

    public static MyException mxe(Throwable t){
        return new MyException(t);
    }

}
