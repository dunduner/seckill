package com.seazen.eurekaserver.event;

import java.util.EventObject;

/**
 * @author zhangning
 * @date 2020/6/28
 */
public class DoorEvent extends EventObject {

    private String doorState= "";//表示门的2中状态，开和关两种

    /**
     * Constructs a prototypical Event.
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DoorEvent(Object source, String doorState) {
        super(source);
        this.doorState = doorState;
    }

    public String getDoorState() {
        return doorState;
    }

    public void setDoorState(String doorState) {
        this.doorState = doorState;
    }
}
