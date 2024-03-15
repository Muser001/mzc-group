package com.model.route;

import com.model.context.ServiceContextHandler;
import com.model.custinfo.CustInfoBean;
import com.model.process.properties.CommonServiceProties;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("serviceRoute")
public class ServiceRouteImpl implements ServiceRoute{

    /**
     * 联机引擎公共配置属性
     */
    @Autowired
    private CommonServiceProties commonServiceProties;

    /**
     * 主映射要素类型长度
     */
    @Autowired
    private static final int MAP_ELEMENT_TYPE_LENGTH = 2;


    /**
     * 路由到CDUS
     * @return
     */
    @Override
    public CustInfoBean routeCDus() {
        CustInfoBean pbsInfo = new CustInfoBean();
        //pbsInfo.setDbId(commonServiceProties.getCDusNo());
        return pbsInfo;
    }

    /**
     * 路由到当前DUS
     * @param mainMapElementInfo 主路由要素
     * @return
     */
    @Override
    public CustInfoBean routeCurrentDus(String mainMapElementInfo) {
        CustInfoBean pbsInfo = new CustInfoBean();
        pbsInfo.setDusId(ServiceContextHandler.getDusId());
        pbsInfo.setShardingId(ServiceContextHandler.getShardingId());
        pbsInfo.setDbId(ServiceContextHandler.getDbId());
        pbsInfo.setCusId(ServiceContextHandler.getCusId());
        pbsInfo.setMappingCusId(ServiceContextHandler.getMappingCusId());
        pbsInfo.setTableId(ServiceContextHandler.getTableId());
        if(StringUtil.isNullOrEmpty(mainMapElementInfo) &&
                mainMapElementInfo.length() > MAP_ELEMENT_TYPE_LENGTH) {
            pbsInfo.setRouteType(mainMapElementInfo.substring(0,MAP_ELEMENT_TYPE_LENGTH));
            pbsInfo.setRouteKey(mainMapElementInfo.substring(MAP_ELEMENT_TYPE_LENGTH));
        }
        return pbsInfo;
    }

    /**
     * 根据PBS的DTO获取到的路由要素查询客户信息，此方法要么返回非空路由结果，要么抛出异常
     * @param infos 路由要素信息
     * @param mainMapElementInfo 主路由要素信息
     * @param multyDeploy 同时在BDUS和CDUS部署
     * @return
     */
    @Override
    public CustInfoBean routeTargetDus(List<RouteInfo> infos, String mainMapElementInfo, boolean multyDeploy) {
        // 路由要素一个都不配，路由到当前DUS
        if(infos == null || infos.isEmpty()) {
            return routeCurrentDus(mainMapElementInfo);
        }

        // 配置了路由要素，则遍历路由要素获取路由结果
        for (RouteInfo info : infos) {
            String cacheKey = info.getType().getValue() + info.getValue();
            RouteResult routeResult = (RouteResult) ServiceContextHandler.getPrivateContextInfo(cacheKey);

            //PCS上下文缓存中有，直接返回
            if (routeResult != null) {
                return routeResultToBean(routeResult);
            }

            //调用路由服务获取路由结果，在路由要素非CDUS且路由要素为空时，会返回null
            try {
                routeResult = routeByRouteKey(info);
            } catch (Exception e) {
                if (multyDeploy && commonServiceProties.isRouteCdusOnMissingRouteValue()) {
                    return routeCDus();
                }else {
                    throw new RuntimeException(e);
                }
            }

            // 没有查到路由信息，则查下一个
            if (routeResult == null) {
                continue;
            }

            //查到路由信息，查到迁移状态和锁定状态
        }
        return null;
    }

    /**
     * 根据路由要素路由：1.路由要素类型为CDUS，直接路由到CDUS；2.路由要素值为空，直接返回null；3.路由
     * 要素不空，调用路由要素；可能不存在业务异常时，数据库异常，直接抛
     * @param info
     * @return
     */
    private RouteResult routeByRouteKey(RouteInfo info) {
        if ("2".equals(info.getType().getValue())) {
            RouteResult routeResult = new RouteResult();
            routeResult.setMigratedStatus("1");
            routeResult.setMapKeyType(info.getType().getValue());
            routeResult.setMapKeyValue(info.getValue());
            //routeResult.setDusCode(commonServiceProties.getCDusNo());
            routeResult.setShardingTime("");
            if (log.isDebugEnabled()) {
                log.debug("根据路由要素:{} 获取目标路由：{}",info, routeResult);
            }
            return routeResult;
        }

        // 考虑要素为空的时候
        if (StringUtil.isNullOrEmpty(info.getValue())) {
            if (log.isDebugEnabled()) {
                log.debug("当前字段配置的路由类型【{}】，但从DTO中未解析到对应的路由要素值，跳过调用路由服务",info.getType().getValue());
            }
            return null;
        }

        return null;
    }

    /**
     * 把全局路由服务返回的客户信息对象映射成服务引擎自定义的客户信息对象
     * @param routeResult 目标客户信息
     * @return
     */
    private CustInfoBean routeResultToBean(RouteResult routeResult) {
        CustInfoBean retBean = new CustInfoBean();
//        retBean.setDusId(routeResult.getDusId());
//        retBean.setShardingId(routeResult.getShardingId());
//        retBean.setDbId(routeResult.getDbId());
//        retBean.setCusId(routeResult.getCusId());
//        retBean.setMappingCusId(routeResult.getMappingCusId());
//        retBean.setTableId(routeResult.getTableId());
//        retBean.setRouteType(routeResult.getRouteType());
//        retBean.setRouteKey(routeResult.getRouteKey());
        return retBean;
    }
}
