package com.model.route;

import com.model.custinfo.CustInfoBean;

import java.util.List;

public interface ServiceRoute {

    /**
     * 路由到CDus
     * @return 路由
     */
    CustInfoBean routeCDus();

    /**
     * 路由到当前DUS,即PCS主客户所在DUS
     * @param mainMapElementInfo 主路由要素
     * @return 路由信息
     */
    CustInfoBean routeCurrentDus(String mainMapElementInfo);

    /**
     * 根据PBS的DTO获取到的路由要素查询客户信息，并保存到公共域的上下文中
     * @param infos 路由要素信息
     * @param mainMapElementInfo 主路由要素信息
     * @param multyDeploy 同时在BDUS和CDUS部署
     * @return
     */
    CustInfoBean routeTargetDus(List<RouteInfo> infos, String mainMapElementInfo, boolean multyDeploy);
}
