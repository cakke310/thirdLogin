package com.xitek.dosnap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tencent.mm.sdk.modelmsg.SendAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void weChatLogin(View v){
        LogUtils.e("weChatLogin");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "dosnap_wechat_login";
        DosnapApp.mWeixinAPI.sendReq(req);
    }
}
