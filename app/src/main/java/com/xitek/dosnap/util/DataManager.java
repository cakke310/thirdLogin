package com.xitek.dosnap.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016-10-08.
 */
public class DataManager {
    public static int WECCHAT_LOG = 0;
    private static DataManager _instance;

    public static DataManager getInstance(){
        synchronized (DataManager.class){
            if(_instance == null){
                _instance = new DataManager();
            }
            return _instance;
        }
    }

    public void weChatEvent(String ev){
        EventBus.getDefault().post(new Event<>(WECCHAT_LOG, ev));
    }
}
