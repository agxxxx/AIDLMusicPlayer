package com.aidlmusicplayer.www;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.aidlmusicplayer.www.service.MusicService;
import com.aidlmusicplayer.www.ui.MusicNotification;

/**
 * author：agxxxx on 2017/3/4 14:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/4.
 */

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    public static App app;
    private IMusicPlayer mMusicPlayerService;
    private MusicNotification mMusicNotification;


    @Override
    public void onCreate() {
        app = this;
        bindService();
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
    }


    public IMusicPlayer getMusicPlayerService() {

        return mMusicPlayerService;
    }

    private void bindService() {
        Intent intent = new Intent(App.app, MusicService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }


    static boolean linkSuccess;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicPlayerService = IMusicPlayer.Stub.asInterface(service);
            linkSuccess = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindService();
        }
    };




    int isCurrentRunningForeground;


    @Override
    public void onActivityStarted(Activity activity) {
        if (isCurrentRunningForeground == 0) {// front
            if (mMusicNotification != null) {
                mMusicNotification.unregisterListener();
            }
        }
        isCurrentRunningForeground++;
    }
    private void showNotification() throws RemoteException {
        if (mMusicNotification == null) {
            mMusicNotification = new MusicNotification(app, mMusicPlayerService);
        }
        mMusicNotification.registerListener();
        mMusicNotification.notifyMusic();
    }


    @Override
    public void onActivityStopped(Activity activity) {
        isCurrentRunningForeground--;
        if (isCurrentRunningForeground == 0) { // back
            try {
                showNotification();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }


    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

}
