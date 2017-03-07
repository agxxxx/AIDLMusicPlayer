package com.aidlmusicplayer.www.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.aidlmusicplayer.www.IMusicPlayer;
import com.aidlmusicplayer.www.IMusicPlayerListener;
import com.aidlmusicplayer.www.MainActivity;
import com.aidlmusicplayer.www.R;
import com.aidlmusicplayer.www.bean.SongListBean;
import com.aidlmusicplayer.www.config.Constant;
import com.aidlmusicplayer.www.receiver.NotificationReceiver;
import com.aidlmusicplayer.www.service.MusicService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.aidlmusicplayer.www.service.MusicService.MUSIC_ACTION_PAUSE;
import static com.aidlmusicplayer.www.service.MusicService.MUSIC_ACTION_PLAY;

/**
 * author：agxxxx on 2017/3/6 17:31
 * email：agxxxx@126.com
 * blog: http://www.jianshu.com/u/c1a3c4c943e5
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/6.
 */

public class MusicNotification {


    private final IMusicPlayer mMusicPlayerService;
    private final Context mContext;
    private NotificationManager mNotificationManager;
    private Notification mNotification;


    public MusicNotification(Context context, IMusicPlayer mMusicPlayerService) {
        this.mMusicPlayerService = mMusicPlayerService;
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

    }


    IMusicPlayerListener mPlayerListener = new IMusicPlayerListener.Stub() {
        @Override
        public void action(int action, Message msg) throws RemoteException {
            switch (action) {
                case MusicService.MUSIC_ACTION_PLAY:
                case MusicService.MUSIC_ACTION_PAUSE:
                    notifyMusic();
                    break;

            }
        }
    };

    public void registerListener() {
        try {
            if (mMusicPlayerService != null) {
                mMusicPlayerService.registerListener(mPlayerListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterListener() {
        if (mMusicPlayerService != null) {
            try {
                mMusicPlayerService.unregisterListener(mPlayerListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 1.play
     * 2.pause
     * 3.next
     * 4.open
     * 5.close
     * @param context
     * @param mMusicPlayerService
     * @return
     */
    private Notification createNotification(Context context, IMusicPlayer mMusicPlayerService) {
        try {
            SongListBean songListBean = (SongListBean) mMusicPlayerService.getCurrentSongInfo().obj;
            if (songListBean == null) {
                return null;
            }
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent openPendingIntent =
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews remoteView = createRemoteView(context);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContent(remoteView);
            builder.setContentTitle(songListBean.title);
            builder.setTicker("音乐已移到后台");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(openPendingIntent);

            builder.setAutoCancel(true);
            builder.setWhen(System.currentTimeMillis());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                builder.setShowWhen(false);
            }
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            NotificationTarget notificationTarget = new NotificationTarget
                    (context, remoteView, R.id.iv_icon, notification, 0);
            Glide.with(context).
                    load(songListBean.pic_big).
                    asBitmap().
                    error(R.mipmap.placeholder_disk_210).
                    into(notificationTarget);
            return notification;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void notifyMusic() {
        mNotification = createNotification(mContext, mMusicPlayerService);
        mNotificationManager.notify(0, mNotification);
    }


    RemoteViews createRemoteView(Context context) throws RemoteException {
        final SongListBean songListBean = (SongListBean) mMusicPlayerService.getCurrentSongInfo().obj;
        if (songListBean == null) {
            return null;
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_music);
        remoteViews.setTextViewText(R.id.tv_title, songListBean.title);
        remoteViews.setTextViewText(R.id.tv_content, songListBean.author + "-" + songListBean.title);


//        1. 2. play and pause
        if (MusicService.MUSIC_CURRENT_ACTION == MUSIC_ACTION_PLAY) {
            remoteViews.setImageViewResource(R.id.iv_pause, R.mipmap.note_btn_pause);
        } else if (MusicService.MUSIC_CURRENT_ACTION == MUSIC_ACTION_PAUSE) {
            remoteViews.setImageViewResource(R.id.iv_pause, R.mipmap.note_btn_play);
        }


        Intent intent = new Intent(NotificationReceiver.ACTION_MUSIC_PLAY);
        intent.putExtra(Constant.TAG_FLAG_1, MusicService.MUSIC_ACTION_PLAY);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_pause, pendingIntent);

//        3.next
        Intent nextIntent = new Intent(NotificationReceiver.ACTION_MUSIC_NEXT);
        nextIntent.putExtra(Constant.TAG_FLAG_1, MusicService.MUSIC_ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 3, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_next, nextPendingIntent);

//        5.close
//        ....

        return remoteViews;
    }

//
}
