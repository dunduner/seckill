package com.seazen.productservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lfx on 2018/12/19.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Approval {

    private String nodeName;

    private String loginName;

    private String name;


}