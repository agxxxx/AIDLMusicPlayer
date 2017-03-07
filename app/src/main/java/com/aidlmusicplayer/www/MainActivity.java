package com.aidlmusicplayer.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aidlmusicplayer.www.activity.MusicActivity;
import com.aidlmusicplayer.www.base.BaseRecyclerViewAdapter;
import com.aidlmusicplayer.www.bean.MusicServiceBean;
import com.aidlmusicplayer.www.bean.SongBillListBean;
import com.aidlmusicplayer.www.bean.SongListBean;
import com.aidlmusicplayer.www.helper.GsonHelper;
import com.aidlmusicplayer.www.net.NetCallBack;
import com.aidlmusicplayer.www.net.NetManager;
import com.aidlmusicplayer.www.service.MusicService;
import com.aidlmusicplayer.www.ui.BottomMusicPlayer;
import com.aidlmusicplayer.www.util.ToastUtil;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


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
    private FrameLayout mBottomContainer;
    private BottomMusicPlayer mBottomMusicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvContainer = ButterKnife.findById(this, R.id.rv_container);
        mBottomContainer = ButterKnife.findById(this, R.id.fl_bottom_container);
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

        mBottomMusicPlayer = new BottomMusicPlayer(this);
        mBottomContainer.addView(mBottomMusicPlayer);


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRvContainer.setLayoutManager(staggeredGridLayoutManager);
        mRvContainer.setLoadingListener(this);
        mSongListAdapter = new SongListAdapter(mSong_list);
        mSongListAdapter.setOnItemClickListener(mSongListAdapter);
        mRvContainer.setAdapter(mSongListAdapter);
        /******************************************************************/


    }




    @Override
    protected void onPause() {
        mBottomMusicPlayer.unregisterListener();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (!App.linkSuccess) {
                    SystemClock.sleep(300);
                }
                mBottomMusicPlayer.registerListener(App.app.getMusicPlayerService());
            }
        }.start();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
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
            startActivity(intent);
            MusicServiceBean musicServiceBean = new MusicServiceBean();
            musicServiceBean.song_list = (ArrayList<SongListBean>) mSongListAdapter.getDatum();
            musicServiceBean.position = position;
            musicServiceBean.backgroundUrl = info.pic_big;
            try {
                App.
                        app.
                        getMusicPlayerService().
                        action(MusicService.MUSIC_ACTION_PLAY, GsonHelper.getGson().toJson(musicServiceBean));
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }
    }


}
