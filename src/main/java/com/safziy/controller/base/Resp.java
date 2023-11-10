package com.safziy.controller.base;

import lombok.Data;

import java.time.Instant;

/**
 * 通用的请求返回
 * @param <T>
 */
@Data
public class Resp<T> {
    /**
     * 请求Id
     */
    private String seqId;
    /**
     * 返回的时间戳
     */
    private long timestamp;
    /**
     * 返回状态码
     */
    private String code;
    /**
     * 返回状态信息
     */
    private String message;
    /**
     * 返回的具体数据
     */
    private T data;
}
