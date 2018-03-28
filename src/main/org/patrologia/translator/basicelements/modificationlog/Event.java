package org.patrologia.translator.basicelements.modificationlog;

/**
 * Created by lkloeble on 02/09/2016.
 */
public class Event {

    private Integer eventCode;
    private Integer position;

    public Event(Integer eventCode, Integer position) {
        this.eventCode = eventCode;
        this.position = position;
    }

    public Integer getEventCode() {
        return eventCode;
    }

    public Integer getPosition() {
        return position;
    }
}
