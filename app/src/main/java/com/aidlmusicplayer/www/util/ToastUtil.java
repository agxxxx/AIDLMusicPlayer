package com.aidlmusicplayer.www.util;

import android.content.Context;
import android.widget.Toast;
/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public class ToastUtil {
    private static Toast mToast;
    public static void showShortToast(Context context , String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(content);
        mToast.show();
    }


}
