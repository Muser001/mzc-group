package com.model.util;

import com.model.annotation.FlowBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

    public static Object methodInvoke(Object target, String methodName, @Nullable Object... params){
        List<Class<?>> parameterTypes = getParamsType(params);
        Method method = ReflectionUtils.findMethod(target.getClass(), methodName,parameterTypes.toArray(new Class<?>[0]));
        Object ret = null;
        if(method == null){
            throw new RuntimeException("方法节点定义的方法[" + methodName + "]不存在");
        }
        if(!method.isAnnotationPresent(FlowBean.class)){
            throw new RuntimeException("方法[" + methodName + "]缺少@FlowBean注解");
        }
        if(Modifier.isStatic(method.getModifiers())){
            ret = ReflectionUtils.invokeMethod(method,null,params);
        }else{
            ret = ReflectionUtils.invokeMethod(method,target,params);
        }
        return ret;
    }

    public static List<Class<?>> getParamsType(@Nullable Object... params){
        List<Class<?>> parameterTypes = new ArrayList<>();
        if(params != null && params.length > 0){
            for(Object o : params){
                parameterTypes.add(o.getClass());
            }
        }
        return parameterTypes;
    }

    public static <E> E newInstance(String className, Class<E> expected, Object... params) {
        Class<?> clazz = forName(className);
        return newInstance(clazz, expected, params);
    }

    public static Class<?> forName(String name) {
        try {
            return ClassUtils.forName(name, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E> E newInstance(Class<?> clazz, Class<E> expected, Object... params) {
        if (expected != null && !expected.isAssignableFrom(clazz)) {
            throw new RuntimeException("没想好");
        }
        if(params == null || params.length == 0) {
            try {
                return (E) clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            List<Class<?>>  parameterTypes = getParamsType(params);
            try {
                return (E) clazz.getConstructor(parameterTypes.toArray(new Class<?>[0])).newInstance(params);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
