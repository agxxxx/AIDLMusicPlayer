package com.aidlmusicplayer.www.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.aidlmusicplayer.www.IMusicPlayer;

/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public class MusicService extends Service {

    private MediaPlayer mMediaPlayer;


    public static final int MUSIC_ACTION_PLAY = 255;
    public static final int MUSIC_ACTION_PREVIOUS = 256;
    public static final int MUSIC_ACTION_NEXT = 257;
    public static final int MUSIC_ACTION_MUTE = 258;
    public static final int MUSIC_ACTION_PAUSE = 259;

    private int currentListItem;

    Binder mBinder = new IMusicPlayer.Stub() {
        @Override
        public void action(int action, String json) throws RemoteException {

            switch (action) {
                case MUSIC_ACTION_PLAY:

                    break;

                case MUSIC_ACTION_PREVIOUS:

                    break;
                case MUSIC_ACTION_NEXT:

                    break;
                case MUSIC_ACTION_MUTE:

                    break;
                case MUSIC_ACTION_PAUSE:

                    break;

            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }


    @Override
    public void onCreate() {
        mMediaPlayer = new MediaPlayer();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    public void refreshView() {
        if (mMediaPlayer.isPlaying()) {

        } else {

        }
    }

}
