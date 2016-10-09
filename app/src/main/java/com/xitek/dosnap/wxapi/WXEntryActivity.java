package com.xitek.dosnap.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.xitek.dosnap.DosnapApp;
import com.xitek.dosnap.util.DataManager;
import com.xitek.dosnap.util.Event;
import com.xitek.dosnap.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/10/8.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DosnapApp.mWeixinAPI.handleIntent(getIntent(),this);
        LogUtils.e("WXEntryActivity oncreate");
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                LogUtils.e("登入成功"+baseResp.getType());
                if(baseResp.getType() == 1){
                    String code = ((SendAuth.Resp) baseResp).code;
                    DataManager.getInstance().weChatEvent(code);
                }

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                LogUtils.e("取消登录");
                break;
        }
        this.finish();
    }
}
