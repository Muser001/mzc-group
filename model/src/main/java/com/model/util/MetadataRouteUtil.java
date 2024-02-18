package com.model.util;

import com.model.annotation.RouteKey;
import com.model.dto.BaseInputDTO;
import com.model.enumtype.CommonRouteMapType;
import com.model.route.RouteInfo;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MetadataRouteUtil {

    private static Map<String,List<Field>> routeFields = new ConcurrentHashMap<>();

    /**
     * 根据dto获取路由要素信息
     * @param dto 必须是打了RouteKey注解的dto类实例，json对象不能扫描到信息
     * @return 路由结果信息
     */
    public static List<RouteInfo> getRouteInfo(BaseInputDTO dto) {
        List<RouteInfo> info = new ArrayList<>();
        for (Field field : getRouteField(dto)) {
            field.setAccessible(true);
            Object fieldObj = null;
            try {
                fieldObj = field.get(dto);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            RouteKey routeKey = field.getAnnotation(RouteKey.class);
            CommonRouteMapType type = routeKey.type();

            if(fieldObj == null) {
                if (CommonRouteMapType.CUST_NO == type) {
                    info.add(new RouteInfo(type,null));
                }else {
                    if(log.isDebugEnabled()) {
                        log.debug("字段为null，路由类型为【{}】不是直发到某特殊dus的情况，不处理路由key",type.name());
                    }
                }
                continue;
            }
            String value = field.toString();
            info.add(new RouteInfo(type,value));
        }
        return info;
    }

    /**
     * 根据dto返回路由字段
     * @param dto 输入dto
     * @return 打了路由字段的注解
     */
    public static List<Field> getRouteField(BaseInputDTO dto) {
        List<Field> list = routeFields.get(dto.getClass().getCanonicalName());
        if (list == null) {
            saveRouteFields(dto);
            list = routeFields.get(dto.getClass().getCanonicalName());
        }
        return list;
    }

    /**
     * 保存路由字段
     * @param dto 输入dto
     */
    public static void saveRouteFields(BaseInputDTO dto) {
        Field[] fields = dto.getClass().getDeclaredFields();
        List<Field> fs = new ArrayList<>();
        for (Field field : fields) {
            RouteKey routeKey = field.getAnnotation(RouteKey.class);
            if (routeKey != null) {
                fs.add(field);
            }
        }
        routeFields.put(dto.getClass().getCanonicalName(),fs);
    }
}
