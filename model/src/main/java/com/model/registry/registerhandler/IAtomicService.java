package com.model.registry.registerhandler;

/**
 * 服务引擎-业务方法接口
 * @param <T> 请求DTO
 * @param <R> 响应DTO
 */
public interface IAtomicService<T,R> {

    R doService(T data);

    R compensate(T data);

}
