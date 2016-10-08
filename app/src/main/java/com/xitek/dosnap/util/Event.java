package com.xitek.dosnap.util;

/**
 * Created by Administrator on 2016-10-08.
 */
public class Event<T> {
    public int type;
    public T data;

    public Event(int type, T data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
