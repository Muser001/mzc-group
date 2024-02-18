package com.model.util;


import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用BeanCopier实现两个DTO之间同名属性值复制
 */
public class EntityBeanCopier {
    //BeanCopier缓存对象：key=类名拼接
    private static final Map<String, BeanCopier> cache = new ConcurrentHashMap<>();

    /**
     * 根据目标DTO的类型创建新对象，将源DTO的属性复制给新对象，并返回
     * @param s 源DTO对象
     * @param targetClass 目标DTO类型
     * @return 目标DTO对象
     * @param <S>
     * @param <T>
     */
    public static <S,T> T copyProperties(S s, Class<T> targetClass) {
        T instance = null;
        try {
            instance = ClassUtil.newInstance(targetClass, targetClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        copyProperties(s, instance);
        return instance;
    }

    /**
     * 将源DTO的属性值赋值给目标DTO对象
     * @param s 源DTO对象
     * @param t 目标DTO对象
     * @param <S>
     * @param <T>
     */
    public static <S, T> void copyProperties(S s, T t) {
        if (s == null || t == null) {
            return;
        }
        String key = generateKey(s.getClass(), t.getClass());
        BeanCopier copier = getCopier(key, s, t);
        copier.copy(s, t, null);
    }

    /**
     * 根据不同DTO类型生成key值
     * @param c1 源DTO类型
     * @param c2 目标DTO类型
     * @return 生成key值
     */
    private static String generateKey(Class<?> c1, Class<?> c2) {
        return c1.toString() + c2.toString();
    }

    /**
     * 根据key将源DTO的属性值复制给目标DTO对象
     * @param key 类名拼接
     * @param s 源DTO对象
     * @param t 目标DTO对象
     * @return 将源DTO的属性值赋值给目标DTO对象
     * @param <S>
     * @param <T>
     */
    private static <S, T> BeanCopier getCopier(String key, S s, T t) {
        if (!cache.containsKey(key)) {
            cache.put(key, BeanCopier.create(s.getClass(), t.getClass(), false));
        }
        return cache.get(key);
    }
}
