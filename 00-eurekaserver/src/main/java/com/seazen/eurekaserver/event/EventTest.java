package com.seazen.eurekaserver.event;

import com.seazen.eurekaserver.event.listener.DoorListener1Impl;
import com.seazen.eurekaserver.event.listener.DoorListener2Impl;

/**
 * @author zhangning
 * @date 2020/6/28
 */
public class EventTest {
    public static void main(String[] args) {
        //创建遥控器
        DoorManager doorManager = new DoorManager();
        //注册监听事件
        doorManager.addDoorListener(new DoorListener1Impl()).addDoorListener(new DoorListener2Impl());
        //开门
        doorManager.fireWorkSpaceOpen();
        System.out.println("3、我已经进来了");
        //关门
        doorManager.fireWorkSpaceClose();
    }
}
