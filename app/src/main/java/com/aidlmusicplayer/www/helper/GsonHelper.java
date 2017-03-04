package com.aidlmusicplayer.www.helper;

import com.google.gson.Gson;

/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public class GsonHelper {

    private static GsonHelper instance;
    private static Gson gson;

    private GsonHelper(){};
    public static GsonHelper getInstance(){
        if (instance == null) {
            synchronized (GsonHelper.class) {
                if (instance == null) {
                    instance = new GsonHelper();
                    gson = new Gson();
                }
            }
        }
        return instance;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(json, classOfT);
    }

}
