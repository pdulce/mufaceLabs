package com.mfc.infra.event;

import com.mfc.infra.utils.ConversionUtils;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

@Data
public class InnerEvent<T> implements Serializable {

    private String typeOfEvent;
    private Timestamp occurredOn;
    private String occurredOnFormatted;
    private String classname;
    private T data;

    public InnerEvent() {}
    public InnerEvent(String typeOfEvent, T data) {
        this.typeOfEvent = typeOfEvent;
        this.occurredOn = new Timestamp(Calendar.getInstance().getTimeInMillis());
        this.occurredOnFormatted = ConversionUtils.formatTimestampToSpanish(this.occurredOn);
        this.data = data;
        this.classname = data.getClass().getName();
    }

    public void setNewData(Object object) {
        // Hacer algo con el objeto
        this.data = (T) object;
    }

}
