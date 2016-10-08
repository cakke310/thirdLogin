package com.xitek.dosnap.util;

import com.google.gson.JsonObject;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016-10-08.
 */
public class HttpUtils {

    private static InputStream s;
    private static String response;

    public static String getWeChatToken(String code) {
        //// TODO: 2016-10-08 中断网络连接一般什么时候要用   刷新token   event线程处理  weixinLogin  face参数是什么
        LogUtils.e("getWeChatToken==" + code);
        String result = "";
        String uri = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxeb794f94487c13df&secret=00fade49499f4d73fec5b0354590ec84&code="
                + code + "&grant_type=authorization_code";
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int rCode = conn.getResponseCode();
//            LogUtils.e("rCode==" + rCode);
            if (rCode == 200) {
                s = conn.getInputStream();
                response = readStream(s);
            }
            JSONObject jsonObject = new JSONObject(response);
            String token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");
            uri = "https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openid;
            url = new URL(uri);
            conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == 200){
                s = conn.getInputStream();
                response = readStream(s);
            }
            LogUtils.e("response---"+response);
            return response;

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public static String readStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();
            String temptext = new String(baos.toByteArray());
            baos.close();
            return temptext;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
}
}
