package com.aidlmusicplayer.www.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aidlmusicplayer.www.IMusicPlayer;
import com.aidlmusicplayer.www.R;
import com.aidlmusicplayer.www.bean.MusicServiceBean;
import com.aidlmusicplayer.www.blur.ImageBlurManager;
import com.aidlmusicplayer.www.config.Constant;
import com.aidlmusicplayer.www.helper.GsonHelper;
import com.aidlmusicplayer.www.service.MusicService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {


    @Bind(R.id.musics_player_background)
    ImageView mMusicsPlayerBackground;
    @Bind(R.id.player_disc)
    ImageView mPlayerDisc;
    @Bind(R.id.player_disc_image)
    ImageView mPlayerDiscImage;
    @Bind(R.id.player_disc_container)
    RelativeLayout mPlayerDiscContainer;
    @Bind(R.id.player_needle)
    ImageView mPlayerNeedle;
    @Bind(R.id.musics_player_name)
    TextView mMusicsPlayerName;
    @Bind(R.id.musics_player_songer_name)
    TextView mMusicsPlayerSongerName;
    @Bind(R.id.musics_player_current_time)
    TextView mMusicsPlayerCurrentTime;

    @Bind(R.id.musics_player_total_time)
    TextView mMusicsPlayerTotalTime;
    @Bind(R.id.musics_player_progress_container)
    LinearLayout mMusicsPlayerProgressContainer;
    @Bind(R.id.musics_player_play_prev_btn)
    ImageButton mMusicsPlayerPlayPrevBtn;
    @Bind(R.id.musics_player_play_ctrl_btn)
    ImageButton mMusicsPlayerPlayCtrlBtn;
    @Bind(R.id.musics_player_play_next_btn)
    ImageButton mMusicsPlayerPlayNextBtn;
    @Bind(R.id.musics_player_loading_view)
    View mMusicsPlayerLoadingView;
    @Bind(R.id.musics_player_container)
    RelativeLayout mMusicsPlayerContainer;
    private IMusicPlayer mMusicPlayer;

    @Bind(R.id.musics_player_seekbar)
    SeekBar mMusicsPlayerSeekbar;
    private MusicServiceBean mMusicServiceBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);


        mMusicServiceBean = getIntent().getParcelableExtra(Constant.TAG_FLAG_1);
        setTitle(mMusicServiceBean.song_list.get(mMusicServiceBean.position).title);



        Bitmap bitmap = ImageBlurManager.doBlurJniArray(BitmapFactory.decodeResource(getResources(),
                R.drawable.player_bg),
                100,
                false);

        //  blur background
        mMusicsPlayerBackground.setImageBitmap(bitmap);
        mMusicsPlayerSeekbar.setOnSeekBarChangeListener(this);
//        try {
// mMusicPlayer.action(MusicService.MUSIC_ACTION_PLAY, GsonHelper.getGson().toJson(mMusicServiceBean));
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }

    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicPlayer = IMusicPlayer.Stub.asInterface(service);

            try {

                mMusicPlayer.action(MusicService.MUSIC_ACTION_PLAY, GsonHelper.getGson().toJson(mMusicServiceBean));
                mMusicPlayer.asBinder().linkToDeath(mDeathRecipient, 0);

//
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {//死亡通知者
        @Override
        public void binderDied() {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);

    }

    /******************************************************************/

    @OnClick({R.id.musics_player_play_prev_btn, R.id.musics_player_play_ctrl_btn, R.id.musics_player_play_next_btn})
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.musics_player_play_prev_btn:
                    mMusicPlayer.action(MusicService.MUSIC_ACTION_PREVIOUS, "");

                    break;
                case R.id.musics_player_play_ctrl_btn:

                    onPayBtnPress();

                    break;
                case R.id.musics_player_play_next_btn:

                    mMusicPlayer.action(MusicService.MUSIC_ACTION_NEXT, "");

                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onPayBtnPress() throws RemoteException {
        switch (MusicService.MUSIC_CURRENT_ACTION) {
            case MusicService.MUSIC_ACTION_PLAY:
                mMusicPlayer.action(MusicService.MUSIC_ACTION_PAUSE, "");
                break;
            case MusicService.MUSIC_ACTION_STOP:
                mMusicPlayer.action(MusicService.MUSIC_ACTION_PLAY, GsonHelper.getGson().toJson(mMusicServiceBean));
                break;
            case MusicService.MUSIC_ACTION_PAUSE:
                mMusicPlayer.action(MusicService.MUSIC_ACTION_CONTINUE_PLAY, "");

                break;
        }
    }

    /******************************************************************/
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekBar.setProgress(seekBar.getProgress());
        try {
            mMusicPlayer.action(MusicService.MUSIC_ACTION_SEEK_PLAY, String.valueOf(progress));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
