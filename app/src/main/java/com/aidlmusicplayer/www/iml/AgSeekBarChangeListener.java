package com.aidlmusicplayer.www.iml;

import android.os.RemoteException;
import android.widget.SeekBar;

import com.aidlmusicplayer.www.App;
import com.aidlmusicplayer.www.IMusicPlayer;
import com.aidlmusicplayer.www.service.MusicService;

/**
 * author：agxxxx on 2017/3/6 10:42
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/6.
 */

public class AgSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private IMusicPlayer mMusicPlayerService;

    public AgSeekBarChangeListener(IMusicPlayer musicPlayerService) {
        mMusicPlayerService = musicPlayerService;
    }

    /******************************************************************/
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        try {
            seekBar.setProgress(seekBar.getProgress());
            if (mMusicPlayerService!=null) {
                mMusicPlayerService.action(MusicService.MUSIC_ACTION_SEEK_PLAY, String.valueOf(seekBar.getProgress()));
            }else{
                mMusicPlayerService = App.app.getMusicPlayerService();
                mMusicPlayerService.action(MusicService.MUSIC_ACTION_SEEK_PLAY, String.valueOf(seekBar.getProgress()));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
