package com.aidlmusicplayer.www;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.aidlmusicplayer.www.bean.SongListBean;
import com.aidlmusicplayer.www.service.MusicService;
import com.aidlmusicplayer.www.util.ToastUtil;

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

    private void showNotification() throws RemoteException {
        mMusicPlayerService = App.app.getMusicPlayerService();
        SongListBean songListBean =
                (SongListBean) mMusicPlayerService.getCurrentSongInfo().obj;
        if (songListBean == null) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(app);
        builder.setContentText("主内容区");
        builder.setContentTitle(songListBean.title);
        builder.setTicker("音乐已移到后台");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent(app, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(app, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        }
        notificationManager.notify(0, notification);
    }


    int isCurrentRunningForeground;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (isCurrentRunningForeground == 0) {// front
        }
        isCurrentRunningForeground++;
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
}
