package com.aidlmusicplayer.www.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.aidlmusicplayer.www.IMusicPlayer;
import com.aidlmusicplayer.www.IMusicPlayerListener;
import com.aidlmusicplayer.www.bean.MusicServiceBean;
import com.aidlmusicplayer.www.bean.PaySongBean;
import com.aidlmusicplayer.www.bean.SongListBean;
import com.aidlmusicplayer.www.helper.GsonHelper;
import com.aidlmusicplayer.www.net.NetCallBack;
import com.aidlmusicplayer.www.net.NetManager;
import com.aidlmusicplayer.www.util.ToastUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public class MusicService extends Service implements
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener {

    /******************************************************************/
    public static final int MUSIC_ACTION_PLAY = 255;
    public static final int MUSIC_ACTION_PREVIOUS = 256;
    public static final int MUSIC_ACTION_NEXT = 257;
    public static final int MUSIC_ACTION_MUTE = 258;
    public static final int MUSIC_ACTION_PAUSE = 259;
    public static final int MUSIC_ACTION_STOP = 260;
    public static final int MUSIC_ACTION_CONTINUE_PLAY = 280;
    public static final int MUSIC_ACTION_SEEK_PLAY = 270;

    public static int MUSIC_CURRENT_ACTION = -1;


    public static final int MUSIC_PLAY_MODE_NORMAL = 2000;
    public static final int MUSIC_PLAY_MODE_RANDOM = 2001;
    public static final int MUSIC_PLAY_MODE_REPEAT = 2002;
    public static final int MUSIC_PLAY_MODE_SINGLE = 2003;

    public static final int MUSIC_CURRENT_MODE = -1;


    public static final int PLAYER_LISTENER_ACTION_NORMAL = 1001;
    /******************************************************************/
    private MediaPlayer mMediaPlayer;
    private Timer mTimer;

    private RemoteCallbackList<IMusicPlayerListener> mListenerList
            = new RemoteCallbackList<>();


    private int currentPosition;
    private ArrayList<SongListBean> mSong_list = new ArrayList<>();

    Binder mBinder = new IMusicPlayer.Stub() {
        @Override
        public void action(int action, String datum) throws RemoteException {
            switch (action) {
                case MUSIC_ACTION_PAUSE:
                    pauseSong();
                    break;
                case MUSIC_ACTION_STOP:
                    stopSong();
                    break;
                case MUSIC_ACTION_SEEK_PLAY:
                    seekPlaySong(Integer.parseInt(datum));
                    break;
                case MUSIC_ACTION_PLAY:
                    MusicServiceBean musicServiceBean = GsonHelper.getGson().fromJson(datum, MusicServiceBean.class);
                    currentPosition = musicServiceBean.position;
                    mSong_list.clear();
                    mSong_list.addAll(musicServiceBean.song_list);
                    play();
                    break;
                case MUSIC_ACTION_CONTINUE_PLAY:
                    continuePlaySong();
                    break;
                case MUSIC_ACTION_MUTE:

                    break;
                case MUSIC_ACTION_PREVIOUS:
                    onActionPrevious();
                    break;
                case MUSIC_ACTION_NEXT:
                    onActionNext();
                    break;
            }
        }


        @Override
        public void registerListener(IMusicPlayerListener listener) throws RemoteException {
            mListenerList.register(listener);

        }

        @Override
        public void unregisterListener(IMusicPlayerListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }

        @Override
        public Message getCurrentSongInfo() throws RemoteException {
            Message msg = Message.obtain();
            msg.obj = mSong_list.get(currentPosition);
            return msg;
        }


    };


    private void onActionPrevious() {
        if (currentPosition > 0) {
            currentPosition--;
        } else {
            currentPosition = mSong_list.size() - 1;
        }
        play();
    }

    private void onActionNext() {
        if (++currentPosition >= mSong_list.size()) {
            currentPosition = 0;
        }
        play();
    }

    private void onStartPlay() {
        Message msg = Message.obtain();
        msg.what = MUSIC_ACTION_PLAY;
        msg.arg1 = currentPosition;
        sendMessage(PLAYER_LISTENER_ACTION_NORMAL, msg);
    }

    private void play() {
        SongListBean songListBean = mSong_list.get(currentPosition);
        String song_id = songListBean.song_id;


        NetManager.getInstance().getPaySongData(song_id, new NetCallBack<PaySongBean>() {
            @Override
            public void onSuccess(PaySongBean paySongBean) {
                if (paySongBean != null && paySongBean.bitrate != null) {
                    onStartPlay();
                    playSong(paySongBean.bitrate.file_link);
                } else {
                    ToastUtil.showShortToast(getApplicationContext(), "音乐播放出错了");
                }
            }
        });

    }


    @Override
    public IBinder onBind(Intent intent) {
//        mMessenger = intent.getParcelableExtra(Constant.TAG_FLAG_5);
        // TODO: Return the communication channel to the service.
        return mBinder;
    }


    @Override
    public void onCreate() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).
                requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /******************************************************************/


    public void playSong(String path) {
        try {
            stopSong();
            mMediaPlayer.reset();//idle
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            MUSIC_CURRENT_ACTION = MUSIC_ACTION_PLAY;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void pauseSong() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            MUSIC_CURRENT_ACTION = MUSIC_ACTION_PAUSE;
        }
    }


    public void continuePlaySong() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            MUSIC_CURRENT_ACTION = MUSIC_ACTION_PLAY;
        }
    }


    public void stopSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            MUSIC_CURRENT_ACTION = MUSIC_ACTION_STOP;
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        }

    }

    public void seekPlaySong(int progress) {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(progress);
        }
    }


    /******************************************************************/
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        ToastUtil.showShortToast(this, "error ...");
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onPaying();
            }
        }, 0, 1000);
    }

    private void onPaying() {
        int currentPosition = mMediaPlayer.getCurrentPosition();
        int totalDuration = mMediaPlayer.getDuration();
        Message msg = Message.obtain();
        msg.what = MUSIC_ACTION_SEEK_PLAY;
        msg.arg1 = currentPosition;
        msg.arg2 = totalDuration;
        sendMessage(PLAYER_LISTENER_ACTION_NORMAL, msg);
    }

    private void sendMessage(int action, Message msg) {
        try {
            final int N = mListenerList.beginBroadcast();
            for (int i = 0; i < N; i++) {
                IMusicPlayerListener broadcastItem = mListenerList.getBroadcastItem(i);
                if (broadcastItem != null) {

                    broadcastItem.action(action, msg);
                }
            }
            mListenerList.finishBroadcast();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        onActionNext();
    }

    /******************************************************************/
    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                mMediaPlayer.start();
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stopSong playback and release media player
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stopSong
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }

    }


}
