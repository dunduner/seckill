package com.seazen.eurekaserver.event.listener;

import com.seazen.eurekaserver.event.DoorEvent;

import java.util.EventListener;

/**
 * @author zhangning
 * @date 2020/6/28
 */
public interface DoorListener extends EventListener {
    //定义新的事件监听接口，该接口继承自EventListener；该接口包含对doorEvent事件的处理程序
    void doorEvent(DoorEvent doorEvent);
}
