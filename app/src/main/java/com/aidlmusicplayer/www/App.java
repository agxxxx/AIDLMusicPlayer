package com.aidlmusicplayer.www;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.aidlmusicplayer.www.service.MusicService;

/**
 * author：agxxxx on 2017/3/4 14:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/4.
 */

public class App extends Application {

    public static App app;

    @Override
    public void onCreate() {
        app = this;
        bindService();
        super.onCreate();


    }

    public IMusicPlayer getMusicPlayerService() {
        return mMusicPlayerService;
    }

    private void bindService() {
        Intent intent = new Intent(App.app, MusicService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    private IMusicPlayer mMusicPlayerService;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicPlayerService = IMusicPlayer.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindService();
        }
    };

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        unbindService(mServiceConnection);
        super.onTerminate();
    }

}
