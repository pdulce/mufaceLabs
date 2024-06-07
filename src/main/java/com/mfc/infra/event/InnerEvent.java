package com.mfc.infra.event;

import com.mfc.infra.utils.ConversionUtils;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

@Data
public class InnerEvent<T> implements Serializable {

    private String typeEvent;
    private Timestamp occurredOn;
    private String occurredOnFormatted;
    private T data;

    public InnerEvent() {}
    public InnerEvent(String typeEvent, T data) {
        this.typeEvent = typeEvent;
        this.occurredOn = new Timestamp(Calendar.getInstance().getTimeInMillis());
        this.occurredOnFormatted = ConversionUtils.formatTimestampToSpanish(this.occurredOn);
        this.data = data;
    }

    public void setNewData(Object object) {
        // Hacer algo con el objeto
        this.data = (T) object;
    }

}
