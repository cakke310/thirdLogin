package com.xitek.dosnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
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
    private AuthInfo authInfo;
    private SsoHandler ssoHandler;
    private UsersAPI usersAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        EventBus.getDefault().register(this);
        authInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        ssoHandler = new SsoHandler(this, authInfo);
    }

    public void weChatLogin(View v){
        LogUtils.e("weChatLogin");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "dosnap_wechat_login";
        DosnapApp.mWeixinAPI.sendReq(req);
    }

    public void weiBoLogin(View v){
        if (DosnapApp.mAccessToken != null
                && DosnapApp.mAccessToken.isSessionValid()) {
            long uid = Long.parseLong(DosnapApp.mAccessToken.getUid());
            usersAPI = new UsersAPI(MainActivity.this, Constants.APP_KEY,
                    DosnapApp.mAccessToken);
            usersAPI.show(uid, mListener);
        } else {
            ssoHandler.authorize(new AuthListener());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode,resultCode,data);
        }
    }

    class AuthListener  implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            DosnapApp.mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (DosnapApp.mAccessToken.isSessionValid()) {
                long uid = Long.parseLong(DosnapApp.mAccessToken.getUid());
                usersAPI = new UsersAPI(MainActivity.this, Constants.APP_KEY, DosnapApp.mAccessToken);
                usersAPI.show(uid,mListener);
            } else {
                // 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
//                String code = values.getString("code", "");
            }
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onWeiboException(WeiboException e) {
        }
    }

    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            LogUtils.e("user--"+s);
            if(!TextUtils.isEmpty(s)){
//                User user = User.parse(s);
//                if(user!=null){
//
//                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }
    };

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
