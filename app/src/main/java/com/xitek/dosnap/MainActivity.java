package com.xitek.dosnap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.xitek.dosnap.util.DataManager;
import com.xitek.dosnap.util.Event;
import com.xitek.dosnap.util.HttpUtils;
import com.xitek.dosnap.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends Activity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        EventBus.getDefault().register(this);
    }

    public void weChatLogin(View v){
        LogUtils.e("weChatLogin");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "dosnap_wechat_login";
        DosnapApp.mWeixinAPI.sendReq(req);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventProcess(Event<Object> event){
        if(event.getType() == DataManager.WECCHAT_LOG){
//            tv.setText("微信登录成功");
            HttpUtils.getWeChatToken((String) event.getData());
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
