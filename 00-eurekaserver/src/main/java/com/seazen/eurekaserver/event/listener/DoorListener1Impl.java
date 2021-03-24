package com.seazen.eurekaserver.event.listener;

import com.seazen.eurekaserver.event.DoorEvent;

/**
 * @author zhangning
 * @date 2020/6/28
 * 该类为 门1监听接口的实现，做具体的开门，关门动作
 */
public class DoorListener1Impl implements DoorListener {
    @Override
    public void doorEvent(DoorEvent doorEvent) {
        if (doorEvent.getDoorState() != null && doorEvent.getDoorState().equals("open")) {
            System.out.println("1、门1打开");
        } else if(doorEvent.getDoorState() != null && doorEvent.getDoorState().equals("close")){
            System.out.println("4、门1关闭");
        }else {
            System.out.println("门1不知道干嘛");

        }
    }
}
