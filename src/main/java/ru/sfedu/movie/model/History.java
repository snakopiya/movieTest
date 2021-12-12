package ru.sfedu.movie.model;

import java.util.Date;

public class History {
    private final Date instanceTime;
    private final String method;
    private final Object object;

    public History(Date instanceTime, String method,Object object) {
        this.instanceTime = instanceTime;
        this.method = method;
        this.object = object;
    }

    public Date getInstanceTime() {
        return instanceTime;
    }

    public String getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }
}
