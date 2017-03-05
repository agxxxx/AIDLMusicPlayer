package com.aidlmusicplayer.www;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.aidlmusicplayer.www.activity.MusicActivity;
import com.aidlmusicplayer.www.base.BaseRecyclerViewAdapter;
import com.aidlmusicplayer.www.bean.MusicServiceBean;
import com.aidlmusicplayer.www.bean.SongBillListBean;
import com.aidlmusicplayer.www.bean.SongListBean;
import com.aidlmusicplayer.www.config.Constant;
import com.aidlmusicplayer.www.net.NetCallBack;
import com.aidlmusicplayer.www.net.NetManager;
import com.aidlmusicplayer.www.util.ToastUtil;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.aidlmusicplayer.www.App.app;


/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public class MainActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {
    private XRecyclerView mRvContainer;
    private ArrayList<SongListBean> mSong_list = new ArrayList<>();
    private SongListAdapter mSongListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvContainer = ButterKnife.findById(this, R.id.rv_container);
//        bindService();



        initVie();
        onRefresh();
    }


    int mType = 1;
    int mSize = 10;
    int mOffset = 0;
    int mPager = 0;

    private void loadData() {
        NetManager.
                getInstance().getSongBillListData(mType,
                mSize,
                mOffset,
                new NetCallBack<SongBillListBean>() {
                    @Override
                    public void onSuccess(SongBillListBean songBillListBean) {
                        mSongListAdapter.addAll(songBillListBean.song_list);

                        mRvContainer.refreshComplete();
                        mRvContainer.loadMoreComplete();
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.showShortToast(MainActivity.this, msg);

                        mRvContainer.refreshComplete();
                        mRvContainer.loadMoreComplete();
                    }
                });
    }


    private void initVie() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRvContainer.setLayoutManager(staggeredGridLayoutManager);
        mRvContainer.setLoadingListener(this);
        mSongListAdapter = new SongListAdapter(mSong_list);
        mSongListAdapter.setOnItemClickListener(mSongListAdapter);
        mRvContainer.setAdapter(mSongListAdapter);
    }

    @Override
    public void onRefresh() {
        mPager = 0;
        mOffset = 0;
        mSongListAdapter.removeAll();
        loadData();
    }

    @Override
    public void onLoadMore() {
        mPager++;
        mOffset = mPager * mSize;
        loadData();
    }

//    private void bindService() {
//        Intent intent = new Intent(App.app, MusicService.class);
//        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
//    }

    private IMusicPlayer mMusicPlayerService;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicPlayerService = IMusicPlayer.Stub.asInterface(service);

        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
//            bindService();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(mServiceConnection);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        try {
            showNotification();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//        super.onBackPressed();
    }
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

    class SongListAdapter extends BaseRecyclerViewAdapter<SongListBean>
            implements BaseRecyclerViewAdapter.OnItemClickListener<SongListBean> {
        public SongListAdapter(List<SongListBean> mDatum) {
            super(mDatum);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_music;
        }

        @Override
        protected void onBind(ViewHolder holder, int position, SongListBean data) {
            ImageView imageView = holder.getImageView(R.id.iv_icon);
            Glide.with(MainActivity.this).load(data.pic_big)
                    .into(imageView);
            holder.setTextView(R.id.tv_name, data.title);
        }


        @Override
        public void onItemClick(View view, int position, SongListBean info) {
            Intent intent = new Intent(MainActivity.this, MusicActivity.class);
            MusicServiceBean musicServiceBean = new MusicServiceBean();
            musicServiceBean.song_list = (ArrayList<SongListBean>) mSongListAdapter.getDatum();
            musicServiceBean.position = position;
            musicServiceBean.backgroundUrl = info.pic_big;
            intent.putExtra(Constant.TAG_FLAG_1, musicServiceBean);
            startActivity(intent);
        }
    }




}
