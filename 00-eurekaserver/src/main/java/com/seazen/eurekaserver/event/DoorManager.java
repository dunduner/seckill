package com.seazen.eurekaserver.event;

import com.seazen.eurekaserver.event.listener.DoorListener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 事件源对象，在这里你可以把它想象成一个控制开门关门的遥控器，
 * (如果是在swing中，就类似一个button)
 */

public class DoorManager {

    private Collection listeners;

    //添加事件
    public DoorManager addDoorListener(DoorListener doorListener) {
        if (listeners == null) {
            listeners = new HashSet();
        }
        listeners.add(doorListener);
        return this;
    }

    //删除事件
    public void deleteDoorListener(DoorListener doorListener) {
        if (listeners == null) {
            return;
        }
        listeners.remove(doorListener);
    }


    //触发开门时间
    protected void fireWorkSpaceOpen() {
        if (listeners == null) {
            return;
        }
        DoorEvent doorEvent = new DoorEvent(this, "open");
        notifyListener(doorEvent);
    }

    //触发关门时间
    protected void fireWorkSpaceClose() {
        if (listeners == null) {
            return;
        }
        DoorEvent doorEvent = new DoorEvent(this, "close");
        notifyListener(doorEvent);
    }


    //通知所有的doorlistener
    private void notifyListener(DoorEvent doorEvent) {
        Iterator iterator = listeners.iterator();
        while (iterator.hasNext()) {
            DoorListener doorListener = (DoorListener) iterator.next();
            doorListener.doorEvent(doorEvent);
        }
    }

}
