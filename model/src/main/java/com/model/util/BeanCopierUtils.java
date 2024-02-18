package com.model.util;

import net.sf.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

public class BeanCopierUtils {

    public static void copy(Object value, Object target) {
        Assert.notNull(value,"源对象不能为空");
        Assert.notNull(target,"目标对象不能为空");
        BeanCopier copier = BeanCopier.create(value.getClass(), target.getClass(), false);
        copier.copy(value, target, null);
    }
}
