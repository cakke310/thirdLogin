package com.xitek.dosnap;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xitek.dosnap.util.LogUtils;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DosnapApp extends Application {

	public static final int ACTIVITY_CATEGORY = 41;
	public static String splashurl = "";
	private static DosnapApp singleton;

	public static final int ACTIVITY_LOGIN = 1;				// 登录

	public static final int ACTIVITY_HISTORY = 2;			// 注册
	public static final int ACTIVITY_WEIBOLOGIN = 3;
	public static final int ACTIVITY_WEIXINLOGIN = 4;
	public static final int ACTIVITY_QQLOGIN = 5;
	public static final int ACTIVITY_XITEKLOGIN = 6;		// 无忌登录
	public static final int ACTIVITY_SET = 7;				// 设置
	public static final int ACTIVITY_IMAGE_CAPTURE = 8;		// 相机
	public static final int SELECT_A_PICTURE = 9;			// 相册
	public static final int SELECT_A_PICTURE_AFTER_KIKAT = 12;// 相册
	public static final int ACTIVITY_AVATAR = 10;			// 头像
	public static final int ACTIVITY_RC = 11;				// 头像
	public static final int ACTIVITY_FILTER = 13;			// 特效
	public static final int ACTIVITY_SHARE = 14;			// 分析
	public static final int ACTIVITY_USER = 15;				// 用户
	public static final int ACTIVITY_ACTIVITY = 16;			// 约拍详细页
	public static final int ACTIVITY_PHOTO = 17;			// 照片详细页
	public static final int ACTIVITY_FANS = 18;				// 粉丝页
	public static final int ACTIVITY_ACTIVITYUSER = 19;		// 活动用户列表页
	public static final int ACTIVITY_COMMENT = 20;			// 评论页
	public static final int ACTIVITY_FOLLOW = 21;
	public static final int ACTIVITY_WECHAT = 22;

	public static final int ACTIVITY_ADDRESS = 23;			// 添加位置
	public static final int ACTIVITY_CALL = 24;				// 添加at
	public static final int ACTIVITY_PHOTOSHOW = 25;		// 单图显示
	public static final int ACTIVITY_MSG = 26;				// 消息列表
	public static final int ACTIVITY_CHAT = 27;				// 私信
	public static final int ACTIVITY_GOODPHOTO = 28;		// 赞过照片列表
	public static final int ACTIVITY_FEEDBACK = 29;			// 意见反馈
	public static final int ACTIVITY_CROP = 30;				// 头像裁剪
	public static final int ACTIVITY_EDIT = 31;				// 编辑页
	public static final int ACTIVITY_SEARCH = 32;			// 搜索
	public static final int ACTIVITY_LABEL = 33;			// 按标签id查找
	public static final int ACTIVITY_FRIEND = 34;			// 好友邀请
	public static final int ACTIVITY_SINAFANS = 35;			// 新浪互粉列表
	public static final int ACTIVITY_AVIARY = 36;			// 特效
	public static final int ACTIVITY_WEIBO = 37;			// 特效
	public static final int ACTIVITY_GENDER = 38;			// 性别
	public static final int ACTIVITY_PHOTOPICK = 39;		// 选择照片
	public static final int ACTIVITY_BINDING = 40;			// 绑定手机号
	public static final int ACTIVITY_SETPASSWORD = 41;		// 设置密码

	public static final int WECHAT_LOGIN_OK = 101;
	/** 用来存放倒计时的时间 */
	public static Map<String, Long> map;

	public static String identifier = "";
	public static String token = "";
	public static int userid = 0;
	public static int timeout = 0;
	public static String username = "";
	public static String usertext = "";
	public static String fansCount = "";
	public static String imgCount = "";
	public static String firstImg = "";
	public static int gender = 0;

	public static String maker = "";
	public static String model = "";
	public static String exif = "";

	public static String xgtoken = "";

	public static int devicewidth = 100;
	public static int deviceheight = 800;

	public static String apiHost = "http://app.dosnap.com/";
	public static String apiDir = "api/";

	public static String ver = "";

	public static Bitmap croppedImage;

	public static int curFragment = 0;
	public static boolean needRefresh = false;
	public static boolean inphotoshow = false;
	public static String newAvatar = "";

	public static int atcolor = 0;

	public static boolean mCache = true;
	public static boolean ispopup = false;

//	public static AQuery aq;

	public static IWXAPI mWeixinAPI;
	public static String WEIXIN_APP_ID = "wxeb794f94487c13df";

	public static Oauth2AccessToken mAccessToken;

//	public static Tencent mTencent;

	public static String VERSION_KEY = "versionkey";
	public static String DOUBLE_TAP = "double_tap";
	public static String ADD_TAGS = "add_tags";
	public static String EDIT_USERINFO = "edit_userinfo";
	public static String YOUR_LAUDED_PHOTOS = "lauded_photos";
	public static String VIEW_BIGAVATAR = "view_bigavatar";
	public static String USER_LAUDED_PHOTOS = "user_lauded_photos";
	public static String EDIT_PHOTOS = "edit_photos";

	/** 是否上传新图片 */
	public static boolean isuploadPhoto = false;

	private static boolean firstLaunch = true;
	/** 获取到主线程的handler */
	public static Handler mMainThreadHandler;
	/** 获取到主线程 */
	public static Thread mMainThread;
	/** 获取到主线程的ID */
	public static int mMainThreadId;
	/** 获取到主线程的轮询器 */
	public static Looper mMainThreadLooper;

	public static ExecutorService LIMITED_TASK_EXECUTOR = null;
	public static String longitude = "";
	public static String latitude = "";

	/** 返回上下文 */
	public static DosnapApp getApplication() {
		return singleton;
	}
	
	/** 返回主线程的handler */
	public static Handler getMainThreadHandler() {
		return mMainThreadHandler;
	}

	/** 返回主线程的looper */
	public static Looper getMainThreadLooper() {
		return mMainThreadLooper;
	}
    /** 返回主线程的id */
	public static int getMainThreadId() {
		return mMainThreadId;
	}
    /** 返回主线程 */
	public static Thread getMainThread() {
		return mMainThread;
	}

	@Override
	public void onCreate() {
		super.onCreate();
//		LeakCanary.install(this);
		//在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(this);
		if (DosnapApp.mWeixinAPI == null) {
			LogUtils.e(DosnapApp.mWeixinAPI+"");
			DosnapApp.mWeixinAPI = WXAPIFactory.createWXAPI(this.getApplicationContext(), DosnapApp.WEIXIN_APP_ID, false);
		}
		DosnapApp.mWeixinAPI.registerApp(DosnapApp.WEIXIN_APP_ID);
		LogUtils.e(DosnapApp.mWeixinAPI+"");
		isFirstLaunch();
//		Fresco.initialize(this, ConfigConstants.getImagePipelineConfig(this));
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(100 * 1024 * 1024)
//				.diskCacheFileCount(300).tasksProcessingOrder(QueueProcessingType.LIFO).build();
//		ImageLoader.getInstance().init(config);
		LIMITED_TASK_EXECUTOR = Executors.newFixedThreadPool(5);
		singleton = this;
		ver = getVersionName();
		mMainThreadHandler = new Handler();
		mMainThread = Thread.currentThread();
		mMainThreadId = android.os.Process.myTid();
		mMainThreadLooper = getMainLooper();
		getconfig();
//		aq = new AQuery(this);
//		atcolor = getResources().getColor(R.color.dosnapblue);
	}
	
	@Override
	public void onLowMemory() {
//		BitmapAjaxCallback.clearCache();
		super.onLowMemory();
	}
	
	/** 判断是否第一次启动 */
	private void isFirstLaunch() {
		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo("com.xitek.dosnap", 0);
			int currentVersion = info.versionCode;
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			int lastVersion = prefs.getInt(VERSION_KEY, 0);
			if (currentVersion > lastVersion) {
				//如果当前版本大于上次版本，该版本属于第一次启动
				firstLaunch = true;
				//将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
				prefs.edit().putInt(VERSION_KEY, currentVersion);
				prefs.edit().putBoolean(DOUBLE_TAP, true);
				prefs.edit().putBoolean(ADD_TAGS, true);
				prefs.edit().putBoolean(EDIT_USERINFO, true);
				prefs.edit().putBoolean(YOUR_LAUDED_PHOTOS, true);
				prefs.edit().putBoolean(VIEW_BIGAVATAR, true);
				prefs.edit().putBoolean(USER_LAUDED_PHOTOS, true);
				prefs.edit().putBoolean(EDIT_PHOTOS, true);
				prefs.edit().apply();
			} else {
				firstLaunch = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getconfig() {
		Context ctx = getApplication();
		SharedPreferences userInfo = ctx.getSharedPreferences("user_info", 0);
		try {
			identifier = userInfo.getString("identifier", "");
			token = userInfo.getString("token", "");
			userid = userInfo.getInt("userid", 0);
			gender = userInfo.getInt("gender", 0);
			timeout = userInfo.getInt("timeout", 0);
			username = userInfo.getString("username", "");
			usertext = userInfo.getString("usertext", "");
			splashurl = userInfo.getString("splashurl", "");
			newAvatar = userInfo.getString("avatar", "");
//			mAccessToken = AccessTokenKeeper.readAccessToken(ctx);
		} catch (Exception e) {
			identifier = "";
			token = "";
			userid = 0;
			username = "";
			usertext = "";
			splashurl = "";
		}
	}

	public int isWifi() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null) {
			if (activeNetInfo.getTypeName().equals("WIFI"))
				return 1;
			else
				return 2;
		} else {
			return 0;
		}
	}

	private String getVersionName() {
		try {
			PackageManager packageManager = getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionName;
		} catch (Exception e) {
			return "";
		}
	}
}
