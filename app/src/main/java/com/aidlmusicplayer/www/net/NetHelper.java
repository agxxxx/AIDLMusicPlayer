package com.aidlmusicplayer.www.net;

import java.util.HashMap;
import java.util.Map;
/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public class NetHelper {
    public static Map<String, String> getMusicApiCommonParams(String method) {
        Map params = new HashMap();
        params.put("format", "json");
        params.put("calback", "");
        params.put("from", "webapp_music");
        params.put("method", method);
        return params;
    }
}
