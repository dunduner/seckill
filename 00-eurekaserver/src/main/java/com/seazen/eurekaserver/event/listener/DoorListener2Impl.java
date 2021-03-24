package com.seazen.eurekaserver.event.listener;

import com.seazen.eurekaserver.event.DoorEvent;

/**
 * @author zhangning
 * @date 2020/6/28
 * 该类为 门1监听接口的实现，做具体的开门，关门动作
 */
public class DoorListener2Impl implements DoorListener {
    @Override
    public void doorEvent(DoorEvent doorEvent) {
        if (doorEvent.getDoorState() != null && doorEvent.getDoorState().equals("open")) {
            System.out.println("2、门2打开，同时打开走廊的灯");
        } else if(doorEvent.getDoorState() != null && doorEvent.getDoorState().equals("close")){
            System.out.println("5、门2关闭，同时关闭走廊的灯");
        }else {
            System.out.println("门2不知道干嘛");
        }
    }
}
