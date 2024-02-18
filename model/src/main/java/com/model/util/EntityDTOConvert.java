package com.model.util;

/**
 * 实现两个DTO之间属性值复制，同名且同类型的属性自动映射，其他属性必须通过转换器完成
 */
public class EntityDTOConvert {

    /**
     * 将源DTO的属性值复制给目标DTO对象
     * @param s 源DTO对象
     * @param t 目标DTO对象
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S,T> T convert(S s, T t) {
        EntityBeanCopier.copyProperties(s, t);
        return t;
    }
}
