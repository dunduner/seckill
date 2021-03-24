package com.seazen.zuul.entity;

import lombok.Data;

/**
 * @author zhangning
 * @date 2020/7/16
 */
@Data
public class GrayRule {

    private Integer id;
    private String user_id;
    private String version;
    private String metadata;
}
