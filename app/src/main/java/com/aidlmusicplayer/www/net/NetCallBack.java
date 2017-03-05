package com.aidlmusicplayer.www.net;

import com.aidlmusicplayer.www.App;
import com.aidlmusicplayer.www.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public abstract class NetCallBack<M> implements Callback<M> {

    public abstract void onSuccess(M m);

    public void onFailure(String msg) {
        ToastUtil.showShortToast(App.app, msg);
    }

    public void onFinish() {
    }



    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        onSuccess(response.body());
        onFinish();
    }

    @Override
    public void onFailure(Call<M> call, Throwable t) {
        onFailure(t.getMessage());
        onFinish();
    }
}
